package com.example.Vaccination_Booking_System.Controller;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.GetDoseRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetDoseResponseDto;
import com.example.Vaccination_Booking_System.Service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dose")
public class DoseController {
    @Autowired
    DoseService doseService;

    @PostMapping("/getDose1")
    public ResponseEntity getDose1(@RequestBody GetDoseRequestDto getDoseRequestDto){
        try {
            GetDoseResponseDto getDoseResponse = doseService.getDose1(getDoseRequestDto);
            return new ResponseEntity(getDoseResponse,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getDose2")
    public ResponseEntity getDose2(@RequestBody GetDoseRequestDto getDoseRequestDto){
        try {
            GetDoseResponseDto getDoseResponse = doseService.getDose2(getDoseRequestDto);
            return new ResponseEntity(getDoseResponse,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
