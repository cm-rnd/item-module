package com.tmax.commerce.itemmodule.common.exception;


import jakarta.validation.*;

import java.util.Set;

/**
 * Controller 단에서 Service를 호출 할 때
 * Request에 대한 Validation을 한번에 처리하기 위해
 * 만들어진 DTO에 대한 Validation을 해당 클래스를 구현하는 것으로 보장한다.
 **/

public abstract class SelfValidating<T> {

    private final Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
