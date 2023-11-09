package com.bolivar.mucuru.service;

import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.LoginDTO;
import com.bolivar.mucuru.dto.LoginResponseDTO;
import com.bolivar.mucuru.repository.LoginRepository;

@Service
public class LoginService {
	
	private final LoginRepository loginRepository;
	
	public LoginService(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}
	
	public LoginResponseDTO userLogin(LoginDTO loginDTO) {
	    return loginRepository.userLogin(loginDTO.getDocumentTypeId(), loginDTO.getDocument(), loginDTO.getPassword());
	}

}
