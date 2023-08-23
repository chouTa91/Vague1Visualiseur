package fr.polemploi.suivi.migration.api.datatableAjax;

import fr.polemploi.suivi.migration.api.counter.LogsCounter;
import fr.polemploi.suivi.migration.api.path.PathDispenser;
import fr.polemploi.suivi.migration.entities.DatedInfos;
import fr.polemploi.suivi.migration.entities.alert.Alert;
import fr.polemploi.suivi.migration.entities.alert.Tuple;
import fr.polemploi.suivi.migration.entities.db2.DB2TablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFilesContainer;
import fr.polemploi.suivi.migration.entities.dl1.Dl1Volumes;
import fr.polemploi.suivi.migration.entities.message.logs.DB2ConvertMessage;
import fr.polemploi.suivi.migration.entities.message.logs.DB2LoadMessage;
import fr.polemploi.suivi.migration.entities.message.logs.RawFile;
import fr.polemploi.suivi.migration.entities.tir.DRSynthesis;
import fr.polemploi.suivi.migration.service.DataCompiler;
import fr.polemploi.suivi.migration.service.impl.FilesRetrieverImpl;
import fr.polemploi.suivi.migration.service.reader.LogsReader;
import fr.polemploi.suivi.migration.service.validator.predicate.PredicateSuiviMigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("ajax/datable")
public class DataTableAjax {

    @Autowired
    private DataCompiler dataCompiler;

    @Autowired
    private FilesRetrieverImpl fileImp;

    @Autowired
    private PredicateSuiviMigration prediMigration;

    @Autowired
    private LogsReader fileReader;

    @Autowired
    private PathDispenser pathDispenser;

    @Autowired
    private LogsCounter logsCounter;



    @GetMapping(value = "/directionSynthesis",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DRSynthesis> getDirectionSynthese() throws IOException {
        List<DRSynthesis> Li = this.dataCompiler.gatherTirSynthesis().getDirectionSynthesis();
        Li = Li.stream().filter(elm ->
                elm.getDirection().name().equals("DR32") ||
                elm.getDirection().name().equals("DR65")
        ).toList();
        return Li;
    }
    @GetMapping(value = "/controlelogs",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableResponse<DL1DirectionRegionaleTablesFiles> getControlelogs() throws IOException {
        DatatableResponse<DL1DirectionRegionaleTablesFiles> d = new DatatableResponse<>();
        d.data = this.dataCompiler.getDl1ControleLogs().getDl1Files().stream().peek(elm -> {
            if(elm.hasMissingFiles()){
                List<Tuple<String, Predicate<Alert>>> li = new ArrayList<>();
                li.add(this.prediMigration.dl1CheckMissingFileVague1(elm));
                elm.setAlertsMessages(li);
            }
        }).toList();
        return d;
    }

    @GetMapping(value = "/dl1FilesVolumetrieOracle",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableResponse<Dl1Volumes> getDl1VolumetrieOracle() throws IOException {
        List<Dl1Volumes> Li = new ArrayList<>();
        AtomicReference<Matcher> matcherElapsedTime = new AtomicReference<>();
        Pattern patternTime = Pattern.compile("Elapsed time was:(.*)");
        this.dataCompiler.gatherTirDL1AllInfos().getdL1TablesVolumeContainer().getVolume().forEach((key,value) ->{
            // pour chaque trois lettre/log/
            try {
                List<RawFile> Lii = this.fileImp.getDL1RawLogsChargeFiles(key);
                List<String> timeAllOfKey = new ArrayList<>();
                for(RawFile elm : Lii){
                    matcherElapsedTime.set(patternTime.matcher(elm.getContent()));
                    if(matcherElapsedTime.get().find()){
                        timeAllOfKey.add(matcherElapsedTime.get().group(1).replaceAll("\\s",""));
                    }
                }
                value.setTotalelapsedTime(getSumTime(timeAllOfKey));
                value.setTableName(key);
                Li.add(value);
            } catch (IOException | ParseException e) {
                System.out.println(e.toString());
            }
        });
        return new DatatableResponse<>(Li);
    }
    private static String getSumTime(List<String> timestampsList) throws ParseException {
        final DateFormat dt = new SimpleDateFormat("HH:mm:ss");
        final Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        long milliseconds = 0;
        c.clear();
        long startingMS = c.getTimeInMillis();
        for (final String t : timestampsList) {
            milliseconds = milliseconds + (dt.parse(t).getTime() - startingMS);
        }
        Duration duration = Duration.ofMillis(milliseconds);
        return format(duration.getSeconds() / 3600) + ":" + format((duration.getSeconds() % 3600) / 60) +":"+ format(duration.getSeconds()  % 60);
    }
    private static String format(long s){
        if (s < 10) return "0" + s;
        else return "" + s;
    }
    @GetMapping(value = "/dl1Files",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableResponse<DL1DirectionRegionaleTablesFiles> getDl1Files() throws IOException {
        DatatableResponse<DL1DirectionRegionaleTablesFiles> d = new DatatableResponse<>();
        d.data = this.dataCompiler.getDl1ControleFiles().getDl1Files().stream().peek(elm -> {
            if(elm.hasMissingFiles()){
                List<Tuple<String, Predicate<Alert>>> li = new ArrayList<>();
                li.add(this.prediMigration.dl1CheckMissingFileVague1(elm));
                elm.setAlertsMessages(li);
            }
        }).toList();
        return d;
    }

    @GetMapping(value = "/dl1ControlInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getDl1ControlInfo() {
        Map<String, String> d = new HashMap<>();
        DatedInfos infosFiles = this.logsCounter.getDL1MissingFilesFromDirectory();
        DatedInfos infosLogs = this.logsCounter.getDL1MissingLogsFromDirectory();
        System.out.println(infosLogs.total);
        System.out.println(infosLogs.count);
        d.put("filesCount", infosFiles.count.toString());
        d.put("filesTotal", infosFiles.total.toString());
        d.put("logCount", infosLogs.getCount().toString());
        d.put("logTotal", String.valueOf(infosLogs.total));
        return new ResponseEntity<>(d, HttpStatus.OK);
    }
    @GetMapping(value = "/db2ControlInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getDb2ControlInfo() {
        Map<String, String> d = new HashMap<>();
        DB2TablesFiles db2TablesFiles = this.logsCounter.getDB2MissingFilesFromDirectory();
        d.put("count", db2TablesFiles.getCount().toString());
        d.put("total", db2TablesFiles.getTotal().toString());
        d.put("refreshDate", db2TablesFiles.getRefreshDate());
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @GetMapping(value = "/db2ControlTable",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableResponse<DB2TablesFiles> getDb2ControlTable() {
        DatatableResponse<DB2TablesFiles> d = new DatatableResponse<>();
        d.data = Collections.singletonList(this.logsCounter.getDB2MissingFilesFromDirectory());
        return d;
    }

    @GetMapping(value = "/db2ConversionTable",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableResponse<DB2ConvertMessage> getDb2ConversionTable() throws IOException {
        DatatableResponse<DB2ConvertMessage> d = new DatatableResponse<>();
        d.data = this.fileReader.retrieveDB2ConvertInfos(this.pathDispenser.getDB2ConvertOracle());
        return d;
    }

    @GetMapping(value = "/db2LoadTable",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableResponse<DB2LoadMessage> getDb2LoadTable() throws IOException {
        DatatableResponse<DB2LoadMessage> d = new DatatableResponse<>();
        d.data = this.fileReader.retrieveDB2LoadInfos(this.pathDispenser.getDB2LoadOracle());
        return d;
    }

}
