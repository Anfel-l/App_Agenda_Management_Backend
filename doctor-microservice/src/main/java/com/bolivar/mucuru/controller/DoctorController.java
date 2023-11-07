package com.bolivar.mucuru.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolivar.mucuru.model.Doctor;
import com.bolivar.mucuru.service.DoctorService;

@RestController
@RequestMapping("/v1")
public class DoctorController {

	private final DoctorService doctorService;
	
	
	@Autowired
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	@GetMapping("/api/doctor/id/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") Long doctorId){
		Doctor doctor = doctorService.getDoctorById(doctorId);
		
		if (doctor != null) {
			return ResponseEntity.ok(doctor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/api/doctor/")
	public ResponseEntity<List<Doctor>> getAllDoctor(){
		try {
			List<Doctor> doctor = doctorService.getAllDoctors();
			return ResponseEntity.ok(doctor);
		} catch (Exception e) {
			return (ResponseEntity<List<Doctor>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/api/doctor/insert/")
	public ResponseEntity<String> insertDoctor(@RequestBody Doctor doctor){
		try {
			doctorService.insertDoctor(doctor);
			return ResponseEntity.ok("Doctor inserted successfully");
			
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting doctor: " + e.getMessage());
		}
	}
	
	@PutMapping("/api/doctor/update/")
	public ResponseEntity<String> updateDoctor(@RequestBody Doctor doctor){
		try {
			doctorService.updateDoctor(doctor);
			return ResponseEntity.ok("Doctor updated successfully");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating doctor: " + e.getMessage());
		}
	}
}
