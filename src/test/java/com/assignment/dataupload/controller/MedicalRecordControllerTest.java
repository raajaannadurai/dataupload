package com.assignment.dataupload.controller;

import com.assignment.dataupload.model.MedicalRecord;
import com.assignment.dataupload.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void MedicalRecordController_UploadData_ReturnCreated() throws Exception {
        String fileName = "test.csv";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                fileName,
                "text/csv",
                ("\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"\n" +
                        "\"ZIB\",\"ZIB001\",\"Type 1\",\"Test\",\"\",\"01-01-2019\",\"\",\"\"").getBytes()
        );
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/api/v1/medical-records");
        mockMvc.perform(multipartRequest.file(mockMultipartFile))
                .andExpect(status().isCreated());
    }

    @Test
    public void MedicalRecordController_UploadData_ReturnInvalidException() throws Exception {
        String fileName = "test.pdf";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                fileName,
                "text/pdf",
                ("Test Pdf").getBytes()
        );
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/api/v1/medical-records");
        mockMvc.perform(multipartRequest.file(mockMultipartFile))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void MedicalRecordController_UploadData_ReturnBadRequest() throws Exception {
        String fileName = "test.csv";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                fileName,
                "text/csv",
                ("\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"\n" +
                        "\"ZIB\",\"ZIB001\",\"Type 1\",\"Test\",\"\",\"01-01-2019\",\"\",\"\"").getBytes()
        );
        doThrow(new IOException()).when(medicalRecordService).saveMedicalRecord(mockMultipartFile);
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/api/v1/medical-records");
        mockMvc.perform(multipartRequest.file(mockMultipartFile))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void MedicalRecordController_FetchAll_ReturnSuccess() throws Exception {
        MedicalRecord medicalRecord = getMedicalRecord();
        when(medicalRecordService.fetchAllMedicalRecords()).thenReturn(List.of(medicalRecord));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medical-records"))
                .andExpect(status().isOk());
    }

    @Test
    public void MedicalRecordController_FetchAll_ReturnNoContent() throws Exception {
        when(medicalRecordService.fetchAllMedicalRecords()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medical-records"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void MedicalRecordController_GetRecordByCode_ReturnSuccess() throws Exception {
        MedicalRecord medicalRecord = getMedicalRecord();
        when(medicalRecordService.getMedicalRecordByCode("101")).thenReturn(Optional.of(medicalRecord));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medical-records/{code}", "101"))
                .andExpect(status().isOk());
    }

    @Test
    public void MedicalRecordController_GetRecordByCode_ReturnNoFound() throws Exception {
        when(medicalRecordService.getMedicalRecordByCode("101")).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medical-records/{code}", "101"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void MedicalRecordController_DeleteAll_ReturnSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/medical-records"))
                .andExpect(status().isOk());
    }

    @Test
    public void MedicalRecordController_DeleteAll_ReturnServerError() throws Exception {
        doThrow(new RuntimeException()).when(medicalRecordService).deleteAllMedicalRecords();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/medical-records"))
                .andExpect(status().isInternalServerError());
    }

    private MedicalRecord getMedicalRecord(){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setCode("101");
        medicalRecord.setCodeList("101-L01");
        medicalRecord.setSource("ZIB");
        medicalRecord.setDisplayValue("ZIB00001");
        return medicalRecord;
    }

}
