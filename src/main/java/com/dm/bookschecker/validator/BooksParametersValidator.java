package com.dm.bookschecker.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.dm.bookschecker.domain.dto.BooksSearchParameters;
import com.dm.bookschecker.domain.dto.OrderType;
import com.dm.bookschecker.domain.model.Book;

@Component
public class BooksParametersValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BooksSearchParameters.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        BooksSearchParameters target = (BooksSearchParameters) object;
        if (!validateOrder(target)) {
            errors.rejectValue("order", "invalid.order", "Invalid order");
        }
        if (target.getLimit() <= 0) {
            errors.rejectValue("limit", "invalid.limit", "Invalid limit");
        }
        if (target.getSkip() < 0) {
            errors.rejectValue("skip", "invalid.skip", "Invalid skip");
        }
        if (!validateOrderByField(target)) {
            errors.rejectValue("orderBy", "invalid.orderBy", "Invalid orderBy");
        }
    }

    private boolean validateOrderByField(BooksSearchParameters target) {
        try {
            Book.class.getDeclaredField(target.getOrderBy());
        } catch (NoSuchFieldException e) {
            return false;
        }
        return true;
    }

    private boolean validateOrder(BooksSearchParameters target) {
        String order = target.getOrder();
        if (order == null) {
            return false;
        }
        order = order.toUpperCase();
        for (OrderType value : OrderType.values()) {
            if (value.toString().equals(order)) {
                return true;
            }
        }
        return false;
    }
}
