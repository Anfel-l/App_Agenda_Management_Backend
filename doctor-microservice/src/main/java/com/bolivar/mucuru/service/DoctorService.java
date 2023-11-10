package com.bolivar.mucuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.DoctorDTO;
import com.bolivar.mucuru.dto.DoctorDetailDTO;
import com.bolivar.mucuru.repository.DoctorRepository;

@Service
public class DoctorService {
	
	private final DoctorRepository doctorRepository;
	
	@Autowired
	public DoctorService(DoctorRepository doctorReppository) {
		this.doctorRepository = doctorReppository;
	}
	
	public DoctorDTO getDoctorById(Long doctorId) {
		return doctorRepository.getDoctorById(doctorId);
	}
	
	public List<DoctorDTO> getAllDoctors(){
		return doctorRepository.getAllDoctors();
	}
	
	public void insertDoctor(DoctorDTO doctor) {
		doctorRepository.insertDoctor(doctor);
	}
	
	public void updateDoctor(DoctorDTO doctor) {
		doctorRepository.updateDoctor(doctor);
	}
	
	public DoctorDetailDTO getDoctorDetail(Long doctorId) {
		return doctorRepository.getDoctorDetail(doctorId);
	}
}
