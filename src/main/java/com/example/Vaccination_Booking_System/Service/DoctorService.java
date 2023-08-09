package com.example.Vaccination_Booking_System.Service;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.DoctorRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.AddDoctorResponseDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.CenterResponseDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.GetDoctorResponseDto;
import com.example.Vaccination_Booking_System.Exception.CenterNotFoundException;
import com.example.Vaccination_Booking_System.Model.Doctor;
import com.example.Vaccination_Booking_System.Model.VaccinationCenter;
import com.example.Vaccination_Booking_System.Repository.DoctorRepository;
import com.example.Vaccination_Booking_System.Repository.VaccinationCenterRepository;
import com.example.Vaccination_Booking_System.Transformer.DoctorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    VaccinationCenterRepository centerRepository;

    @Autowired
    DoctorRepository doctorRespository;

    public AddDoctorResponseDto addDoctor(DoctorRequestDto doctorRequestDto) {

        Optional<VaccinationCenter> vaccinationCenterOptional = centerRepository.findById(doctorRequestDto.getCenterId());
        if(vaccinationCenterOptional.isEmpty()){
            throw new CenterNotFoundException("Sorry! Wrong center Id");
        }

        VaccinationCenter center = vaccinationCenterOptional.get();

        // create doctor entity
        Doctor doctor = new Doctor();
        doctor.setName(doctorRequestDto.getName());
        doctor.setAge(doctorRequestDto.getAge());
        doctor.setEmailId(doctorRequestDto.getEmailId());
        doctor.setGender(doctorRequestDto.getGender());
        doctor.setCenter(center);

        // add in center's doctor list
        center.getDoctors().add(doctor);

        VaccinationCenter savedCenter = centerRepository.save(center); // save both center and doctor

        // prepare response dto
        List<Doctor> doctors = savedCenter.getDoctors();
        Doctor latestSavedDoctor = doctors.get(doctors.size()-1);

        CenterResponseDto centerResponseDto = new CenterResponseDto();
        centerResponseDto.setCenterType(savedCenter.getCenterType());
        centerResponseDto.setAddress(savedCenter.getAddress());
        centerResponseDto.setCenterName(savedCenter.getCenterName());

        AddDoctorResponseDto addDoctorResponseDto = new AddDoctorResponseDto();
        addDoctorResponseDto.setName(latestSavedDoctor.getName());
        addDoctorResponseDto.setMessage("Congrats!! You have been registered!");
        addDoctorResponseDto.setCenterResponseDto(centerResponseDto);

        return addDoctorResponseDto;
    }

    public List<String> getByAgeGreaterThan(int age) {
        List<Doctor> doctors = doctorRespository.getByAgeGreaterThan(age);
        List<String> ans = new ArrayList<>();

        for(Doctor d: doctors)
            ans.add(d.getName());

        return ans;
    }

    public List<GetDoctorResponseDto> getDoctorsWithHighestAppointments() {
        List<Doctor> doctorList = new ArrayList<>();
        int max=0;
        for (Doctor doctor: doctorRespository.findAll()) {
            if (doctor.getAppointments().size()>max){
                max=doctor.getAppointments().size();
                doctorList.clear();
                doctorList.add(doctor);
            }else if (doctor.getAppointments().size()==max) doctorList.add(doctor);
        }

        return DoctorTransformer.doctorToDoctorResponseDtoList(doctorList);
    }

    public List<GetDoctorResponseDto> getDoctorsAtCenter(int centerId) {
        List<Doctor> doctorList = doctorRespository.findByCenterId(centerId);
        return DoctorTransformer.doctorToDoctorResponseDtoList(doctorList);

    }
}