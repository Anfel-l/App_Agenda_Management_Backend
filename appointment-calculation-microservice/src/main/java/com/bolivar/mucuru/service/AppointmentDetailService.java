package com.bolivar.mucuru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.AppointmentDetailDTO;
import com.bolivar.mucuru.dto.ResponseDTO;
import com.bolivar.mucuru.repository.AppointmentDetailRepository;

@Service
public class AppointmentDetailService {
	
	private AppointmentDetailRepository appointmentRepository;
	
	@Autowired
	public AppointmentDetailService(AppointmentDetailRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}
	
	public ResponseDTO insertAppointmentDetails(AppointmentDetailDTO appointment) {
		return appointmentRepository.insertAppointmentDetails(appointment);
	}

}
