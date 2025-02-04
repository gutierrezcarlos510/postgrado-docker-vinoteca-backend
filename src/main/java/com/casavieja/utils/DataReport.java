package com.casavieja.utils;

import com.casavieja.utils.enums.ReportE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class DataReport {
    private String name;
    private String tipo;
    private String url;
    private Map<String, Object> parametro = new HashMap<>();

    public DataReport(String tipo, String url) {
        this.tipo = tipo;
        this.url = url;
    }
    public DataReport(ReportE tipo, String url) {
        this.tipo = tipo.getValue();
        this.url = url;
    }
    public DataReport(String name, String tipo, String url) {
        this.name = name;
        this.tipo = tipo;
        this.url = url;
    }
    public DataReport(String name, ReportE tipo, String url) {
        this.name = name;
        this.tipo = tipo.getValue();
        this.url = url;
    }
    public void addParametro(String name, Object value) {
        this.parametro.put(name, value);
    }
}
