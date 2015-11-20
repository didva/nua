package com.dm.bookschecker.validator;

import java.util.Map;

public interface BaseValidator<T> {

    public Map<String, String> validate(T target);
}
