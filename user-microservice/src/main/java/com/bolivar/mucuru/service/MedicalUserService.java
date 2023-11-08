package com.bolivar.mucuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.model.MedicalUser;
import com.bolivar.mucuru.repository.MedicalUserRepository;

@Service
public class MedicalUserService {

    private final MedicalUserRepository medicalUserRepository;

    @Autowired
    public MedicalUserService(MedicalUserRepository medicalUserRepository) {
        this.medicalUserRepository = medicalUserRepository;
    }

    public MedicalUser getMedicalUserById(Long userId) {
        return medicalUserRepository.getMedicalUserById(userId);
    }
    
    public List<MedicalUser> getAllMedicalUsers(){
    	return medicalUserRepository.getAllMedicalUsers();
    }
    
    public MedicalUser getMedicalUserByDocument(String document) {
    	return medicalUserRepository.getMedicalUserByDocument(document);
    }
    
    public void insertMedicalUser(MedicalUser user) {
    	medicalUserRepository.insertMedicalUser(user);
    }
    
    public void updateMedicalUser(MedicalUser user) {
    	medicalUserRepository.updateMedicalUser(user);
    }
    
    

}
