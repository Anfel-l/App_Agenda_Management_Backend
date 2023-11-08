package com.bolivar.mucuru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCenterDTO {
	private Long medicalCenterId;
	private String medicalCenterName;
	private Integer locationId;
}
