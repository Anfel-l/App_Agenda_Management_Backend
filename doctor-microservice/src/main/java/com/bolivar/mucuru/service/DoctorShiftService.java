package com.bolivar.mucuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.model.DoctorShift;
import com.bolivar.mucuru.repository.DoctorShiftRepository;

@Service
public class DoctorShiftService {
	
	private final DoctorShiftRepository doctorShiftRepository;
	
	@Autowired
	public DoctorShiftService(DoctorShiftRepository doctorShiftRepository) {
		this.doctorShiftRepository = doctorShiftRepository;
	}
	
	public DoctorShift getDoctorShiftById(Long doctorShiftId) {
		return doctorShiftRepository.getDoctorShiftById(doctorShiftId);
	}
	
	public List<DoctorShift> getAllDoctorShifts(){
		return doctorShiftRepository.getAllDoctorShifts();
	}
	
	public void insertDoctorShift(DoctorShift doctorShift) {
		doctorShiftRepository.insertDoctorShift(doctorShift);
	}
	
	public void updateDoctorShift(DoctorShift doctorShift) {
		doctorShiftRepository.updateDoctorShift(doctorShift);
	}
}
