package com.example.Vaccination_Booking_System.Service;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.GetDoseRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetDoseResponseDto;
import com.example.Vaccination_Booking_System.Enums.DoseType;
import com.example.Vaccination_Booking_System.Exception.Dose1AlreadyTakenException;
import com.example.Vaccination_Booking_System.Exception.Dose1NotTakenException;
import com.example.Vaccination_Booking_System.Exception.Dose2AlreadyTakenException;
import com.example.Vaccination_Booking_System.Exception.PersonNotFoundException;
import com.example.Vaccination_Booking_System.Model.Dose;
import com.example.Vaccination_Booking_System.Model.Person;
import com.example.Vaccination_Booking_System.Repository.DoseRepository;
import com.example.Vaccination_Booking_System.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DoseService {

    @Autowired
    DoseRepository doseRepository;

    @Autowired
    PersonRepository personRepository;

    public GetDoseResponseDto getDose1(GetDoseRequestDto getDoseRequestDto) {
        int personId = getDoseRequestDto.getPersonId();
        DoseType doseType = getDoseRequestDto.getDoseType();

        Optional<Person> personCheck = personRepository.findById(personId);
        if(!personCheck.isPresent()) throw new PersonNotFoundException("Invalid Person Id!!!");

        Person person=personCheck.get();

        if (person.isDose1Taken()) throw new Dose1AlreadyTakenException("Dose 1 Already Taken!!!");

        Dose dose=new Dose();
        dose.setDoseId(UUID.randomUUID().toString());
        dose.setPerson(person);
        dose.setDoseType(doseType);
        dose.setDoseNumber(1);

        person.setDose1Taken(true);
        Dose savedDose = doseRepository.save(dose);
        person.getDosesTaken().add(savedDose);
        personRepository.save(person);

        GetDoseResponseDto getDoseResponse = new GetDoseResponseDto();
        getDoseResponse.setDoseType(doseType);
        getDoseResponse.setName(person.getName());
        getDoseResponse.setMessage("Congrats!!! "+getDoseResponse.getName()+" Dose 1( "+doseType+" ) taken!!!");
        return getDoseResponse;
    }

    public GetDoseResponseDto getDose2(GetDoseRequestDto getDoseRequestDto) {
        int personId = getDoseRequestDto.getPersonId();
        DoseType doseType = getDoseRequestDto.getDoseType();

        Optional<Person> personCheck = personRepository.findById(personId);
        if(!personCheck.isPresent()) throw new PersonNotFoundException("Invalid Person Id!!!");

        Person person=personCheck.get();
        if (person.isDose2Taken()) throw new Dose2AlreadyTakenException("Dose 2 Already Taken!!!");
        if (!person.isDose1Taken()) throw new Dose1NotTakenException("Take Dose 1 first.");

        Dose dose=new Dose();
        dose.setDoseId(UUID.randomUUID().toString());
        dose.setPerson(person);
        dose.setDoseType(doseType);
        dose.setDoseNumber(2);

        person.setDose2Taken(true);
        Dose savedDose = doseRepository.save(dose);
        person.getDosesTaken().add(savedDose);
        personRepository.save(person);


        GetDoseResponseDto getDoseResponse = new GetDoseResponseDto();
        getDoseResponse.setDoseType(doseType);
        getDoseResponse.setName(person.getName());
        getDoseResponse.setMessage("Congrats!!! "+getDoseResponse.getName()+" Dose 2( "+doseType+" ) taken!!!");
        return getDoseResponse;
    }
}
