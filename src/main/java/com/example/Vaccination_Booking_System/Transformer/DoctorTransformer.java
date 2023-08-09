package com.example.Vaccination_Booking_System.Transformer;

import com.example.Vaccination_Booking_System.Dtos.ResponseDto.CenterResponseDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetDoctorResponseDto;
import com.example.Vaccination_Booking_System.Model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorTransformer {
    public static GetDoctorResponseDto doctorToDoctorResponseDto(Doctor doctor){
        CenterResponseDto centerResponseDto = CenterTransformer.centerToCenterResponseDto(doctor.getCenter());
        return GetDoctorResponseDto.builder()
                .name(doctor.getName())
                .age(doctor.getAge())
                .emailId(doctor.getEmailId())
                .gender(doctor.getGender())
                .centerResponseDto(centerResponseDto)
                .build();
    }

    public static List<GetDoctorResponseDto> doctorToDoctorResponseDtoList(List<Doctor> doctorList) {
        List<GetDoctorResponseDto> doctors = new ArrayList<>();
        for (Doctor doctor: doctorList) doctors.add(DoctorTransformer.doctorToDoctorResponseDto(doctor));
        return doctors;
    }
}
