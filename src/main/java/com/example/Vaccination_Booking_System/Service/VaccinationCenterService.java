package com.example.Vaccination_Booking_System.Service;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.CenterRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.CenterResponseDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetDoctorResponseDto;
import com.example.Vaccination_Booking_System.Enums.CenterType;
import com.example.Vaccination_Booking_System.Model.Doctor;
import com.example.Vaccination_Booking_System.Model.VaccinationCenter;
import com.example.Vaccination_Booking_System.Repository.VaccinationCenterRepository;
import com.example.Vaccination_Booking_System.Transformer.CenterTransformer;
import com.example.Vaccination_Booking_System.Transformer.DoctorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationCenterService {

    @Autowired
    VaccinationCenterRepository centerRepository;

    public CenterResponseDto addCenter(CenterRequestDto centerRequestDto) {

        // request dto -> entity
        VaccinationCenter center = new VaccinationCenter();
        center.setCenterName(centerRequestDto.getCenterName());
        center.setCenterType(centerRequestDto.getCenterType());
        center.setAddress(centerRequestDto.getAddress());

        // save entity to db
        VaccinationCenter savedCenter = centerRepository.save(center);

        // entity -> response Dto
        CenterResponseDto centerResponseDto = new CenterResponseDto();
        centerResponseDto.setCenterName(savedCenter.getCenterName());
        centerResponseDto.setAddress(savedCenter.getAddress());
        centerResponseDto.setCenterType(savedCenter.getCenterType());
        return centerResponseDto;

    }

    public List<GetDoctorResponseDto> getListOfDoctorsAtCenterType(CenterType centerType) {
        List<VaccinationCenter> vaccinationCenterList = centerRepository.findByCenterType(centerType);
        List<GetDoctorResponseDto> doctors = new ArrayList<>();
        for (VaccinationCenter center: vaccinationCenterList) {
            for (Doctor doctor: center.getDoctors()) {
                doctors.add(DoctorTransformer.doctorToDoctorResponseDto(doctor));
            }
        }
        return doctors;
    }

    public List<CenterResponseDto> getCenterWithHighestDoctors() {
        return getListOfCenters(centerRepository.findAll());
    }

    private List<CenterResponseDto> getListOfCenters(List<VaccinationCenter> centerList) {
        List<VaccinationCenter> centers = new ArrayList<>();
        int max=0;
        for (VaccinationCenter center: centerList) {
            if (center.getDoctors().size()>max){
                max=center.getDoctors().size();
                centers.clear();
                centers.add(center);
            }else if (center.getDoctors().size()==max) centers.add(center);
        }
        List<CenterResponseDto> centerResponse = new ArrayList<>();
        for (VaccinationCenter center: centers) centerResponse.add(CenterTransformer.centerToCenterResponseDto(center));

        return centerResponse;
    }

    public List<CenterResponseDto> getCenterWithHighestDoctorsAtCenterType(CenterType centerType) {
        return getListOfCenters(centerRepository.findByCenterType(centerType));
    }
}
