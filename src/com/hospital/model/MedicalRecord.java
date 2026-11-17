package com.hospital.model;

import java.io.Serializable;

public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String patientId;
    private String doctorName;
    private String date;
    private String diagnosis;
    private String prescription;
    private String notes;

    public MedicalRecord() {}

    public MedicalRecord(String id, String patientId, String doctorName, String date,
                         String diagnosis, String prescription, String notes) {
        this.id = id;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return String.format("[%s] Date: %s | Dr. %s | Diagnosis: %s | Rx: %s",
                id, date, doctorName, diagnosis, prescription);
    }
}
