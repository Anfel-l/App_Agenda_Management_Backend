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
import com.bolivar.mucuru.model.DoctorShift;
import com.bolivar.mucuru.service.DoctorShiftService;


@RestController
@RequestMapping("/v1")
public class DoctorShiftController {
	
	private final DoctorShiftService doctorShiftService;
	
	@Autowired
	public DoctorShiftController(DoctorShiftService doctorShiftService) {
		this.doctorShiftService = doctorShiftService;
	}
	
	@GetMapping("/api/doctor_shift/id/{id}")
	public ResponseEntity<DoctorShift> getDoctorShiftById(@PathVariable("id") Long doctorShiftId){
		DoctorShift doctorShift = doctorShiftService.getDoctorShiftById(doctorShiftId);
		
		if (doctorShift != null) {
			return ResponseEntity.ok(doctorShift);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/api/doctor_shift/")
	public ResponseEntity<List<DoctorShift>> getAllDoctorShifts(){
		try {
			List<DoctorShift> doctorShift = doctorShiftService.getAllDoctorShifts();
			return ResponseEntity.ok(doctorShift);
		} catch (Exception e) {
			return (ResponseEntity<List<DoctorShift>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/api/doctor_shift/insert/")
	public ResponseEntity<String> insertDoctorShift(@RequestBody DoctorShift doctorShift){
		try {
			doctorShiftService.insertDoctorShift(doctorShift);
			return ResponseEntity.ok("Doctor shift inserted successfully");
			
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting doctor shift: " + e.getMessage());
		}
	}
	
	@PutMapping("/api/doctor_shift/update/")
	public ResponseEntity<String> updateDoctorShift(@RequestBody DoctorShift doctorShift){
		try {
			doctorShiftService.updateDoctorShift(doctorShift);
			return ResponseEntity.ok("Doctor Shift updated successfully");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating doctor shift: " + e.getMessage());
		}
	}
}
