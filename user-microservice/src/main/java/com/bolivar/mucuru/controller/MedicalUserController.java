package com.bolivar.mucuru.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bolivar.mucuru.model.MedicalUser;
import com.bolivar.mucuru.service.MedicalUserService;

@RestController
@RequestMapping("/v1")
public class MedicalUserController {

    private final MedicalUserService medicalUserService;

    @Autowired
    public MedicalUserController(MedicalUserService medicalUserService) {
        this.medicalUserService = medicalUserService;
    }

    @GetMapping("/api/medical-user/id/{id}")
    public ResponseEntity<MedicalUser> getMedicalUserById(@PathVariable("id") Long userId) {
        MedicalUser medicalUser = medicalUserService.getMedicalUserById(userId);
        if (medicalUser != null) {
            return ResponseEntity.ok(medicalUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/api/medical-user/document/{document}")
    public ResponseEntity<MedicalUser> getMedicalUserByDocument(@PathVariable("document") String document){
    	MedicalUser medicalUser = medicalUserService.getMedicalUserByDocument(document);
    	if (medicalUser != null) {
    		return ResponseEntity.ok(medicalUser);
    	} else {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @PostMapping("/api/medical-user/insert/")
    public ResponseEntity<String> insertMedicalUser(@RequestBody MedicalUser user) {
        try {
            medicalUserService.insertMedicalUser(user);
            return ResponseEntity.ok("User inserted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting user: " + e.getMessage());
        }
    }

    @PutMapping("/api/medical-user/update/")
    public ResponseEntity<String> updateMedicalUser(@RequestBody MedicalUser user) {
        try {
            medicalUserService.updateMedicalUser(user);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
        }
    }
    
    @GetMapping("/api/medical-user/")
    public ResponseEntity<List<MedicalUser>> getAllMedicalUser(){
        try {
        	List<MedicalUser> users = medicalUserService.getAllMedicalUsers();
            return ResponseEntity.ok(users);
		} catch (Exception e) {
            return (ResponseEntity<List<MedicalUser>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    
}
