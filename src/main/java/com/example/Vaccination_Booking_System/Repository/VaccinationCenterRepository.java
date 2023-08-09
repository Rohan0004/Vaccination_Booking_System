package com.example.Vaccination_Booking_System.Repository;

import com.example.Vaccination_Booking_System.Enums.CenterType;
import com.example.Vaccination_Booking_System.Model.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter,Integer> {
    List<VaccinationCenter> findByCenterType(CenterType centerType);
}