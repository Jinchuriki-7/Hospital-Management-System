package com.hospital.util;

import com.hospital.model.*;
import com.hospital.model.enums.Gender;

import java.io.*;
import java.util.*;

public class DataPersistence implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DATA_FILE = "data/hospital_store.dat";

    private Map<String, Patient> patients = new LinkedHashMap<>();
    private Map<String, Doctor> doctors = new LinkedHashMap<>();
    private Map<String, Appointment> appointments = new LinkedHashMap<>();
    private Map<String, Bill> bills = new LinkedHashMap<>();
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    private int nextPatientId = 101;
    private int nextDoctorId = 201;
    private int nextAppointmentId = 301;
    private int nextBillId = 401;
    private int nextRecordId = 501;

    public DataPersistence() {}

    public static DataPersistence load() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                DataPersistence data = (DataPersistence) ois.readObject();
                return data;
            } catch (Exception e) {
                System.out.println("Could not load saved data. Initializing fresh dataset. (" + e.getMessage() + ")");
            }
        }
        DataPersistence fresh = new DataPersistence();
        fresh.seedInitialData();
        fresh.save();
        return fresh;
    }

    public synchronized void save() {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                oos.writeObject(this);
            }
        } catch (IOException e) {
            System.err.println("Warning: Failed to save hospital data: " + e.getMessage());
        }
    }

    public void seedInitialData() {
        // Seed Doctors
        Doctor d1 = new Doctor("DOC-201", "Dr. Ayesha Rahman", "Cardiology", "+8801711000001", 1200.0, "Mon-Thu", "09:00 AM - 02:00 PM");
        Doctor d2 = new Doctor("DOC-202", "Dr. Tanvir Ahmed", "Neurology", "+8801711000002", 1500.0, "Sun-Wed", "10:00 AM - 03:00 PM");
        Doctor d3 = new Doctor("DOC-203", "Dr. Nusrat Jahan", "Pediatrics", "+8801711000003", 900.0, "Everyday", "04:00 PM - 09:00 PM");
        Doctor d4 = new Doctor("DOC-204", "Dr. Rafiqul Islam", "Orthopedics", "+8801711000004", 1100.0, "Mon-Fri", "11:00 AM - 05:00 PM");
        Doctor d5 = new Doctor("DOC-205", "Dr. Farhana Yasmin", "Dermatology", "+8801711000005", 800.0, "Tue-Sat", "02:00 PM - 07:00 PM");

        doctors.put(d1.getId(), d1);
        doctors.put(d2.getId(), d2);
        doctors.put(d3.getId(), d3);
        doctors.put(d4.getId(), d4);
        doctors.put(d5.getId(), d5);
        nextDoctorId = 206;

        // Seed Patients
        Patient p1 = new Patient("PAT-101", "Kazi Hasan", 45, Gender.MALE, "+8801812345601", "B+", "Dhanmondi, Dhaka", "Chest tightness & hypertension");
        p1.admit("ICU-04", "2026-11-15");

        Patient p2 = new Patient("PAT-102", "Sadia Chowdhury", 28, Gender.FEMALE, "+8801812345602", "O+", "Gulshan, Dhaka", "Chronic Migraine");
        Patient p3 = new Patient("PAT-103", "Anik Barua", 12, Gender.MALE, "+8801812345603", "A+", "Uttara, Dhaka", "Seasonal viral fever & cough");
        Patient p4 = new Patient("PAT-104", "Farida Begum", 62, Gender.FEMALE, "+8801812345604", "AB+", "Mirpur, Dhaka", "Knee joint arthritis");

        patients.put(p1.getId(), p1);
        patients.put(p2.getId(), p2);
        patients.put(p3.getId(), p3);
        patients.put(p4.getId(), p4);
        nextPatientId = 105;

        // Seed Appointments
        Appointment a1 = new Appointment("APT-301", p2.getId(), p2.getName(), d2.getId(), d2.getName(), "2026-11-20", "10:30 AM", "Migraine follow-up");
        Appointment a2 = new Appointment("APT-302", p3.getId(), p3.getName(), d3.getId(), d3.getName(), "2026-11-21", "04:30 PM", "High fever check");
        appointments.put(a1.getId(), a1);
        appointments.put(a2.getId(), a2);
        nextAppointmentId = 303;

        // Seed Medical Records
        MedicalRecord mr1 = new MedicalRecord("REC-501", p1.getId(), d1.getName(), "2026-11-15", "Stage 2 Hypertension", "Tab. Amlodipine 5mg once daily", "Monitor BP morning and evening.");
        MedicalRecord mr2 = new MedicalRecord("REC-502", p2.getId(), d2.getName(), "2026-11-10", "Migraine with Aura", "Tab. Sumatriptan 50mg PRN, Naproxen 250mg", "Avoid bright lights, adequate sleep.");
        medicalRecords.add(mr1);
        medicalRecords.add(mr2);
        nextRecordId = 503;

        // Seed Bills
        Bill b1 = new Bill("BIL-401", p1.getId(), p1.getName(), 1200.0, 850.0, 3000.0, "2026-11-16");
        bills.put(b1.getId(), b1);
        nextBillId = 402;
    }

    public Map<String, Patient> getPatients() { return patients; }
    public Map<String, Doctor> getDoctors() { return doctors; }
    public Map<String, Appointment> getAppointments() { return appointments; }
    public Map<String, Bill> getBills() { return bills; }
    public List<MedicalRecord> getMedicalRecords() { return medicalRecords; }

    public String generatePatientId() { return "PAT-" + (nextPatientId++); }
    public String generateDoctorId() { return "DOC-" + (nextDoctorId++); }
    public String generateAppointmentId() { return "APT-" + (nextAppointmentId++); }
    public String generateBillId() { return "BIL-" + (nextBillId++); }
    public String generateRecordId() { return "REC-" + (nextRecordId++); }
}
