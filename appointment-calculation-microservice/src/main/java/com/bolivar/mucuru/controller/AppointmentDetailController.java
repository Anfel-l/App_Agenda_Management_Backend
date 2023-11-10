package com.bolivar.mucuru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolivar.mucuru.dto.AppointmentDetailDTO;
import com.bolivar.mucuru.dto.ResponseDTO;
import com.bolivar.mucuru.service.AppointmentDetailService;

@RestController
@RequestMapping("/v1")
public class AppointmentDetailController {
	
	private final AppointmentDetailService appointmentDetailService;
	
	@Autowired
	public AppointmentDetailController(AppointmentDetailService appointmentDetailService) {
		this.appointmentDetailService = appointmentDetailService;
	}
	
	@PostMapping("/api/apppointment-detail/insert/")
	public ResponseEntity<?> insertAppointmentDetails(@RequestBody AppointmentDetailDTO appointment){
		try {
			ResponseDTO result = appointmentDetailService.insertAppointmentDetails(appointment);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting appointment: " + e.getMessage());		}
	}
}
