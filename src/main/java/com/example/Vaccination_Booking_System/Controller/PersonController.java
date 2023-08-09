package com.example.Vaccination_Booking_System.Controller;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.AddPersonRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.AddPersonResponseDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetPersonResponseDto;
import com.example.Vaccination_Booking_System.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/add")
    public ResponseEntity addPerson(@RequestBody AddPersonRequestDto addPersonRequestDto){
        try{
            AddPersonResponseDto addResponse = personService.addPerson(addPersonRequestDto);
            return new ResponseEntity(addResponse, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity("Email already exists",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_email")
    public ResponseEntity updateEmail(@RequestParam("oldEmail") String oldEmail,
                                      @RequestParam("newEmail") String newEmail){

        try{
            String response = personService.updateEmail(oldEmail,newEmail);
            return new ResponseEntity(response,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    // get all males of age greater than a certain age
    @GetMapping("/getMalesWithAgeGreaterThan")
    public ResponseEntity getMalesWithAgeGreaterThan(@RequestParam int age){
        List<GetPersonResponseDto> persons = personService.getMalesWithAgeGreaterThan(age);
        return new ResponseEntity(persons,HttpStatus.ACCEPTED);
    }


    // get all females who have taken only dose 1 not dose 2
    @GetMapping("/getFemalesWhoTakenOnlyDose1")
    public ResponseEntity getFemalesWhoTakenOnlyDose1(){
        List<GetPersonResponseDto> persons = personService.getFemalesWhoTakenOnlyDose1();
        return new ResponseEntity(persons,HttpStatus.ACCEPTED);
    }


    // get all the people who are fully vaccinated
    @GetMapping("/FullyVaccinated")
    public ResponseEntity FullyVaccinatedPersons(){
        List<GetPersonResponseDto> people = personService.FullyVaccinatedPersons();
        return new ResponseEntity<>(people,HttpStatus.ACCEPTED);
    }


    // get all the people who have not taken even a single dose
    @GetMapping("/getPeoplesWithZeroDoseTaken")
    public ResponseEntity getPeoplesWithZeroDoseTaken(){
        List<GetPersonResponseDto> persons = personService.getPeoplesWithZeroDoseTaken();
        return new ResponseEntity(persons,HttpStatus.ACCEPTED);
    }


    // get all females whose age is greater than a particular age and who have taken only dose 1
    @GetMapping("/getFemalesWhoTakenOnlyDose1AndAgeGreaterThan")
    public ResponseEntity getFemalesWhoTakenOnlyDose1AndAgeGreaterThan(int age){
        List<GetPersonResponseDto> persons = personService.getFemalesWhoTakenOnlyDose1AndAgeGreaterThan(age);
        return new ResponseEntity(persons,HttpStatus.ACCEPTED);
    }


    // get all males whose age is greater than a particular age and who have taken both
    @GetMapping("/FullyVaccinatedMales")
    public ResponseEntity FullyVaccinatedMales(){
        List<GetPersonResponseDto> persons = personService.FullyVaccinatedMales();
        return new ResponseEntity(persons,HttpStatus.ACCEPTED);
    }

}
