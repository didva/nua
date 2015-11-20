package com.dm.bookschecker.exception;

import org.springframework.validation.BindingResult;

public class IllegalRestArguments extends RuntimeException {

    private BindingResult bindingResult;

    public IllegalRestArguments(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
