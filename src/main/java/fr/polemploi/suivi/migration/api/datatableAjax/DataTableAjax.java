package fr.polemploi.suivi.migration.api.datatableAjax;

import fr.polemploi.suivi.migration.api.beans.DatatableResponse;
import fr.polemploi.suivi.migration.entities.dl1.Dl1Volumes;
import fr.polemploi.suivi.migration.entities.message.logs.RawFile;
import fr.polemploi.suivi.migration.entities.tir.DRSynthesis;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDB2;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDL1;
import fr.polemploi.suivi.migration.service.DataCompiler;
import fr.polemploi.suivi.migration.service.impl.FilesRetrieverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("ajax/datable")
public class DataTableAjax {

    @Autowired
    private DataCompiler dataCompiler;

    @Autowired
    private FilesRetrieverImpl fileImp;
    @GetMapping(value = "/directionSynthesis",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DRSynthesis> getDirectionSynthese() throws IOException {
        List<DRSynthesis> Li = this.dataCompiler.gatherTirSynthesis().getDirectionSynthesis();
        Li = Li.stream().filter(elm ->
                elm.getDirection().name().equals("DR32") ||
                elm.getDirection().name().equals("DR65")
        ).toList();
        return Li;
    }
    @GetMapping(value = "/dl1FilesVolumetrieOracle",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableResponse getDl1VolumetrieOracle() throws IOException {
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
            } catch (IOException e) {
                System.out.println(e.toString());
            } catch (ParseException e) {
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
    public DatatableDL1FilesResponse getDl1Files() throws IOException {
        DatatableDL1FilesResponse d = new DatatableDL1FilesResponse();
        TirDetailDL1 tirDetailDL1 = this.dataCompiler.gatherTirDL1AllInfos();
        d.data = tirDetailDL1.getDl1ControleFilesContainer().getDl1Files();
        return d;
    }

    @GetMapping(value = "/db2ConversionTablesLines",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableDL2ConversionResponse getdb2ConversionTableLines() throws IOException {
        DatatableDL2ConversionResponse d = new DatatableDL2ConversionResponse();
        TirDetailDB2 tirDetailDB2 = this.dataCompiler.gatherTirDB2AllInfos();
        d.data = tirDetailDB2.getDb2conversion();
        return d;
    }
}
