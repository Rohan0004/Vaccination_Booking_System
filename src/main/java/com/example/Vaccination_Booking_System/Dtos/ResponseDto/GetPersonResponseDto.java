package com.example.Vaccination_Booking_System.Dtos.ResponseDto;

import com.example.Vaccination_Booking_System.Enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetPersonResponseDto {
    String name;
    int age;
    String emailId;
    Gender gender;
}
