package com.example.Vaccination_Booking_System.Controller;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.CenterRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.CenterResponseDto;
import com.example.Vaccination_Booking_System.Enums.CenterType;
import com.example.Vaccination_Booking_System.Service.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/center")
public class VaccinationCenterController {

    @Autowired
    VaccinationCenterService centerService;

    @PostMapping("/add")
    public CenterResponseDto addCenter(@RequestBody CenterRequestDto centerRequestDto){

        CenterResponseDto centerResponseDto = centerService.addCenter(centerRequestDto);
        return centerResponseDto;
    }


    // get all the doctors at centers of a particular centerType
    @GetMapping("/getListOfDoctorsAtCenterType")
    public ResponseEntity getListOfDoctorsAtCenterType(@RequestParam CenterType centerType){
        return new ResponseEntity(centerService.getListOfDoctorsAtCenterType(centerType), HttpStatus.ACCEPTED);
    }


    // get the center with highest number of doctors
    @GetMapping("/getCenterWithHighestDoctors")
    public ResponseEntity getCenterWithHighestDoctors(){
        return new ResponseEntity(centerService.getCenterWithHighestDoctors(), HttpStatus.ACCEPTED);
    }


    // get the center with highest number of doctors among a particular centerType
    @GetMapping("/getCenterWithHighestDoctorsAtCenterType")
    public ResponseEntity getCenterWithHighestDoctorsAtCenterType(@RequestParam CenterType centerType){
        return new ResponseEntity(centerService.getCenterWithHighestDoctorsAtCenterType(centerType), HttpStatus.ACCEPTED);
    }
}