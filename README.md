# 🏥 Hospital Management System (Console Edition)

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)
[![Pure Console](https://img.shields.io/badge/Interface-CLI%20%2F%20Console-blue?style=for-the-badge)](https://github.com/Jinchuriki-7/Hospital-Management-System)

A robust, enterprise-grade **Hospital Management System** built with **pure Java**, running entirely through the terminal/console interface without graphical UI dependencies. Designed using clean Object-Oriented Programming (OOP) principles, multi-tier service architecture, and persistent storage.

---

## 🌟 Key Features

### 1. 🧑‍🤝‍🧑 Patient Management
- **Register Patients:** Capture full profile (Name, Age, Gender, Blood Group, Contact, Address, Ailment).
- **Inpatient & Outpatient Tracking:** Admit patients to specific rooms/wards (ICU, General Ward, Cabin) and track admission dates.
- **Discharge System:** Free allocated rooms and update records.
- **Search & Filter:** Search patients by ID, Name, or Contact with formatted tabular output.

### 2. 👨‍⚕️ Doctor Management
- **Specialist Directory:** Track doctors across departments (Cardiology, Neurology, Pediatrics, Orthopedics, Dermatology, etc.).
- **Consultation Scheduling:** Record visiting days, visiting hours, and consultation fee structure.
- **Doctor Search:** Fast lookup by doctor name or medical specialization.

### 3. 📅 Appointment Scheduling
- **Book Appointments:** Seamlessly link registered patients with available doctors.
- **Real-time Status Updates:** Manage appointment lifecycles (`SCHEDULED`, `COMPLETED`, `CANCELLED`).
- **Targeted Queries:** View appointments filtered by Patient ID or Doctor ID.

### 4. 📋 Electronic Medical Records (EMR)
- **Clinical Diagnoses & Notes:** Log doctor observations and clinical assessments.
- **Digital Prescriptions:** Record prescribed medications, dosages, and patient instructions.
- **Complete Patient History:** View chronologically ordered medical history for any patient.

### 5. 💳 Billing & Invoicing
- **Itemized Invoicing:** Generates itemized bills breaking down Doctor Fees, Pharmacy/Medicine Charges, Room/Bed Charges, and Tax.
- **Payment Processing:** Settle dues and mark invoices as `PAID` with timestamps.
- **Printable Receipts:** Beautifully formatted ASCII receipts for patients.

### 6. 📊 Real-Time Analytics Dashboard
- Live hospital overview metrics:
  - Total registered patients & inpatients admitted
  - Total doctors on duty
  - Scheduled active appointments
  - Total revenue collected and pending dues

### 7. 💾 Automated Data Persistence
- Built-in data serialization ensures patient records, doctor rosters, appointments, and bills are automatically saved and restored across sessions.
- Pre-seeded with realistic sample data on initial run for immediate testing.

---

## 📂 Project Architecture

```
Hospital Management/
├── .gitignore
├── README.md
├── run.bat                 # 1-Click build & run for Windows
├── run.sh                  # 1-Click build & run for Linux / macOS
├── src/
│   └── com/
│       └── hospital/
│           ├── Main.java                        # Interactive CLI UI & Menus
│           ├── model/                           # Data Models
│           │   ├── Patient.java
│           │   ├── Doctor.java
│           │   ├── Appointment.java
│           │   ├── MedicalRecord.java
│           │   ├── Bill.java
│           │   └── enums/                       # Domain Enums
│           │       ├── Gender.java
│           │       ├── AppointmentStatus.java
│           │       └── PaymentStatus.java
│           ├── service/
│           │   └── HospitalService.java         # Core Business Logic Layer
│           └── util/
│               ├── ConsoleHelper.java           # ANSI UI, Tables & Safe Readers
│               └── DataPersistence.java         # File Storage & Seed Data
└── data/                                        # Persistent Data Store
```

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK 17 or higher)**
- Any standard terminal (PowerShell, Command Prompt, Bash, Zsh)

Verify Java installation:
```bash
java -version
javac -version
```

### Quick Run

#### Windows:
Double-click `run.bat` or run:
```cmd
.\run.bat
```

#### Linux / macOS:
```bash
chmod +x run.sh
./run.sh
```

#### Manual Compilation & Run:
```bash
# Compile all source files into bin directory
javac -d bin src/com/hospital/model/enums/*.java src/com/hospital/model/*.java src/com/hospital/util/*.java src/com/hospital/service/*.java src/com/hospital/Main.java

# Run application
java -cp bin com.hospital.Main
```

---

## 🖥️ Preview / Console Interface

```text
==========================================================================================
   HOSPITAL MANAGEMENT SYSTEM - MAIN MENU
==========================================================================================
  1. Patient Management   (Register, View, Search, Admit/Discharge)
  2. Doctor Management    (Add Doctor, View List, Search Specialties)
  3. Appointments         (Book, View, Reschedule, Cancel, Complete)
  4. Medical Records      (Prescriptions, Diagnoses, Patient History)
  5. Billing & Invoicing  (Generate Invoices, Settle Bills, Receipts)
  6. Hospital Analytics  (Overview Stats & Live Metrics)
  0. Exit Application
------------------------------------------------------------------------------------------
```

### Sample Formatted Invoice Receipt:
```text
==========================================================
                 HOSPITAL INVOICE RECEIPT                 
==========================================================
 Invoice No    : BIL-401
 Billing Date  : 2026-11-16
 Patient ID    : PAT-101
 Patient Name  : Kazi Hasan
----------------------------------------------------------
  Doctor Consultation Fee : $    1200.00
  Medicine / Pharmacy Fee : $     850.00
  Room / Bed Service Fee  : $    3000.00
  Service Tax (5%)        : $     252.50
----------------------------------------------------------
  TOTAL PAYABLE           : $    5302.50
----------------------------------------------------------
 Payment Status: [PAID] (Date: 2026-11-17)
==========================================================
```

---

## 🛠️ Tech Stack & Concepts
- **Language:** Java 17+ (OOP, Generics, Stream API, Collections Framework)
- **Architecture:** Layered Architecture (Model - Service - Presentation / CLI - Storage)
- **Input Sanitization:** Robust Exception Handling avoiding crashes on incorrect user input
- **Storage:** Java Object Persistence Serialization (`.dat`)

---

## 📄 License
This project is open-source and available under the [MIT License](LICENSE).
