package com.example.Vaccination_Booking_System.Transformer;

import com.example.Vaccination_Booking_System.Dtos.ResponseDto.AppointmentResponseDto;
import com.example.Vaccination_Booking_System.Model.Appointment;

public class AppointmentTransformer {
    public static AppointmentResponseDto appointmentToAppointmentResponseDto(Appointment appointment){
        return AppointmentResponseDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentDate(appointment.getAppointmentDate())
                .doctorName(appointment.getDoctor().getName())
                .personName(appointment.getPerson().getName())
                .centerResponseDto(CenterTransformer.centerToCenterResponseDto(appointment.getDoctor().getCenter()))
                .build();
    }
}
