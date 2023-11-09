package com.bolivar.mucuru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
	
	private Integer doctorId;
	private String firstName;
	private String secondName;
	private String lastName;
	private Integer medicalFieldId;
	private Integer medicalCenterId;
}


