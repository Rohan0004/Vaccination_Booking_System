package com.example.Vaccination_Booking_System.Repository;

import com.example.Vaccination_Booking_System.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    @Query(value = "select * from doctor where age > :age",nativeQuery = true)
    List<Doctor> getByAgeGreaterThan(int age);

    List<Doctor> findByCenterId(int centerId);

    Doctor findByemailId(String emailId);
}
