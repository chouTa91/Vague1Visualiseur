package fr.polemploi.suivi.migration.api.beans;


import java.util.List;

public class DatatableResponse<T> {
    public T data;

    public DatatableResponse(T data) {
        this.data = data;
    }
}
