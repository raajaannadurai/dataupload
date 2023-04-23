package com.assignment.dataupload.service;

import com.assignment.dataupload.model.MedicalRecord;
import com.assignment.dataupload.repository.MedicalRecordRepository;
import com.assignment.dataupload.util.DataUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> fetchAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public void deleteAllMedicalRecords() {
        medicalRecordRepository.deleteAll();
    }

    public Optional<MedicalRecord> getMedicalRecordByCode(String code) {
        return medicalRecordRepository.findById(code);
    }

    public void saveMedicalRecord(MultipartFile file) throws IOException {
        List<MedicalRecord> medicalRecords = DataUploadUtil.csvToMedicalRecords(file.getInputStream());
        medicalRecordRepository.saveAll(medicalRecords);
    }
}

