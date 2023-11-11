package com.bolivar.mucuru.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.AppointmentTypeDTO;
import com.bolivar.mucuru.dto.MedicalFieldDTO;
import com.bolivar.mucuru.dto.SymptomDTO;
import com.bolivar.mucuru.repository.SoftDetailRepository;
import com.bolivar.mucuru.repository.SoftDetailRepository.MedicalFieldRowMapper;
import com.bolivar.mucuru.repository.SoftDetailRepository.SymptomRowMapper;

import oracle.jdbc.OracleTypes;

@Service
public class SoftDetailService {

	private SoftDetailRepository softDetailRepository;
	
	@Autowired
	public SoftDetailService(SoftDetailRepository softDetailRepository) {
		this.softDetailRepository = softDetailRepository;
	}
	
	
	public List<SymptomDTO> getAllSymptoms(){
		return softDetailRepository.getAllSymptoms();
	}
	public List<MedicalFieldDTO> getAllMedicalField(){
		return softDetailRepository.getAllMedicalField();
	}
	public List<AppointmentTypeDTO> getAllAppointmentType(){
		return softDetailRepository.getAllAppointmentType();
	}
	
}
