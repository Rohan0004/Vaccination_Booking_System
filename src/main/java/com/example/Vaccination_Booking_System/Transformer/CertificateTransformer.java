package com.example.Vaccination_Booking_System.Transformer;

import com.example.Vaccination_Booking_System.Dtos.ResponseDto.CertificateResponseDto;
import com.example.Vaccination_Booking_System.Model.Certificate;

import java.util.UUID;

public class CertificateTransformer {

    public static CertificateResponseDto certificateTocertificateResponseDto(Certificate certificate){
        return CertificateResponseDto.builder()
                .certificateNumber(UUID.randomUUID().toString())
                .message("Congrats you are fully vaccinated!!!!")
                .personResponseDto(PersonTransformer.personToPersonResponseDto(certificate.getPerson())).build();
    }
}
