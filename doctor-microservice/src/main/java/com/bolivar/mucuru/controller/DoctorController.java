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

import com.bolivar.mucuru.dto.DoctorDTO;
import com.bolivar.mucuru.dto.DoctorDetailDTO;
import com.bolivar.mucuru.service.DoctorService;

@RestController
@RequestMapping("/v1/api/doctor")
public class DoctorController {

	private final DoctorService doctorService;
	
	
	@Autowired
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable("id") Long doctorId){
		DoctorDTO doctor = doctorService.getDoctorById(doctorId);
		
		if (doctor != null) {
			return ResponseEntity.ok(doctor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<DoctorDetailDTO> getDoctorDetail(@PathVariable("id") Long doctorId){
		DoctorDetailDTO doctor = doctorService.getDoctorDetail(doctorId);
		
		if (doctor != null) {
			return ResponseEntity.ok(doctor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<DoctorDTO>> getAllDoctor(){
		try {
			List<DoctorDTO> doctor = doctorService.getAllDoctors();
			return ResponseEntity.ok(doctor);
		} catch (Exception e) {
			return (ResponseEntity<List<DoctorDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/insert/")
	public ResponseEntity<String> insertDoctor(@RequestBody DoctorDTO doctor){
		try {
			doctorService.insertDoctor(doctor);
			return ResponseEntity.ok("Doctor inserted successfully");
			
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting doctor: " + e.getMessage());
		}
	}
	
	@PutMapping("/update/")
	public ResponseEntity<String> updateDoctor(@RequestBody DoctorDTO doctor){
		try {
			doctorService.updateDoctor(doctor);
			return ResponseEntity.ok("Doctor updated successfully");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating doctor: " + e.getMessage());
		}
	}
}
