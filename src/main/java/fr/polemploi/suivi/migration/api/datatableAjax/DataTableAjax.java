package fr.polemploi.suivi.migration.api.datatableAjax;

import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFilesContainer;
import fr.polemploi.suivi.migration.entities.tir.DRSynthesis;
import fr.polemploi.suivi.migration.entities.tir.TirSynthesis;
import fr.polemploi.suivi.migration.service.DataCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
    public datatableResponse getDl1Files() throws IOException {
        datatableResponse d = new datatableResponse();
        d.data = this.dataCompiler.getDl1ControleFiles().getDl1Files();
        return d;
    }

    public class datatableResponse{
        public List<DL1DirectionRegionaleTablesFiles> data;
    }
}
