package com.dm.bookschecker.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class RestResultDTO {

    private boolean success = false;
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
    private String msg;

    public RestResultDTO(boolean success) {
        this.success = success;
    }

    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
