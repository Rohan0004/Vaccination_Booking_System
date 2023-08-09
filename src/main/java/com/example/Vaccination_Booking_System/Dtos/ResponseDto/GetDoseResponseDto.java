package com.example.Vaccination_Booking_System.Dtos.ResponseDto;

import com.example.Vaccination_Booking_System.Enums.DoseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetDoseResponseDto {
    String name;
    String message;
    DoseType doseType;
}
