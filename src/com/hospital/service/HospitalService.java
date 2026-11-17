package com.hospital.service;

import com.hospital.model.*;
import com.hospital.model.enums.AppointmentStatus;
import com.hospital.model.enums.Gender;
import com.hospital.model.enums.PaymentStatus;
import com.hospital.util.DataPersistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class HospitalService {
    private final DataPersistence database;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public HospitalService() {
        this.database = DataPersistence.load();
    }

    public synchronized void save() {
        database.save();
    }

    // ================= PATIENT OPERATIONS =================

    public Patient registerPatient(String name, int age, Gender gender, String contact,
                                   String bloodGroup, String address, String ailment) {
        String id = database.generatePatientId();
        Patient patient = new Patient(id, name, age, gender, contact, bloodGroup, address, ailment);
        database.getPatients().put(id, patient);
        save();
        return patient;
    }

    public Collection<Patient> getAllPatients() {
        return database.getPatients().values();
    }

    public Patient getPatientById(String id) {
        if (id == null) return null;
        return database.getPatients().get(id.toUpperCase().trim());
    }

    public List<Patient> searchPatients(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(database.getPatients().values());
        }
        String q = query.toLowerCase().trim();
        return database.getPatients().values().stream()
                .filter(p -> p.getId().toLowerCase().contains(q) ||
                             p.getName().toLowerCase().contains(q) ||
                             p.getContact().toLowerCase().contains(q) ||
                             p.getAilment().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public boolean admitPatient(String patientId, String roomNumber) {
        Patient p = getPatientById(patientId);
        if (p == null) return false;
        String today = LocalDate.now().format(dateFormatter);
        p.admit(roomNumber, today);
        save();
        return true;
    }

    public boolean dischargePatient(String patientId) {
        Patient p = getPatientById(patientId);
        if (p == null || !p.isAdmitted()) return false;
        p.discharge();
        save();
        return true;
    }

    // ================= DOCTOR OPERATIONS =================

    public Doctor registerDoctor(String name, String specialization, String contact,
                                 double fee, String days, String hours) {
        String id = database.generateDoctorId();
        Doctor doctor = new Doctor(id, name, specialization, contact, fee, days, hours);
        database.getDoctors().put(id, doctor);
        save();
        return doctor;
    }

    public Collection<Doctor> getAllDoctors() {
        return database.getDoctors().values();
    }

    public Doctor getDoctorById(String id) {
        if (id == null) return null;
        return database.getDoctors().get(id.toUpperCase().trim());
    }

    public List<Doctor> searchDoctors(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(database.getDoctors().values());
        }
        String q = query.toLowerCase().trim();
        return database.getDoctors().values().stream()
                .filter(d -> d.getId().toLowerCase().contains(q) ||
                             d.getName().toLowerCase().contains(q) ||
                             d.getSpecialization().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    // ================= APPOINTMENT OPERATIONS =================

    public Appointment bookAppointment(String patientId, String doctorId, String date, String timeSlot, String reason) {
        Patient patient = getPatientById(patientId);
        Doctor doctor = getDoctorById(doctorId);
        if (patient == null || doctor == null) return null;

        String id = database.generateAppointmentId();
        Appointment appointment = new Appointment(id, patient.getId(), patient.getName(),
                doctor.getId(), doctor.getName(), date, timeSlot, reason);
        database.getAppointments().put(id, appointment);
        save();
        return appointment;
    }

    public Collection<Appointment> getAllAppointments() {
        return database.getAppointments().values();
    }

    public Appointment getAppointmentById(String id) {
        if (id == null) return null;
        return database.getAppointments().get(id.toUpperCase().trim());
    }

    public boolean updateAppointmentStatus(String appointmentId, AppointmentStatus newStatus) {
        Appointment app = getAppointmentById(appointmentId);
        if (app == null) return false;
        app.setStatus(newStatus);
        save();
        return true;
    }

    public List<Appointment> getAppointmentsForPatient(String patientId) {
        return database.getAppointments().values().stream()
                .filter(a -> a.getPatientId().equalsIgnoreCase(patientId))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorId) {
        return database.getAppointments().values().stream()
                .filter(a -> a.getDoctorId().equalsIgnoreCase(doctorId))
                .collect(Collectors.toList());
    }

    // ================= MEDICAL RECORD OPERATIONS =================

    public MedicalRecord addMedicalRecord(String patientId, String doctorName, String diagnosis, String prescription, String notes) {
        Patient p = getPatientById(patientId);
        if (p == null) return null;

        String id = database.generateRecordId();
        String date = LocalDate.now().format(dateFormatter);
        MedicalRecord record = new MedicalRecord(id, patientId, doctorName, date, diagnosis, prescription, notes);
        database.getMedicalRecords().add(record);
        save();
        return record;
    }

    public List<MedicalRecord> getPatientMedicalHistory(String patientId) {
        return database.getMedicalRecords().stream()
                .filter(r -> r.getPatientId().equalsIgnoreCase(patientId))
                .collect(Collectors.toList());
    }

    // ================= BILLING OPERATIONS =================

    public Bill generateBill(String patientId, double consultationFee, double medicineCharges, double roomCharges) {
        Patient patient = getPatientById(patientId);
        if (patient == null) return null;

        String id = database.generateBillId();
        String date = LocalDate.now().format(dateFormatter);
        Bill bill = new Bill(id, patient.getId(), patient.getName(), consultationFee, medicineCharges, roomCharges, date);
        database.getBills().put(id, bill);
        save();
        return bill;
    }

    public Collection<Bill> getAllBills() {
        return database.getBills().values();
    }

    public Bill getBillById(String id) {
        if (id == null) return null;
        return database.getBills().get(id.toUpperCase().trim());
    }

    public boolean payBill(String billId) {
        Bill b = getBillById(billId);
        if (b == null || b.getStatus() == PaymentStatus.PAID) return false;
        String today = LocalDate.now().format(dateFormatter);
        b.markAsPaid(today);
        save();
        return true;
    }

    public List<Bill> getBillsForPatient(String patientId) {
        return database.getBills().values().stream()
                .filter(b -> b.getPatientId().equalsIgnoreCase(patientId))
                .collect(Collectors.toList());
    }

    // ================= SYSTEM STATS =================

    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("Total Patients", database.getPatients().size());
        long admittedCount = database.getPatients().values().stream().filter(Patient::isAdmitted).count();
        stats.put("Admitted Inpatients", admittedCount);
        stats.put("Total Doctors", database.getDoctors().size());
        long scheduledApts = database.getAppointments().values().stream()
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED).count();
        stats.put("Scheduled Appointments", scheduledApts);

        double totalRevenue = database.getBills().values().stream()
                .filter(b -> b.getStatus() == PaymentStatus.PAID)
                .mapToDouble(Bill::getTotalAmount).sum();
        double pendingRevenue = database.getBills().values().stream()
                .filter(b -> b.getStatus() == PaymentStatus.PENDING)
                .mapToDouble(Bill::getTotalAmount).sum();

        stats.put("Total Revenue Paid ($)", String.format("%.2f", totalRevenue));
        stats.put("Pending Dues ($)", String.format("%.2f", pendingRevenue));
        return stats;
    }
}
