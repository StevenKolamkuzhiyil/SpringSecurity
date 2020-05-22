package com.stevenkolamkuzhiyil.TicTacToe.exception;

import java.util.List;
import java.util.Map;

public class InvalidObject {

    private String name;
    private List<String> errors;
    private Map<String, String> fieldErrors;

    public InvalidObject() {
    }

    public InvalidObject(String name, List<String> errors, Map<String, String> fieldErrors) {
        this.name = name;
        this.errors = errors;
        this.fieldErrors = fieldErrors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
