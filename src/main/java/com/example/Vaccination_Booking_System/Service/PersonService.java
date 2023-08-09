package com.example.Vaccination_Booking_System.Service;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.AddPersonRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.AddPersonResponseDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetPersonResponseDto;
import com.example.Vaccination_Booking_System.Enums.Gender;
import com.example.Vaccination_Booking_System.Exception.PersonNotFoundException;
import com.example.Vaccination_Booking_System.Model.Person;
import com.example.Vaccination_Booking_System.Repository.PersonRepository;
import com.example.Vaccination_Booking_System.Transformer.PersonTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public AddPersonResponseDto addPerson(AddPersonRequestDto addPersonRequestDto) {
        Person person = new Person();
        person.setDose1Taken(false);
        person.setDose2Taken(false);
        person.setAge(addPersonRequestDto.getAge());
        person.setGender(addPersonRequestDto.getGender());
        person.setName(addPersonRequestDto.getName());
        person.setEmailId(addPersonRequestDto.getEmailId());

        Person savedPerson = personRepository.save(person);

        AddPersonResponseDto addResponse = new AddPersonResponseDto();
        addResponse.setName(savedPerson.getName());
        addResponse.setMessage("Congrats!!! " + addResponse.getName() + ", You have been registered successfully!!!");
        return addResponse;
    }

    public String updateEmail(String oldEmail, String newEmail) {
        Person person = personRepository.findByEmailId(oldEmail);
        if (person == null) throw new PersonNotFoundException("Please enter valid mail Id");

        person.setEmailId(newEmail);
        personRepository.save(person);
        return "Congrats!! Your email has been updated successfully";
    }

    public List<GetPersonResponseDto> FullyVaccinatedPersons() {
        List<Person> personList = personRepository.findByDose1TakenAndDose2Taken(true, true);
        return PersonTransformer.personToPersonResponseDtoList(personList);
    }

    public List<GetPersonResponseDto> getMalesWithAgeGreaterThan(int age) {
        List<Person> personList = personRepository.findByGender(Gender.MALE);
        List<GetPersonResponseDto> persons = new ArrayList<>();
        for (Person person: personList) {
            if (person.getAge()>age){
                persons.add(PersonTransformer.personToPersonResponseDto(person));
            }
        }
        return persons;
    }

    public List<GetPersonResponseDto> getFemalesWhoTakenOnlyDose1() {
        List<Person> personList = personRepository.findByDose1TakenAndDose2TakenAndGender(true,false,Gender.FEMALE);
        return PersonTransformer.personToPersonResponseDtoList(personList);
    }

    public List<GetPersonResponseDto> getPeoplesWithZeroDoseTaken() {
        List<Person> personList = personRepository.findByDose1TakenAndDose2Taken(false,false);
        return PersonTransformer.personToPersonResponseDtoList(personList);
    }

    public List<GetPersonResponseDto> FullyVaccinatedMales() {
        List<Person> personList = personRepository.findByDose1TakenAndDose2TakenAndGender(true,true,Gender.MALE);
        return PersonTransformer.personToPersonResponseDtoList(personList);
    }

    public List<GetPersonResponseDto> getFemalesWhoTakenOnlyDose1AndAgeGreaterThan(int age) {
        List<Person> personList = personRepository.getByDose1TakenAndDose2TakenAndGenderAndAge(true,false,Gender.FEMALE,age);
        return PersonTransformer.personToPersonResponseDtoList(personList);
    }
}
