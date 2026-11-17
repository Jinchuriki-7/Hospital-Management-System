package com.hospital.model;

import com.hospital.model.enums.PaymentStatus;
import java.io.Serializable;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String patientId;
    private String patientName;
    private double consultationFee;
    private double medicineCharges;
    private double roomCharges;
    private double tax;
    private double totalAmount;
    private PaymentStatus status;
    private String billingDate;
    private String paymentDate;

    public Bill() {}

    public Bill(String id, String patientId, String patientName, double consultationFee,
                double medicineCharges, double roomCharges, String billingDate) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.consultationFee = consultationFee;
        this.medicineCharges = medicineCharges;
        this.roomCharges = roomCharges;
        this.billingDate = billingDate;
        this.paymentDate = "N/A";
        this.status = PaymentStatus.PENDING;

        double subtotal = consultationFee + medicineCharges + roomCharges;
        this.tax = subtotal * 0.05; // 5% tax
        this.totalAmount = subtotal + this.tax;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }

    public double getMedicineCharges() { return medicineCharges; }
    public void setMedicineCharges(double medicineCharges) { this.medicineCharges = medicineCharges; }

    public double getRoomCharges() { return roomCharges; }
    public void setRoomCharges(double roomCharges) { this.roomCharges = roomCharges; }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public String getBillingDate() { return billingDate; }
    public void setBillingDate(String billingDate) { this.billingDate = billingDate; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public void markAsPaid(String date) {
        this.status = PaymentStatus.PAID;
        this.paymentDate = date;
    }

    @Override
    public String toString() {
        return String.format("[%s] Date: %s | Patient: %s (%s) | Total: $%.2f | Status: %s",
                id, billingDate, patientName, patientId, totalAmount, status);
    }
}
