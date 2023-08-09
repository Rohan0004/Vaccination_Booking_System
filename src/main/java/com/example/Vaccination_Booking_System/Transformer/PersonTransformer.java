package com.example.Vaccination_Booking_System.Transformer;

import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetPersonResponseDto;
import com.example.Vaccination_Booking_System.Model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonTransformer {
    public static GetPersonResponseDto personToPersonResponseDto(Person person){
        return GetPersonResponseDto.builder()
                .age(person.getAge())
                .emailId(person.getEmailId())
                .gender(person.getGender())
                .name(person.getName())
                .build();
    }

    public static List<GetPersonResponseDto> personToPersonResponseDtoList(List<Person> personList){
        List<GetPersonResponseDto> persons = new ArrayList<>();
        for (Person person : personList)
            persons.add(PersonTransformer.personToPersonResponseDto(person));
        return persons;
    }
}
