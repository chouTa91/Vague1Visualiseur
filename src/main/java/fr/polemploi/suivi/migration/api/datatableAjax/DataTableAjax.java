package fr.polemploi.suivi.migration.api.datatableAjax;

import fr.polemploi.suivi.migration.entities.tir.DRSynthesis;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDB2;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDL1;
import fr.polemploi.suivi.migration.service.DataCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("ajax/datable")
public class DataTableAjax {

    @Autowired
    private DataCompiler dataCompiler;

    @GetMapping(value = "/directionSynthesis",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DRSynthesis> getDirectionSynthese() throws IOException {
        List<DRSynthesis> Li = this.dataCompiler.gatherTirSynthesis().getDirectionSynthesis();
        Li = Li.stream().filter(elm ->
                elm.getDirection().name().equals("DR32") ||
                elm.getDirection().name().equals("DR65")
        ).toList();
        return Li;
    }

    @GetMapping(value = "/dl1Files",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableDL1FilesResponse getDl1Files() throws IOException {
        DatatableDL1FilesResponse d = new DatatableDL1FilesResponse();
        TirDetailDL1 tirDetailDL1 = this.dataCompiler.gatherTirDL1AllInfos();
        d.data = tirDetailDL1.getDl1ControleFilesContainer().getDl1Files();
        return d;
    }

    @GetMapping(value = "/db2ConversionTablesLines",produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableDB2ConversionResponse getdb2ConversionTableLines() throws IOException {
        DatatableDB2ConversionResponse d = new DatatableDB2ConversionResponse();
        TirDetailDB2 tirDetailDB2 = this.dataCompiler.gatherTirDB2AllInfos();
        d.data = tirDetailDB2.getDb2conversion();
        return d;
    }
}
