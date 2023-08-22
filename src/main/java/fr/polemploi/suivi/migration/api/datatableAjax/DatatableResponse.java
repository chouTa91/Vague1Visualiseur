package fr.polemploi.suivi.migration.api.datatableAjax;

import fr.polemploi.suivi.migration.entities.dl1.Dl1Volumes;

import java.util.List;

public class DatatableResponse<T> {
    public List<T> data;

    public DatatableResponse() {
    }

    public DatatableResponse(List<T> data) {
        this.data = data;
    }
}
