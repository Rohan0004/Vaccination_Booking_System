package com.example.Vaccination_Booking_System.Dtos.RequestDto;

import com.example.Vaccination_Booking_System.Enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPersonRequestDto {

    String name;

    int age;

    String emailId;

    Gender gender;

}
