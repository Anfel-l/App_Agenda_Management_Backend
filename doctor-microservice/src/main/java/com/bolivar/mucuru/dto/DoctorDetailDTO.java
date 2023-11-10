package com.bolivar.mucuru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDetailDTO {
	private String firstName;
	private String lastName;
	private String medicalFieldName;
	private String medicalCenterName;
}
