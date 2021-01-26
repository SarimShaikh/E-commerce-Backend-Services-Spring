package com.usermangment.auth.messageDto.response;

import java.io.Serializable;

public class CustomResponseDTO implements Serializable {

    public CustomResponseDTO() {
    }

    public CustomResponseDTO(String message, String responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }

    private String message;
    private String responseCode;

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
}
