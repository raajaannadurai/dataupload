package com.assignment.dataupload.constants;

public class Constants {

    public static final String FILE_TYPE = "text/csv";
    public static final String FILE_UPLOAD_SUCCESS = "Uploaded the file successfully!";
    public static final String RECORD_DELETE_SUCCESS = "Deleted all records successfully!";
    public static final String RECORD_NOT_AVAILABLE = "There is no record available!";
    public static final String UPLOAD_FAILED_ERROR_CODE = "FILE_UPLOAD_FAILED";
    public static final String FILE_INVALID_ERROR_CODE = "INVALID_FILE_FORMAT";
    public static final String RECORD_NOT_FOUND_ERROR_CODE = "MEDICAL_RECORD_NOT_FOUND";
    public static final String DELETE_FAILED_ERROR_CODE = "RECORD_DELETE_FAILED";
    public static final String FETCH_FAILED_ERROR_CODE = "RECORD_FETCH_FAILED";


    public static final String UPLOAD_FAILED_ERROR_MESSAGE = "Could not upload the file : %s";
    public static final String FILE_INVALID_ERROR_MESSAGE = "Invalid file format. Please upload a csv file";
    public static final String RECORD_NOT_FOUND_ERROR_MESSAGE = "Medical record not found for the code : %s";
    public static final String DELETE_FAILED_ERROR_MESSAGE = "Error while deleting the records";
    public static final String FETCH_FAILED_ERROR_MESSAGE = "Error while fetching the records";
    public static final String INPUT_FILE_VALIDATION_MESSAGE = "Input file is missing!";
}
