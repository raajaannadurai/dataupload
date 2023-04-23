package com.assignment.dataupload.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class DataUploadException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;
}
