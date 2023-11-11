package com.bolivar.mucuru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolivar.mucuru.dto.LoginDTO;
import com.bolivar.mucuru.dto.LoginResponseDTO;
import com.bolivar.mucuru.service.LoginService;

@RestController
@RequestMapping("/v1/api/login")
public class LoginController {
	private final LoginService loginService;
	
	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@PostMapping("/validation/")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
	    try {
	        LoginResponseDTO response = loginService.userLogin(loginDTO);
	        if (response.getUserId() != null) {
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponseDTO());
	    }
	}

}
