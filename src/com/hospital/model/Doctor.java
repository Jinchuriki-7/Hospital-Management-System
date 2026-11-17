package com.hospital.model;

import java.io.Serializable;

public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String specialization;
    private String contact;
    private double consultationFee;
    private String availableDays;
    private String availableHours;

    public Doctor() {}

    public Doctor(String id, String name, String specialization, String contact,
                  double consultationFee, String availableDays, String availableHours) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
        this.consultationFee = consultationFee;
        this.availableDays = availableDays;
        this.availableHours = availableHours;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }

    public String getAvailableDays() { return availableDays; }
    public void setAvailableDays(String availableDays) { this.availableDays = availableDays; }

    public String getAvailableHours() { return availableHours; }
    public void setAvailableHours(String availableHours) { this.availableHours = availableHours; }

    @Override
    public String toString() {
        return String.format("[%s] Dr. %s | %s | Fee: $%.2f | Schedule: %s (%s)",
                id, name, specialization, consultationFee, availableDays, availableHours);
    }
}
