package com.bolivar.mucuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.MedicalCenterDTO;
import com.bolivar.mucuru.repository.UserLocationRepository;

@Service
public class UserLocationService {
	
	private final UserLocationRepository userLocationRepository;
	
	@Autowired
	public UserLocationService(UserLocationRepository userLocationRepository){
		this.userLocationRepository = userLocationRepository;
	}
	
	public List<MedicalCenterDTO> getCentersByUserLocation(Long userId){
		return userLocationRepository.getCentersByUserLocation(userId);
	}
	

}
