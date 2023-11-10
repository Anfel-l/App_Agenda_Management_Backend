package com.bolivar.mucuru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalUserDetailDTO {
	private String firstName;
	private String secondName;
	private String lastName;
	private String document;
	private String documentType;
	private String contractType;
	private String locationName;
}


