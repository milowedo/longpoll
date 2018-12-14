package com.exceptionHandlingStuff;

import lombok.Data;

@Data
public class CustomerErrorResponse {

    private int status;
    private String message;
    private long timeStamp;
}
