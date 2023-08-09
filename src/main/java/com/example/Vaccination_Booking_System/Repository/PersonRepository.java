package com.example.Vaccination_Booking_System.Repository;


import com.example.Vaccination_Booking_System.Enums.Gender;
import com.example.Vaccination_Booking_System.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
    Person findByEmailId(String oldEmail);
    List<Person> findByDose1TakenAndDose2Taken(boolean dose1,boolean dose2);

    List<Person> findByGender(Gender gender);

    List<Person> findByDose1TakenAndDose2TakenAndGender(boolean dose1,boolean dose2, Gender gender);
    @Query(value = "Select p from Person p where p.dose1Taken=:dose1 and p.dose2Taken=:dose2 and p.age >=:age and p.gender =:gender")
    List<Person> getByDose1TakenAndDose2TakenAndGenderAndAge(boolean dose1,boolean dose2, Gender gender, int age);

}