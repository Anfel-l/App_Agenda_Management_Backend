package com.bolivar.mucuru.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
	
	private Long doctorId;
	private String firstName;
	private String secondName;
	private String lastName;
	private Integer medicalFielId;
	private Integer medicalCenterId;
	
}
