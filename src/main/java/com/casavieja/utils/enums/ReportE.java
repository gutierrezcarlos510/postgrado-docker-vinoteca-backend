package com.casavieja.utils.enums;

import lombok.Getter;

@Getter
public enum ReportE {
    PDF("pdf"),
    EXCEL("xls");
    private final String value;
    private ReportE(String value) {
        this.value = value;
    }
}
