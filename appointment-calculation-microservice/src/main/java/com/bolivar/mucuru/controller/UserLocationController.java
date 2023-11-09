package com.bolivar.mucuru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolivar.mucuru.dto.MedicalCenterDTO;
import com.bolivar.mucuru.service.UserLocationService;

@RestController
@RequestMapping("/v1")
public class UserLocationController {
	
	private final UserLocationService userLocationService;
	
	@Autowired
	public UserLocationController(UserLocationService userLocationService) {
		this.userLocationService = userLocationService;
	}
	
	@GetMapping("/api/medical-center/location/{id}")
	public ResponseEntity<List<MedicalCenterDTO>> getMedicalCentersByUserLocation(@PathVariable("id") Long userId){
		try {
			List<MedicalCenterDTO> medicalCenters = userLocationService.getCentersByUserLocation(userId);
			return ResponseEntity.ok(medicalCenters);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
