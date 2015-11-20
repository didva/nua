package com.dm.bookschecker.controller.rest;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dm.bookschecker.domain.dto.RestResultDTO;
import com.dm.bookschecker.exception.IllegalRestArguments;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(IllegalRestArguments.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResultDTO processValidationError(IllegalRestArguments ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResultDTO processConstraintException(Exception ex) {
        RestResultDTO restResultDTO = new RestResultDTO(false);
        restResultDTO.setMsg(ex.getMessage());
        return restResultDTO;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResultDTO processException(Exception ex) {
        RestResultDTO restResultDTO = new RestResultDTO(false);
        restResultDTO.setMsg(ex.getMessage());
        return restResultDTO;
    }

    private RestResultDTO processFieldErrors(List<FieldError> fieldErrors) {
        RestResultDTO dto = new RestResultDTO(false);
        for (FieldError fieldError : fieldErrors) {
            dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }
}