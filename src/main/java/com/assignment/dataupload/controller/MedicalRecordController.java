package com.assignment.dataupload.controller;

import com.assignment.dataupload.constants.Constants;
import com.assignment.dataupload.exception.DataUploadException;
import com.assignment.dataupload.exception.InvalidFileFormatException;
import com.assignment.dataupload.exception.RecordNotFoundException;
import com.assignment.dataupload.model.MedicalRecord;
import com.assignment.dataupload.service.MedicalRecordService;
import com.assignment.dataupload.util.DataUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @PostMapping("/medical-records")
    public ResponseEntity<String> uploadMedicalRecords(@RequestParam("file") MultipartFile file) {
        if (DataUploadUtil.hasCSVFormat(file)) {
            try {
                medicalRecordService.saveMedicalRecord(file);
                return ResponseEntity.status(HttpStatus.CREATED).body(Constants.FILE_UPLOAD_SUCCESS);
            } catch (Exception e) {
                throw new DataUploadException(HttpStatus.BAD_REQUEST, Constants.UPLOAD_FAILED_ERROR_CODE,
                        String.format(Constants.UPLOAD_FAILED_ERROR_MESSAGE, file.getOriginalFilename()));
            }
        } else {
            throw new InvalidFileFormatException(HttpStatus.UNPROCESSABLE_ENTITY,
                    Constants.FILE_INVALID_ERROR_CODE, Constants.FILE_INVALID_ERROR_MESSAGE);
        }
    }

    @GetMapping("/medical-records")
    public ResponseEntity<List<MedicalRecord>> fetchAllMedicalRecords() {
        try {
            List<MedicalRecord> medicalRecords = medicalRecordService.fetchAllMedicalRecords();
            if (medicalRecords.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
        } catch (Exception e) {
            throw new DataUploadException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.FETCH_FAILED_ERROR_CODE,
                    Constants.FETCH_FAILED_ERROR_MESSAGE);
        }
    }

    @GetMapping("/medical-records/{code}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByCode(@PathVariable("code") String code) {
        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByCode(code);
        if (medicalRecord.isPresent()) {
            return new ResponseEntity<>(medicalRecord.get(), HttpStatus.OK);
        } else {
            throw new RecordNotFoundException(HttpStatus.NOT_FOUND, Constants.RECORD_NOT_FOUND_ERROR_CODE,
                    String.format(Constants.RECORD_NOT_FOUND_ERROR_MESSAGE, code));
        }
    }

    @DeleteMapping("/medical-records")
    public ResponseEntity<String> deleteAllMedicalRecords() {
        try {
            medicalRecordService.deleteAllMedicalRecords();
            return ResponseEntity.status(HttpStatus.OK).body(Constants.RECORD_DELETE_SUCCESS);
        } catch (Exception e) {
            throw new DataUploadException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.DELETE_FAILED_ERROR_CODE,
                    Constants.DELETE_FAILED_ERROR_MESSAGE);
        }
    }

}