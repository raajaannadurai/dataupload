package com.assignment.dataupload.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "medical_record")
public class MedicalRecord {
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "source")
    private String source;
    @Column(name = "code_list")
    private String codeList;
    @Column(name = "display_value")
    private String displayValue;
    @Column(name = "long_description")
    private String longDescription;
    @Column(name = "from_date")
    private String fromDate;
    @Column(name = "to_date")
    private String toDate;
    @Column(name = "sorting_priority")
    private String sortingPriority;

}
