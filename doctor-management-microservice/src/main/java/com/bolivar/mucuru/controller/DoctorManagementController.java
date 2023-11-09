package com.bolivar.mucuru.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolivar.mucuru.dto.DoctorDTO;
import com.bolivar.mucuru.service.DoctorManagementService;

@RestController
@RequestMapping("/v1")
public class DoctorManagementController {
	
	private DoctorManagementService doctorManagementService;
	
	@Autowired
	public DoctorManagementController(DoctorManagementService doctorManagementService){
		this.doctorManagementService = doctorManagementService;
	}
	
	@GetMapping("/api/doctor-available/{appointment_id}/{id}")
	public ResponseEntity<DoctorDTO> getDoctorsAvailable(@PathVariable("appointment_id") int medicalAppointmentId, @PathVariable("id") int userId){
		try {
			DoctorDTO doctor = doctorManagementService.getDoctorAvailable(medicalAppointmentId, userId);
			return ResponseEntity.ok(doctor);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			
		}
	
	}	
	
}
