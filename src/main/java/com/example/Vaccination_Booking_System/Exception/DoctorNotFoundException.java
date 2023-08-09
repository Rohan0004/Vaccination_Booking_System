package com.example.Vaccination_Booking_System.Exception;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String msg) {
        super(msg);
    }
}
