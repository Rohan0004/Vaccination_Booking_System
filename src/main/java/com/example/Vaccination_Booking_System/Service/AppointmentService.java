package com.example.Vaccination_Booking_System.Service;

import com.example.Vaccination_Booking_System.Dtos.RequestDto.BookAppointmentRequestDto;
import com.example.Vaccination_Booking_System.Dtos.ResponseDto.AppointmentResponseDto;
import com.example.Vaccination_Booking_System.Exception.DoctorNotFoundException;
import com.example.Vaccination_Booking_System.Exception.PersonNotFoundException;
import com.example.Vaccination_Booking_System.Model.Appointment;
import com.example.Vaccination_Booking_System.Model.Doctor;
import com.example.Vaccination_Booking_System.Model.Person;
import com.example.Vaccination_Booking_System.Model.VaccinationCenter;
import com.example.Vaccination_Booking_System.Repository.AppointmentRepository;
import com.example.Vaccination_Booking_System.Repository.DoctorRepository;
import com.example.Vaccination_Booking_System.Repository.PersonRepository;
import com.example.Vaccination_Booking_System.Transformer.AppointmentTransformer;
import com.example.Vaccination_Booking_System.Transformer.CenterTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public AppointmentResponseDto bookAppointment(BookAppointmentRequestDto bookAppointmentRequestDto) {
        Optional<Person> optionalPerson = personRepository.findById(bookAppointmentRequestDto.getPersonId());
        Optional<Doctor> optionalDoctor = doctorRepository.findById(bookAppointmentRequestDto.getDoctorId());

        if (!optionalPerson.isPresent()) throw new PersonNotFoundException("Person Not Found");
        if (!optionalDoctor.isPresent()) throw new DoctorNotFoundException("Doctor Not Found");

        Person person=optionalPerson.get();
        Doctor doctor=optionalDoctor.get();

        Appointment appointment= Appointment.builder()
                .appointmentId(UUID.randomUUID().toString())
                .person(person)
                .doctor(doctor)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        doctor.getAppointments().add(savedAppointment);
        person.getAppointments().add(savedAppointment);

        Doctor savedDoctor = doctorRepository.save(doctor);
        Person savedPerson = personRepository.save(person);
        VaccinationCenter center = savedDoctor.getCenter();

        String messageText = "Congrats!! "+savedPerson.getName()+" Your appointment has been booked with Doctor "+
                savedDoctor.getName() + ".\n Your vaccination center name is: " + center.getCenterName() + ".\n Please reach at this address "+
                center.getAddress() + " at this time: " + savedAppointment.getAppointmentDate()+" .";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("rohanrautacciojob@gmail.com");
        simpleMailMessage.setTo(person.getEmailId());
        simpleMailMessage.setSubject("Congrats!!!! Your AppointMent is booked Successfully.");
        simpleMailMessage.setText(messageText);

        javaMailSender.send(simpleMailMessage);

        return AppointmentResponseDto.builder()
                .personName(savedPerson.getName())
                .appointmentDate(savedAppointment.getAppointmentDate())
                .appointmentId(savedAppointment.getAppointmentId())
                .centerResponseDto(CenterTransformer.centerToCenterResponseDto(savedDoctor.getCenter()))
                .doctorName(savedDoctor.getName())
                .build();
    }

    public List<AppointmentResponseDto> getAppointmentOfDoctor(String emailId) {
        Doctor doctor = doctorRepository.findByemailId(emailId);
        if (doctor==null) throw new DoctorNotFoundException("Doctor Not Found!!!");

        List<AppointmentResponseDto> appointmentResponseDtoList = new ArrayList<>();
        for (Appointment appointment:doctor.getAppointments()) {
            appointmentResponseDtoList.add(AppointmentTransformer.appointmentToAppointmentResponseDto(appointment));
        }

        return appointmentResponseDtoList;
    }

    public List<AppointmentResponseDto> getAppointmentOfPerson(String emailId) {
        Person person = personRepository.findByEmailId(emailId);
        if (person==null) throw new PersonNotFoundException("Person Not Found !!!!");

        List<AppointmentResponseDto> appointmentResponseDtoList = new ArrayList<>();
        for (Appointment appointment:person.getAppointments()) {
            appointmentResponseDtoList.add(AppointmentTransformer.appointmentToAppointmentResponseDto(appointment));
        }

        return appointmentResponseDtoList;
    }
}
