package com.hospital.model;

import com.hospital.model.enums.Gender;
import java.io.Serializable;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private int age;
    private Gender gender;
    private String contact;
    private String bloodGroup;
    private String address;
    private String ailment;
    private boolean admitted;
    private String roomNumber;
    private String admissionDate;

    public Patient() {}

    public Patient(String id, String name, int age, Gender gender, String contact,
                   String bloodGroup, String address, String ailment) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.ailment = ailment;
        this.admitted = false;
        this.roomNumber = "N/A";
        this.admissionDate = "N/A";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAilment() { return ailment; }
    public void setAilment(String ailment) { this.ailment = ailment; }

    public boolean isAdmitted() { return admitted; }
    public void setAdmitted(boolean admitted) { this.admitted = admitted; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }

    public void admit(String roomNumber, String date) {
        this.admitted = true;
        this.roomNumber = roomNumber;
        this.admissionDate = date;
    }

    public void discharge() {
        this.admitted = false;
        this.roomNumber = "N/A";
        this.admissionDate = "N/A";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Age: %d | %s | Blood: %s | Contact: %s | Status: %s",
                id, name, age, gender, bloodGroup, contact, (admitted ? "Admitted (Room " + roomNumber + ")" : "Outpatient"));
    }
}
