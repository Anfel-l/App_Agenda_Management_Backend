package com.bolivar.mucuru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.DetailDTO;
import com.bolivar.mucuru.dto.ResponseDTO;
import com.bolivar.mucuru.repository.DetailRepository;

@Service
public class DetailService {
	
	private DetailRepository detailRepository;
	
	@Autowired
	public DetailService(DetailRepository detailRepository) {
		this.detailRepository = detailRepository;
	}
	
	public ResponseDTO assignAppointment(DetailDTO detail) {
		return detailRepository.assignAppointment(detail);
	}
}
