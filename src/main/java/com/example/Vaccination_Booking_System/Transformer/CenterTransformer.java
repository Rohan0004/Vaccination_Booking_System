package com.example.Vaccination_Booking_System.Transformer;

import com.example.Vaccination_Booking_System.Dtos.ResponseDto.CenterResponseDto;
import com.example.Vaccination_Booking_System.Model.VaccinationCenter;

public class CenterTransformer {

    public static CenterResponseDto centerToCenterResponseDto(VaccinationCenter center){
        return CenterResponseDto.builder()
                .centerName(center.getCenterName())
                .centerType(center.getCenterType())
                .address(center.getAddress())
                .build();
    }
}
