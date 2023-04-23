package com.assignment.dataupload.exception;

import org.springframework.http.HttpStatus;

public class InvalidFileFormatException extends DataUploadException{
    public InvalidFileFormatException(HttpStatus httpStatus, String errorCode, String errorMessage) {
        super(httpStatus, errorCode, errorMessage);
    }
}
