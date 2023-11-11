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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bolivar.mucuru.dto.DoctorDTO;
import com.bolivar.mucuru.dto.DoctorShiftDTO;
import com.bolivar.mucuru.service.DoctorShiftService;


@RestController
@RequestMapping("/v1/api/doctor")
public class DoctorShiftController {
	
	private final DoctorShiftService doctorShiftService;
	
	@Autowired
	public DoctorShiftController(DoctorShiftService doctorShiftService) {
		this.doctorShiftService = doctorShiftService;
	}
	
	@GetMapping("/doctor-shift/id/{id}")
	public ResponseEntity<DoctorShiftDTO> getDoctorShiftById(@PathVariable("id") Long doctorShiftId){
		DoctorShiftDTO doctorShift = doctorShiftService.getDoctorShiftById(doctorShiftId);
		
		if (doctorShift != null) {
			return ResponseEntity.ok(doctorShift);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/doctor-shift/")
	public ResponseEntity<List<DoctorShiftDTO>> getAllDoctorShifts(){
		try {
			List<DoctorShiftDTO> doctorShift = doctorShiftService.getAllDoctorShifts();
			return ResponseEntity.ok(doctorShift);
		} catch (Exception e) {
			return (ResponseEntity<List<DoctorShiftDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/doctor-shift/insert/")
	public ResponseEntity<String> insertDoctorShift(@RequestBody DoctorShiftDTO doctorShift){
		try {
			doctorShiftService.insertDoctorShift(doctorShift);
			return ResponseEntity.ok("Doctor shift inserted successfully");
			
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting doctor shift: " + e.getMessage());
		}
	}
	
	@PutMapping("/doctor-shift/update/")
	public ResponseEntity<String> updateDoctorShift(@RequestBody DoctorShiftDTO doctorShift){
		try {
			doctorShiftService.updateDoctorShift(doctorShift);
			return ResponseEntity.ok("Doctor Shift updated successfully");
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating doctor shift: " + e.getMessage());
		}
	}
	
	
	
    @PostMapping("/doctor-shift/upload/")
    public ResponseEntity<String> uploadDoctorShifts(@RequestParam("file") MultipartFile file) {
        try {
            doctorShiftService.processDoctorShiftsFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }
}
