package com.bolivar.mucuru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.AppointmentDetailDetailsDTO;
import com.bolivar.mucuru.dto.AppointmentDetailsDTO;
import com.bolivar.mucuru.repository.DetailsRepository;

@Service
public class DetailsService {

	private DetailsRepository detailsRepository;
	
	@Autowired
	public DetailsService(DetailsRepository detailsRepository) {
		this.detailsRepository = detailsRepository;
	}
	
	public AppointmentDetailsDTO getAppointmentDetail(Integer appointmentId) {
		return detailsRepository.getAppointmentDetail(appointmentId);
	}
	public AppointmentDetailDetailsDTO getAppointmentDetailDetails(Integer appointmentDetailId) {
		return detailsRepository.getAppointmentDetailDetails(appointmentDetailId);
	}
}
