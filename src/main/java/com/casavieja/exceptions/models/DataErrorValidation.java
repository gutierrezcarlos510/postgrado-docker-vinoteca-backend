package com.casavieja.exceptions.models;

import java.util.ArrayList;
import java.util.List;

public class DataErrorValidation {
    private List<FieldInvalidate> fields;

    public DataErrorValidation() {
        this.fields = new ArrayList<>();
    }

    public void addDataError(String field, String msg) {
        this.fields.add(new FieldInvalidate(field, msg));
    }

    public List<FieldInvalidate> getFields() {
        return fields;
    }

    public void setFields(List<FieldInvalidate> fields) {
        this.fields = fields;
    }
}
