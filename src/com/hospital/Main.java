package com.hospital;

import com.hospital.model.*;
import com.hospital.model.enums.AppointmentStatus;
import com.hospital.model.enums.Gender;
import com.hospital.model.enums.PaymentStatus;
import com.hospital.service.HospitalService;
import com.hospital.util.ConsoleHelper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Main {
    private static final HospitalService service = new HospitalService();

    public static void main(String[] args) {
        showWelcomeBanner();

        boolean running = true;
        while (running) {
            ConsoleHelper.printHeader("Hospital Management System - Main Menu");
            System.out.println("  1. " + ConsoleHelper.BOLD + "Patient Management" + ConsoleHelper.RESET + "   (Register, View, Search, Admit/Discharge)");
            System.out.println("  2. " + ConsoleHelper.BOLD + "Doctor Management" + ConsoleHelper.RESET + "    (Add Doctor, View List, Search Specialties)");
            System.out.println("  3. " + ConsoleHelper.BOLD + "Appointments" + ConsoleHelper.RESET + "         (Book, View, Reschedule, Cancel, Complete)");
            System.out.println("  4. " + ConsoleHelper.BOLD + "Medical Records" + ConsoleHelper.RESET + "      (Prescriptions, Diagnoses, Patient History)");
            System.out.println("  5. " + ConsoleHelper.BOLD + "Billing & Invoicing" + ConsoleHelper.RESET + "  (Generate Invoices, Settle Bills, Receipts)");
            System.out.println("  6. " + ConsoleHelper.BOLD + "Hospital Analytics" + ConsoleHelper.RESET + "  (Overview Stats & Live Metrics)");
            System.out.println("  0. " + ConsoleHelper.RED + "Exit Application" + ConsoleHelper.RESET);
            System.out.println("------------------------------------------------------------------------------------------");

            int choice = ConsoleHelper.readInt("Select an option [0-6]", 0, 6);
            switch (choice) {
                case 1 -> patientMenu();
                case 2 -> doctorMenu();
                case 3 -> appointmentMenu();
                case 4 -> medicalRecordMenu();
                case 5 -> billingMenu();
                case 6 -> showDashboard();
                case 0 -> {
                    service.save();
                    ConsoleHelper.printSuccess("All changes saved successfully. Thank you for using Hospital Management System!");
                    running = false;
                }
            }
        }
    }

    private static void showWelcomeBanner() {
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD);
        System.out.println("  +==================================================================================+");
        System.out.println("  |                                                                                  |");
        System.out.println("  |     _    _  ____   _____ _____ _____ _______       _      _____                  |");
        System.out.println("  |    | |  | |/ __ \\ / ____|  __ \\_   _|__   __|/\\   | |    / ____|                 |");
        System.out.println("  |    | |__| | |  | | (___ | |__) || |    | |  /  \\  | |   | (___                   |");
        System.out.println("  |    |  __  | |  | |\\___ \\|  ___/ | |    | | / /\\ \\ | |    \\___ \\                  |");
        System.out.println("  |    | |  | | |__| |____) | |    _| |_   | |/ ____ \\| |____ ____) |                 |");
        System.out.println("  |    |_|  |_|\\____/|_____/|_|   |_____|  |_/_/    \\_\\______|_____/                  |");
        System.out.println("  |                                                                                  |");
        System.out.println("  |                   HOSPITAL MANAGEMENT CONSOLE SYSTEM v2.0                        |");
        System.out.println("  |                      Designed & Developed with Pure Java                         |");
        System.out.println("  +==================================================================================+");
        System.out.println(ConsoleHelper.RESET);
    }

    // ==========================================
    // PATIENT MENU
    // ==========================================
    private static void patientMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("Patient Management");
            System.out.println("  1. Register New Patient");
            System.out.println("  2. View All Patients");
            System.out.println("  3. Search Patient");
            System.out.println("  4. Admit Patient (Allocate Bed/Room)");
            System.out.println("  5. Discharge Patient");
            System.out.println("  0. Back to Main Menu");
            System.out.println("------------------------------------------------------------------------------------------");

            int choice = ConsoleHelper.readInt("Select an option [0-5]", 0, 5);
            switch (choice) {
                case 1 -> {
                    ConsoleHelper.printSubHeader("Register New Patient");
                    String name = ConsoleHelper.readString("Enter Full Name");
                    int age = ConsoleHelper.readInt("Enter Age", 0, 130);
                    System.out.println("Gender options: 1. MALE, 2. FEMALE, 3. OTHER");
                    int gChoice = ConsoleHelper.readInt("Select Gender [1-3]", 1, 3);
                    Gender gender = gChoice == 1 ? Gender.MALE : (gChoice == 2 ? Gender.FEMALE : Gender.OTHER);
                    String contact = ConsoleHelper.readString("Enter Phone Number");
                    String blood = ConsoleHelper.readString("Enter Blood Group (e.g. A+, B+, O+)");
                    String address = ConsoleHelper.readString("Enter City / Address");
                    String ailment = ConsoleHelper.readString("Enter Primary Ailment / Symptoms");

                    Patient p = service.registerPatient(name, age, gender, contact, blood, address, ailment);
                    ConsoleHelper.printSuccess("Patient registered successfully! Assigned ID: " + p.getId());
                    ConsoleHelper.pressEnterToContinue();
                }
                case 2 -> {
                    printPatientsTable(service.getAllPatients());
                    ConsoleHelper.pressEnterToContinue();
                }
                case 3 -> {
                    String query = ConsoleHelper.readString("Enter Patient ID or Name to search");
                    List<Patient> found = service.searchPatients(query);
                    if (found.isEmpty()) {
                        ConsoleHelper.printWarning("No patients matching '" + query + "' found.");
                    } else {
                        printPatientsTable(found);
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 4 -> {
                    ConsoleHelper.printSubHeader("Admit Patient");
                    String id = ConsoleHelper.readString("Enter Patient ID");
                    Patient p = service.getPatientById(id);
                    if (p == null) {
                        ConsoleHelper.printError("Patient not found with ID: " + id);
                    } else if (p.isAdmitted()) {
                        ConsoleHelper.printWarning("Patient is already admitted to room " + p.getRoomNumber());
                    } else {
                        String room = ConsoleHelper.readString("Enter Room / Bed Number (e.g. WARD-12, ICU-02)");
                        service.admitPatient(id, room);
                        ConsoleHelper.printSuccess("Patient " + p.getName() + " admitted to room " + room);
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 5 -> {
                    ConsoleHelper.printSubHeader("Discharge Patient");
                    String id = ConsoleHelper.readString("Enter Patient ID");
                    Patient p = service.getPatientById(id);
                    if (p == null) {
                        ConsoleHelper.printError("Patient not found with ID: " + id);
                    } else if (!p.isAdmitted()) {
                        ConsoleHelper.printWarning("Patient is currently not admitted as an inpatient.");
                    } else {
                        service.dischargePatient(id);
                        ConsoleHelper.printSuccess("Patient " + p.getName() + " has been discharged successfully.");
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 0 -> back = true;
            }
        }
    }

    private static void printPatientsTable(Collection<Patient> list) {
        ConsoleHelper.printSubHeader("Patient Directory (" + list.size() + " total)");
        System.out.println("+------------+-------------------------+-----+--------+-------+------------------+------------------------------+");
        System.out.println("| ID         | Full Name               | Age | Gender | Blood | Contact          | Status & Location            |");
        System.out.println("+------------+-------------------------+-----+--------+-------+------------------+------------------------------+");
        for (Patient p : list) {
            String status = p.isAdmitted() ? ("Admitted (" + p.getRoomNumber() + ")") : "Outpatient";
            System.out.printf("| %-10s | %-23s | %-3d | %-6s | %-5s | %-16s | %-28s |\n",
                    p.getId(), truncate(p.getName(), 23), p.getAge(), p.getGender(),
                    p.getBloodGroup(), truncate(p.getContact(), 16), truncate(status, 28));
        }
        System.out.println("+------------+-------------------------+-----+--------+-------+------------------+------------------------------+");
    }

    // ==========================================
    // DOCTOR MENU
    // ==========================================
    private static void doctorMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("Doctor Management");
            System.out.println("  1. Add New Doctor");
            System.out.println("  2. View All Doctors");
            System.out.println("  3. Search Doctor (by Name or Specialization)");
            System.out.println("  0. Back to Main Menu");
            System.out.println("------------------------------------------------------------------------------------------");

            int choice = ConsoleHelper.readInt("Select an option [0-3]", 0, 3);
            switch (choice) {
                case 1 -> {
                    ConsoleHelper.printSubHeader("Add New Doctor");
                    String name = ConsoleHelper.readString("Enter Doctor Name (without 'Dr.')");
                    String spec = ConsoleHelper.readString("Enter Specialization (e.g. Cardiology, Neurology, General)");
                    String contact = ConsoleHelper.readString("Enter Contact Number");
                    double fee = ConsoleHelper.readDouble("Enter Consultation Fee ($)", 0.0);
                    String days = ConsoleHelper.readString("Enter Available Days (e.g. Mon-Thu, Sun-Wed)");
                    String hours = ConsoleHelper.readString("Enter Working Hours (e.g. 10:00 AM - 04:00 PM)");

                    Doctor doc = service.registerDoctor(name, spec, contact, fee, days, hours);
                    ConsoleHelper.printSuccess("Doctor registered successfully! Assigned ID: " + doc.getId());
                    ConsoleHelper.pressEnterToContinue();
                }
                case 2 -> {
                    printDoctorsTable(service.getAllDoctors());
                    ConsoleHelper.pressEnterToContinue();
                }
                case 3 -> {
                    String query = ConsoleHelper.readString("Enter Doctor Name or Specialization");
                    List<Doctor> found = service.searchDoctors(query);
                    if (found.isEmpty()) {
                        ConsoleHelper.printWarning("No doctors found matching '" + query + "'.");
                    } else {
                        printDoctorsTable(found);
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 0 -> back = true;
            }
        }
    }

    private static void printDoctorsTable(Collection<Doctor> list) {
        ConsoleHelper.printSubHeader("Doctors Directory (" + list.size() + " total)");
        System.out.println("+------------+-------------------------+----------------------+------------+------------+-----------------------+");
        System.out.println("| ID         | Name                    | Specialization       | Fee ($)    | Days       | Visiting Hours        |");
        System.out.println("+------------+-------------------------+----------------------+------------+------------+-----------------------+");
        for (Doctor d : list) {
            System.out.printf("| %-10s | %-23s | %-20s | %-10.2f | %-10s | %-21s |\n",
                    d.getId(), truncate(d.getName(), 23), truncate(d.getSpecialization(), 20),
                    d.getConsultationFee(), truncate(d.getAvailableDays(), 10), truncate(d.getAvailableHours(), 21));
        }
        System.out.println("+------------+-------------------------+----------------------+------------+------------+-----------------------+");
    }

    // ==========================================
    // APPOINTMENT MENU
    // ==========================================
    private static void appointmentMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("Appointment Scheduling");
            System.out.println("  1. Book New Appointment");
            System.out.println("  2. View All Appointments");
            System.out.println("  3. View Appointments by Patient ID");
            System.out.println("  4. View Appointments by Doctor ID");
            System.out.println("  5. Mark Appointment as Completed");
            System.out.println("  6. Cancel Appointment");
            System.out.println("  0. Back to Main Menu");
            System.out.println("------------------------------------------------------------------------------------------");

            int choice = ConsoleHelper.readInt("Select an option [0-6]", 0, 6);
            switch (choice) {
                case 1 -> {
                    ConsoleHelper.printSubHeader("Book New Appointment");
                    String pId = ConsoleHelper.readString("Enter Patient ID (e.g. PAT-101)");
                    Patient patient = service.getPatientById(pId);
                    if (patient == null) {
                        ConsoleHelper.printError("Patient not found! Please register the patient first.");
                        ConsoleHelper.pressEnterToContinue();
                        break;
                    }

                    String dId = ConsoleHelper.readString("Enter Doctor ID (e.g. DOC-201)");
                    Doctor doctor = service.getDoctorById(dId);
                    if (doctor == null) {
                        ConsoleHelper.printError("Doctor not found with ID: " + dId);
                        ConsoleHelper.pressEnterToContinue();
                        break;
                    }

                    String date = ConsoleHelper.readString("Enter Appointment Date (YYYY-MM-DD)");
                    String time = ConsoleHelper.readString("Enter Preferred Time Slot (e.g. 10:30 AM)");
                    String reason = ConsoleHelper.readString("Enter Reason for Visit");

                    Appointment apt = service.bookAppointment(patient.getId(), doctor.getId(), date, time, reason);
                    ConsoleHelper.printSuccess("Appointment successfully booked! Appointment ID: " + apt.getId());
                    ConsoleHelper.pressEnterToContinue();
                }
                case 2 -> {
                    printAppointmentsTable(service.getAllAppointments());
                    ConsoleHelper.pressEnterToContinue();
                }
                case 3 -> {
                    String pId = ConsoleHelper.readString("Enter Patient ID");
                    List<Appointment> list = service.getAppointmentsForPatient(pId);
                    if (list.isEmpty()) {
                        ConsoleHelper.printWarning("No appointments found for patient: " + pId);
                    } else {
                        printAppointmentsTable(list);
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 4 -> {
                    String dId = ConsoleHelper.readString("Enter Doctor ID");
                    List<Appointment> list = service.getAppointmentsForDoctor(dId);
                    if (list.isEmpty()) {
                        ConsoleHelper.printWarning("No appointments found for doctor: " + dId);
                    } else {
                        printAppointmentsTable(list);
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 5 -> {
                    String aId = ConsoleHelper.readString("Enter Appointment ID to complete");
                    if (service.updateAppointmentStatus(aId, AppointmentStatus.COMPLETED)) {
                        ConsoleHelper.printSuccess("Appointment marked as COMPLETED.");
                    } else {
                        ConsoleHelper.printError("Appointment ID not found.");
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 6 -> {
                    String aId = ConsoleHelper.readString("Enter Appointment ID to cancel");
                    if (service.updateAppointmentStatus(aId, AppointmentStatus.CANCELLED)) {
                        ConsoleHelper.printSuccess("Appointment marked as CANCELLED.");
                    } else {
                        ConsoleHelper.printError("Appointment ID not found.");
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 0 -> back = true;
            }
        }
    }

    private static void printAppointmentsTable(Collection<Appointment> list) {
        ConsoleHelper.printSubHeader("Appointments (" + list.size() + " total)");
        System.out.println("+------------+------------+----------+----------------------+----------------------+-----------------+");
        System.out.println("| ID         | Date       | Time     | Patient Name         | Doctor Name          | Status          |");
        System.out.println("+------------+------------+----------+----------------------+----------------------+-----------------+");
        for (Appointment a : list) {
            String statusColor = switch (a.getStatus()) {
                case SCHEDULED -> ConsoleHelper.YELLOW + a.getStatus() + ConsoleHelper.RESET;
                case COMPLETED -> ConsoleHelper.GREEN + a.getStatus() + ConsoleHelper.RESET;
                case CANCELLED -> ConsoleHelper.RED + a.getStatus() + ConsoleHelper.RESET;
            };
            System.out.printf("| %-10s | %-10s | %-8s | %-20s | %-20s | %-24s|\n",
                    a.getId(), a.getDate(), a.getTimeSlot(),
                    truncate(a.getPatientName(), 20), truncate(a.getDoctorName(), 20), statusColor);
        }
        System.out.println("+------------+------------+----------+----------------------+----------------------+-----------------+");
    }

    // ==========================================
    // MEDICAL RECORDS MENU
    // ==========================================
    private static void medicalRecordMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("Electronic Medical Records (EMR)");
            System.out.println("  1. Add Medical Record / Prescription");
            System.out.println("  2. View Patient Medical History");
            System.out.println("  0. Back to Main Menu");
            System.out.println("------------------------------------------------------------------------------------------");

            int choice = ConsoleHelper.readInt("Select an option [0-2]", 0, 2);
            switch (choice) {
                case 1 -> {
                    ConsoleHelper.printSubHeader("Add Medical Record");
                    String pId = ConsoleHelper.readString("Enter Patient ID");
                    Patient p = service.getPatientById(pId);
                    if (p == null) {
                        ConsoleHelper.printError("Patient not found!");
                        ConsoleHelper.pressEnterToContinue();
                        break;
                    }
                    String docName = ConsoleHelper.readString("Attending Doctor Name");
                    String diagnosis = ConsoleHelper.readString("Diagnosis / Condition");
                    String prescription = ConsoleHelper.readString("Prescribed Medications & Dosages");
                    String notes = ConsoleHelper.readString("Clinical Notes / Instructions");

                    MedicalRecord mr = service.addMedicalRecord(pId, docName, diagnosis, prescription, notes);
                    ConsoleHelper.printSuccess("Medical record added successfully! Record ID: " + mr.getId());
                    ConsoleHelper.pressEnterToContinue();
                }
                case 2 -> {
                    String pId = ConsoleHelper.readString("Enter Patient ID");
                    Patient p = service.getPatientById(pId);
                    if (p == null) {
                        ConsoleHelper.printError("Patient not found!");
                        ConsoleHelper.pressEnterToContinue();
                        break;
                    }
                    List<MedicalRecord> history = service.getPatientMedicalHistory(pId);
                    ConsoleHelper.printSubHeader("Medical History for " + p.getName() + " (" + p.getId() + ")");
                    if (history.isEmpty()) {
                        ConsoleHelper.printInfo("No past medical records logged for this patient.");
                    } else {
                        for (MedicalRecord r : history) {
                            System.out.println(ConsoleHelper.CYAN + "------------------------------------------------------------------" + ConsoleHelper.RESET);
                            System.out.println("Record ID   : " + r.getId() + " | Date: " + r.getDate());
                            System.out.println("Doctor      : " + r.getDoctorName());
                            System.out.println("Diagnosis   : " + ConsoleHelper.BOLD + r.getDiagnosis() + ConsoleHelper.RESET);
                            System.out.println("Prescription: " + ConsoleHelper.GREEN + r.getPrescription() + ConsoleHelper.RESET);
                            System.out.println("Notes       : " + r.getNotes());
                        }
                        System.out.println(ConsoleHelper.CYAN + "------------------------------------------------------------------" + ConsoleHelper.RESET);
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 0 -> back = true;
            }
        }
    }

    // ==========================================
    // BILLING & INVOICING MENU
    // ==========================================
    private static void billingMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("Billing & Invoicing");
            System.out.println("  1. Generate Patient Bill");
            System.out.println("  2. View All Invoices");
            System.out.println("  3. Settle / Pay Bill");
            System.out.println("  4. View / Print Detailed Invoice Receipt");
            System.out.println("  0. Back to Main Menu");
            System.out.println("------------------------------------------------------------------------------------------");

            int choice = ConsoleHelper.readInt("Select an option [0-4]", 0, 4);
            switch (choice) {
                case 1 -> {
                    ConsoleHelper.printSubHeader("Generate Bill");
                    String pId = ConsoleHelper.readString("Enter Patient ID");
                    Patient p = service.getPatientById(pId);
                    if (p == null) {
                        ConsoleHelper.printError("Patient not found!");
                        ConsoleHelper.pressEnterToContinue();
                        break;
                    }
                    double fee = ConsoleHelper.readDouble("Enter Consultation Fee ($)", 0.0);
                    double med = ConsoleHelper.readDouble("Enter Medicine Charges ($)", 0.0);
                    double room = ConsoleHelper.readDouble("Enter Room/Bed Charges ($)", 0.0);

                    Bill bill = service.generateBill(pId, fee, med, room);
                    ConsoleHelper.printSuccess("Bill generated! Invoice ID: " + bill.getId() + " | Total Amount: $" + String.format("%.2f", bill.getTotalAmount()));
                    ConsoleHelper.pressEnterToContinue();
                }
                case 2 -> {
                    printBillsTable(service.getAllBills());
                    ConsoleHelper.pressEnterToContinue();
                }
                case 3 -> {
                    String bId = ConsoleHelper.readString("Enter Bill ID to mark as PAID");
                    if (service.payBill(bId)) {
                        ConsoleHelper.printSuccess("Payment processed successfully! Bill ID " + bId + " is now PAID.");
                    } else {
                        ConsoleHelper.printError("Unable to settle bill. Ensure ID is correct and bill is currently PENDING.");
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 4 -> {
                    String bId = ConsoleHelper.readString("Enter Bill ID");
                    Bill b = service.getBillById(bId);
                    if (b == null) {
                        ConsoleHelper.printError("Bill not found!");
                    } else {
                        printInvoiceReceipt(b);
                    }
                    ConsoleHelper.pressEnterToContinue();
                }
                case 0 -> back = true;
            }
        }
    }

    private static void printBillsTable(Collection<Bill> list) {
        ConsoleHelper.printSubHeader("Invoices List (" + list.size() + " total)");
        System.out.println("+------------+------------+----------------------+------------+------------+-----------------+");
        System.out.println("| Bill ID    | Date       | Patient Name         | Total ($)  | Tax ($)    | Status          |");
        System.out.println("+------------+------------+----------------------+------------+------------+-----------------+");
        for (Bill b : list) {
            String status = b.getStatus() == PaymentStatus.PAID
                    ? ConsoleHelper.GREEN + "PAID" + ConsoleHelper.RESET
                    : ConsoleHelper.RED + "PENDING" + ConsoleHelper.RESET;
            System.out.printf("| %-10s | %-10s | %-20s | %-10.2f | %-10.2f | %-24s|\n",
                    b.getId(), b.getBillingDate(), truncate(b.getPatientName(), 20),
                    b.getTotalAmount(), b.getTax(), status);
        }
        System.out.println("+------------+------------+----------------------+------------+------------+-----------------+");
    }

    private static void printInvoiceReceipt(Bill b) {
        System.out.println();
        System.out.println(ConsoleHelper.CYAN + "==========================================================" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.BOLD + "                 HOSPITAL INVOICE RECEIPT                 " + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + "==========================================================" + ConsoleHelper.RESET);
        System.out.println(" Invoice No    : " + b.getId());
        System.out.println(" Billing Date  : " + b.getBillingDate());
        System.out.println(" Patient ID    : " + b.getPatientId());
        System.out.println(" Patient Name  : " + b.getPatientName());
        System.out.println("----------------------------------------------------------");
        System.out.printf("  Doctor Consultation Fee : $ %10.2f\n", b.getConsultationFee());
        System.out.printf("  Medicine / Pharmacy Fee : $ %10.2f\n", b.getMedicineCharges());
        System.out.printf("  Room / Bed Service Fee  : $ %10.2f\n", b.getRoomCharges());
        System.out.printf("  Service Tax (5%%)        : $ %10.2f\n", b.getTax());
        System.out.println("----------------------------------------------------------");
        System.out.printf(ConsoleHelper.BOLD + "  TOTAL PAYABLE           : $ %10.2f\n" + ConsoleHelper.RESET, b.getTotalAmount());
        System.out.println("----------------------------------------------------------");
        String stat = b.getStatus() == PaymentStatus.PAID
                ? ConsoleHelper.GREEN + "[PAID] (Date: " + b.getPaymentDate() + ")" + ConsoleHelper.RESET
                : ConsoleHelper.RED + "[PAYMENT PENDING]" + ConsoleHelper.RESET;
        System.out.println(" Payment Status: " + stat);
        System.out.println(ConsoleHelper.CYAN + "==========================================================" + ConsoleHelper.RESET);
    }

    // ==========================================
    // SYSTEM DASHBOARD
    // ==========================================
    private static void showDashboard() {
        ConsoleHelper.printHeader("Hospital Analytics & Live Dashboard");
        Map<String, Object> stats = service.getSystemStatistics();

        System.out.println("+----------------------------------------+----------------------+");
        System.out.println("| Metric Name                            | Current Value        |");
        System.out.println("+----------------------------------------+----------------------+");
        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            System.out.printf("| %-38s | %-20s |\n", entry.getKey(), entry.getValue());
        }
        System.out.println("+----------------------------------------+----------------------+");
        ConsoleHelper.pressEnterToContinue();
    }

    private static String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }
}
