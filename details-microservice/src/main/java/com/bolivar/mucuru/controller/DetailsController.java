package com.bolivar.mucuru.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.UrlResource;


import com.bolivar.mucuru.dto.AppointmentDetailDetailsDTO;
import com.bolivar.mucuru.dto.AppointmentDetailsDTO;
import com.bolivar.mucuru.service.DetailsService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/v1/api/detail")
public class DetailsController {
	
	private DetailsService detailsService;
	
	@Autowired
	public DetailsController(DetailsService detailsService) {
		this.detailsService = detailsService;
	}
	
	@GetMapping("/appointment/{id}")
	public ResponseEntity<AppointmentDetailsDTO> getAppointmentDetail(@PathVariable("id") Integer appointmentId){
		AppointmentDetailsDTO appointment = detailsService.getAppointmentDetail(appointmentId);
		
		if (appointment != null) {
			return ResponseEntity.ok(appointment);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/appointment-detail/{id}")
	public ResponseEntity<AppointmentDetailDetailsDTO> getAppointmentDetailDetails(@PathVariable("id") Integer appointmentId){
		AppointmentDetailDetailsDTO appointment = detailsService.getAppointmentDetailDetails(appointmentId);
		
		if (appointment != null) {
			return ResponseEntity.ok(appointment);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/agenda-detail/{id}")
	public ResponseEntity<List<AppointmentDetailDetailsDTO>> getAgendaDetail(@PathVariable("id")Integer doctorId){
		try {
			List<AppointmentDetailDetailsDTO> agenda = detailsService.getAgendaDetail(doctorId);
			return ResponseEntity.ok(agenda);
		} catch (Exception e) {
			return (ResponseEntity<List<AppointmentDetailDetailsDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/agenda-detail/")
	public ResponseEntity<List<AppointmentDetailDetailsDTO>> getMassiveAgenda(){
	    try {
	        List<AppointmentDetailDetailsDTO> agenda = detailsService.getMassiveAgenda();
	        return ResponseEntity.ok(agenda);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	// Massive Process
	// Agenda per doctor
    @GetMapping("/agenda-detail/generate-csv/{doctorId}")
    public ResponseEntity<UrlResource> generateCSV(@PathVariable Integer doctorId) throws MalformedURLException {
        String fileName = "agenda_medica_" + doctorId + ".csv";
        Path filePath = detailsService.generateCsvFile(doctorId, fileName);

        UrlResource fileResource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }
    
    // All doctors agenda
    @GetMapping("agenda-detail/massive/generate-xlsx")
    public ResponseEntity<UrlResource> generateMassiveXLSX() throws MalformedURLException {
        String fileName = "agenda_medica_masiva.xlsx";
        Path filePath = detailsService.generateMassiveXlsxFile(fileName);

        UrlResource fileResource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }
}
