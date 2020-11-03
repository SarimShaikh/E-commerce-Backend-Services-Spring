package com.inventory.sys.exceptions;

import java.io.Serializable;

public class CustomResponseDto implements Serializable {

    private String message;
    private String responseCode;
    private Object entityClass;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Object getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Object entityClass) {
        this.entityClass = entityClass;
    }
}
