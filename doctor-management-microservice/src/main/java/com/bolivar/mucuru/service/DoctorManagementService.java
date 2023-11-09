package com.bolivar.mucuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.DoctorDTO;
import com.bolivar.mucuru.repository.DoctorManagementRepository;

@Service
public class DoctorManagementService {
	
	private DoctorManagementRepository doctorManagementRepository;
	
	@Autowired
	public DoctorManagementService(DoctorManagementRepository doctorManagementRepository) {
		this.doctorManagementRepository = doctorManagementRepository;
	}
	
	public DoctorDTO getDoctorAvailable(int medicalAppointmentId, int userId){
		return doctorManagementRepository.getDoctorsAvailable(medicalAppointmentId, userId);
	}
}
