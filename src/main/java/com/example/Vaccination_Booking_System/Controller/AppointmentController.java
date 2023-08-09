package com.example.Vaccination_Booking_System.Controller;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.BookAppointmentRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.AppointmentResponseDto;
import com.example.Vaccination_Booking_System.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity bookAppointment(@RequestBody BookAppointmentRequestDto bookAppointmentRequestDto){

        try{
            AppointmentResponseDto appointmentResponseDto = appointmentService.bookAppointment(bookAppointmentRequestDto);
            return new ResponseEntity(appointmentResponseDto,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get all the appointments of a particular doctor
    @GetMapping("/getAppointmentOfDoctor")
    public ResponseEntity getAppointmentOfDoctor(@RequestParam String emailId){
        try {
            return new ResponseEntity<>(appointmentService.getAppointmentOfDoctor(emailId),HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get all the appointments for a particular person
    @GetMapping("/getAppointmentOfPerson")
    public ResponseEntity getAppointmentOfPerson(@RequestParam String emailId){
        try {
            return new ResponseEntity<>(appointmentService.getAppointmentOfPerson(emailId),HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
