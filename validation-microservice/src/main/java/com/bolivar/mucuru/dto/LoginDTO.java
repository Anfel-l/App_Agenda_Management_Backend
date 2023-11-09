package com.bolivar.mucuru.dto;

import lombok.Data;

@Data
public class LoginDTO {
	private Integer documentTypeId;
	private String document;
	private String password;
}
