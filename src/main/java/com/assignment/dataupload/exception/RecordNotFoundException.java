package com.assignment.dataupload.exception;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends DataUploadException {
    public RecordNotFoundException(HttpStatus httpStatus, String errorCode, String errorMessage) {
        super(httpStatus, errorCode, errorMessage);
    }
}
