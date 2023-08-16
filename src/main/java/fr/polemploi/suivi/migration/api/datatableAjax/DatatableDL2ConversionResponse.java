package fr.polemploi.suivi.migration.api.datatableAjax;

import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.message.logs.DB2ConvertMessage;

import java.util.List;

public class DatatableDL2ConversionResponse {
    public List<DB2ConvertMessage> data;
}
