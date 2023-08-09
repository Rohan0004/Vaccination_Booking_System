package com.example.Vaccination_Booking_System.Dtos.RequestDto;

import com.example.Vaccination_Booking_System.Enums.DoseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetDoseRequestDto {
    Integer personId;
    DoseType doseType;
}
