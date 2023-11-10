package com.bolivar.mucuru.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
	
	private Long doctorId;
	private String firstName;
	private String secondName;
	private String lastName;
	private Integer medicalFieldId;
	private Integer medicalCenterId;
	
}
