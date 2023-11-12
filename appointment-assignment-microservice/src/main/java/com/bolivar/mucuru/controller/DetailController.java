package com.bolivar.mucuru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolivar.mucuru.dto.DetailDTO;
import com.bolivar.mucuru.dto.ResponseDTO;
import com.bolivar.mucuru.service.DetailService;

@RestController
@RequestMapping("/v1/api/appointment-detail")
public class DetailController {
	
	private DetailService detailService;
	
	@Autowired
	public DetailController(DetailService detailService) {
		this.detailService = detailService;
	}
	
    @PostMapping("/insert/")
    public ResponseEntity<?> assignAppointment(@RequestBody DetailDTO detail) {
        try {
            ResponseDTO result = detailService.assignAppointment(detail);
            return ResponseEntity.ok(result); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting detail: " + e.getMessage());
        }
    }
}
