package com.assignment.dataupload.util;

import com.assignment.dataupload.model.MedicalRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.assignment.dataupload.constants.Constants.FILE_TYPE;

public class DataUploadUtil {
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!FILE_TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<MedicalRecord> csvToMedicalRecords(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        ) {
            List<MedicalRecord> medicalRecordList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            csvRecords.forEach(record -> {
                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setCode(record.get("code"));
                medicalRecord.setSource(record.get("source"));
                medicalRecord.setCodeList(record.get("codeListCode"));
                medicalRecord.setDisplayValue(record.get("displayValue"));
                medicalRecord.setLongDescription(record.get("longDescription"));
                medicalRecord.setFromDate(record.get("fromDate"));
                medicalRecord.setToDate(record.get("toDate"));
                medicalRecord.setSortingPriority(record.get("sortingPriority"));
                medicalRecordList.add(medicalRecord);
            });
            return medicalRecordList;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
        }
    }
}
