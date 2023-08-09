package com.example.Vaccination_Booking_System.Controller;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.DoctorRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.AddDoctorResponseDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetDoctorResponseDto;
import com.example.Vaccination_Booking_System.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity addDoctor(@RequestBody DoctorRequestDto doctorRequestDto){

        try{
            AddDoctorResponseDto addDoctorResponseDto = doctorService.addDoctor(doctorRequestDto);
            return new ResponseEntity(addDoctorResponseDto,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_by_age_greater_than")
    public List<String> getByAgeGreaterThan(@RequestParam("age") int age){
        List<String> doctors = doctorService.getByAgeGreaterThan(age);
        return doctors;
    }

    // get the doctor with highest number of appointments
    @GetMapping("/getDoctorsWithHighestAppointments")
    public ResponseEntity getDoctorsWithHighestAppointments(){
        List<GetDoctorResponseDto> doctors = doctorService.getDoctorsWithHighestAppointments();
        return new ResponseEntity<>(doctors,HttpStatus.ACCEPTED);
    }

    // get the list of doctors who belong to a particular center
    @GetMapping("/getDoctorsAtCenter")
    public ResponseEntity getDoctorsAtCenter(@RequestParam int centerId){
        List<GetDoctorResponseDto> doctors = doctorService.getDoctorsAtCenter(centerId);
        return new ResponseEntity<>(doctors,HttpStatus.ACCEPTED);
    }

    // api to update email and/or age of a doctor

}
