package com.bolivar.mucuru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolivar.mucuru.dto.AppointmentDetailDetailsDTO;
import com.bolivar.mucuru.dto.AppointmentDetailsDTO;
import com.bolivar.mucuru.service.DetailsService;

@RestController
@RequestMapping("/v1")
public class DetailsController {
	
	private DetailsService detailsService;
	
	@Autowired
	public DetailsController(DetailsService detailsService) {
		this.detailsService = detailsService;
	}
	
	@GetMapping("/api/detail/appointment/{id}")
	public ResponseEntity<AppointmentDetailsDTO> getAppointmentDetail(@PathVariable("id") Integer appointmentId){
		AppointmentDetailsDTO appointment = detailsService.getAppointmentDetail(appointmentId);
		
		if (appointment != null) {
			return ResponseEntity.ok(appointment);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/api/detail/appointment-detail/{id}")
	public ResponseEntity<AppointmentDetailDetailsDTO> getAppointmentDetailDetails(@PathVariable("id") Integer appointmentId){
		AppointmentDetailDetailsDTO appointment = detailsService.getAppointmentDetailDetails(appointmentId);
		
		if (appointment != null) {
			return ResponseEntity.ok(appointment);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/api/detail/agenda-detail/{id}")
	public ResponseEntity<List<AppointmentDetailDetailsDTO>> getAgendaDetail(@PathVariable("id")Integer doctorId){
		try {
			List<AppointmentDetailDetailsDTO> agenda = detailsService.getAgendaDetail(doctorId);
			return ResponseEntity.ok(agenda);
		} catch (Exception e) {
			return (ResponseEntity<List<AppointmentDetailDetailsDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
