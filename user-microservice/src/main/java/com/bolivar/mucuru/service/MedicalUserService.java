package com.bolivar.mucuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.MedicalUserDTO;
import com.bolivar.mucuru.dto.MedicalUserDetailDTO;
import com.bolivar.mucuru.repository.MedicalUserRepository;

@Service
public class MedicalUserService {

    private final MedicalUserRepository medicalUserRepository;

    @Autowired
    public MedicalUserService(MedicalUserRepository medicalUserRepository) {
        this.medicalUserRepository = medicalUserRepository;
    }

    public MedicalUserDTO getMedicalUserById(Long userId) {
        return medicalUserRepository.getMedicalUserById(userId);
    }
    
    public List<MedicalUserDTO> getAllMedicalUsers(){
    	return medicalUserRepository.getAllMedicalUsers();
    }
    
    public MedicalUserDTO getMedicalUserByDocument(String document) {
    	return medicalUserRepository.getMedicalUserByDocument(document);
    }
    
    public void insertMedicalUser(MedicalUserDTO user) {
    	medicalUserRepository.insertMedicalUser(user);
    }
    
    public void updateMedicalUser(MedicalUserDTO user) {
    	medicalUserRepository.updateMedicalUser(user);
    }
    
    public MedicalUserDetailDTO getMedicalUserDetail(Long userId) {
    	return medicalUserRepository.getMedicalUserDetail(userId);
    }
    
    

}
