package com.bolivar.mucuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.model.Doctor;
import com.bolivar.mucuru.repository.DoctorRepository;

@Service
public class DoctorService {
	
	private final DoctorRepository doctorRepository;
	
	@Autowired
	public DoctorService(DoctorRepository doctorReppository) {
		this.doctorRepository = doctorReppository;
	}
	
	public Doctor getDoctorById(Long doctorId) {
		return doctorRepository.getDoctorById(doctorId);
	}
	
	public List<Doctor> getAllDoctors(){
		return doctorRepository.getAllDoctors();
	}
	
	public void insertDoctor(Doctor doctor) {
		doctorRepository.insertDoctor(doctor);
	}
	
	public void updateDoctor(Doctor doctor) {
		doctorRepository.updateDoctor(doctor);
	}
}
