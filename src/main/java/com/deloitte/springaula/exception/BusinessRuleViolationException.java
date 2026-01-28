package com.deloitte.springaula.exception;

import java.util.Map;

public class BusinessRuleViolationException extends RuntimeException {
    private final Map<String, String> errors;

    public BusinessRuleViolationException(Map<String, String> errors) {
        super("Regras de negócio violadas");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
