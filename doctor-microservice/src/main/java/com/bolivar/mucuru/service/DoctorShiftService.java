package com.bolivar.mucuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.DoctorShiftDTO;
import com.bolivar.mucuru.repository.DoctorShiftRepository;

@Service
public class DoctorShiftService {
	
	private final DoctorShiftRepository doctorShiftRepository;
	
	@Autowired
	public DoctorShiftService(DoctorShiftRepository doctorShiftRepository) {
		this.doctorShiftRepository = doctorShiftRepository;
	}
	
	public DoctorShiftDTO getDoctorShiftById(Long doctorShiftId) {
		return doctorShiftRepository.getDoctorShiftById(doctorShiftId);
	}
	
	public List<DoctorShiftDTO> getAllDoctorShifts(){
		return doctorShiftRepository.getAllDoctorShifts();
	}
	
	public void insertDoctorShift(DoctorShiftDTO doctorShift) {
		doctorShiftRepository.insertDoctorShift(doctorShift);
	}
	
	public void updateDoctorShift(DoctorShiftDTO doctorShift) {
		doctorShiftRepository.updateDoctorShift(doctorShift);
	}
}
