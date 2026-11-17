package com.hospital.model;

import com.hospital.model.enums.AppointmentStatus;
import java.io.Serializable;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String date;
    private String timeSlot;
    private String reason;
    private AppointmentStatus status;

    public Appointment() {}

    public Appointment(String id, String patientId, String patientName,
                       String doctorId, String doctorName, String date,
                       String timeSlot, String reason) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.reason = reason;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%s] Date: %s %s | Patient: %s (%s) -> Dr. %s (%s) | Status: %s",
                id, date, timeSlot, patientName, patientId, doctorName, doctorId, status);
    }
}
