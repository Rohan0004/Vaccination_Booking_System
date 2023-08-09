package com.example.Vaccination_Booking_System.Dtos.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CertificateResponseDto {
    String certificateNumber;
    String message;
    GetPersonResponseDto personResponseDto;
}
