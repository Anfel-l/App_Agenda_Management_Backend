package com.bolivar.mucuru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolivar.mucuru.dto.AppointmentTypeDTO;
import com.bolivar.mucuru.dto.DocumentTypeDTO;
import com.bolivar.mucuru.dto.MedicalCenterDTO;
import com.bolivar.mucuru.dto.MedicalFieldDTO;
import com.bolivar.mucuru.dto.SymptomDTO;
import com.bolivar.mucuru.service.SoftDetailService;

@RestController
@RequestMapping("/v1/api/detail")
public class SoftDetailController {
	
	private final SoftDetailService softDetailService;
	
	
	@Autowired
	public SoftDetailController(SoftDetailService softDetailService) {
		this.softDetailService = softDetailService;
	}
	
	@GetMapping("/symptom/")
	public ResponseEntity<List<SymptomDTO>> getAllSymptom(){
		try {
			List<SymptomDTO> symptoms = softDetailService.getAllSymptoms();
			return ResponseEntity.ok(symptoms);
		} catch (Exception e) {
			return (ResponseEntity<List<SymptomDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/medical-field/")
	public ResponseEntity<List<MedicalFieldDTO>> getAllMedicalField(){
		try {
			List<MedicalFieldDTO> symptoms = softDetailService.getAllMedicalField();
			return ResponseEntity.ok(symptoms);
		} catch (Exception e) {
			return (ResponseEntity<List<MedicalFieldDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/appointment-type/")
	public ResponseEntity<List<AppointmentTypeDTO>> getAllAppointmentType(){
		try {
			List<AppointmentTypeDTO> types = softDetailService.getAllAppointmentType();
			return ResponseEntity.ok(types);
		} catch (Exception e) {
			return (ResponseEntity<List<AppointmentTypeDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/document-type/")
	public ResponseEntity<List<DocumentTypeDTO>> getAllDocumentType(){
		try {
			List<DocumentTypeDTO> types = softDetailService.getAllDocumentType();
			return ResponseEntity.ok(types);
		} catch (Exception e) {
			return (ResponseEntity<List<DocumentTypeDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/medical-center/{id}")
	public ResponseEntity<MedicalCenterDTO> getMedicalCenter(@PathVariable("id") Long centerId){
		MedicalCenterDTO medicalCenter = softDetailService.getMedicalCenter(centerId);
		if (medicalCenter != null) {
			return ResponseEntity.ok(medicalCenter);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	
	
}
