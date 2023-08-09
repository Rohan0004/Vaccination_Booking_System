package com.example.Vaccination_Booking_System.Dtos.ResponseDto;

import com.example.Vaccination_Booking_System.Enums.CenterType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CenterResponseDto {

    String centerName;

    CenterType centerType;

    String address;
}
