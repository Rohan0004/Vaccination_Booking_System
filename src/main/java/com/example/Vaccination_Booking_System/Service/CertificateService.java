package com.example.Vaccination_Booking_System.Service;

import com.example.Vaccination_Booking_System.Dtos.ResponseDto.CertificateResponseDto;
import com.example.Vaccination_Booking_System.Exception.Dose1NotTakenException;
import com.example.Vaccination_Booking_System.Exception.Dose2NotTakenException;
import com.example.Vaccination_Booking_System.Exception.PersonNotFoundException;
import com.example.Vaccination_Booking_System.Model.Certificate;
import com.example.Vaccination_Booking_System.Model.Person;
import com.example.Vaccination_Booking_System.Repository.CertificateRepository;
import com.example.Vaccination_Booking_System.Repository.PersonRepository;
import com.example.Vaccination_Booking_System.Transformer.CertificateTransformer;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public CertificateResponseDto getCertificate(String emailId) throws Exception {
        Person person = personRepository.findByEmailId(emailId);

        if (person == null) throw new PersonNotFoundException("invalid email id");
        if (!person.isDose1Taken()) throw new Dose1NotTakenException("Please take dose 1");
        if (!person.isDose2Taken()) throw new Dose2NotTakenException("Please take dose 2");

        if (person.getCertificate() != null) return CertificateTransformer.certificateTocertificateResponseDto(person.getCertificate());

        Certificate certificate = Certificate.builder()
                .certificateNo(UUID.randomUUID().toString())
                .confirmationMessage("Congrats you are fully vaccinated!!!!")
                .person(person)
                .build();

        person.setCertificate(certificate);

        Person savedPerson = personRepository.save(person);

        String messageText = "Congrats!! "+savedPerson.getName()+" Your are fully vaccinated. Your Certificate no. is:"
                +savedPerson.getCertificate().getCertificateNo()+" .";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setFrom("rohanrautacciojob@gmail.com");
        mimeMessageHelper.setTo(savedPerson.getEmailId());
        mimeMessageHelper.setSubject("Vaccination Certificate!!");
        mimeMessageHelper.setText(messageText);

        String msg = "<b>Vaccination Dose Information:</b>"
                +"<table>" +
                "<tr>" +
                "<th>Sr. No.</th>" +
                "<th>Dose No.</th>" +
                "<th>Date.</th>" +
                "</tr>"+
                "<tr>" +
                "<td>1</td>" +
                "<td>"+savedPerson.getDosesTaken().get(0).getDoseId()+"</td>" +
                "<td>"+savedPerson.getDosesTaken().get(0).getVaccinationDate()+"</td>" +
                "</tr>"+
                "<tr>" +
                "<td>2</td>" +
                "<td>"+savedPerson.getDosesTaken().get(1).getDoseId()+"</td>" +
                "<td>"+savedPerson.getDosesTaken().get(1).getVaccinationDate()+"</td>" +
                "</tr>"+
                "</table>";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeMessage.setContent(multipart);
        javaMailSender.send(mimeMessage);

        return CertificateTransformer.certificateTocertificateResponseDto(savedPerson.getCertificate());


    }
}
