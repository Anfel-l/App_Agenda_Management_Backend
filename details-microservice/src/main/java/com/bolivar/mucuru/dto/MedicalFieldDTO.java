package com.bolivar.mucuru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalFieldDTO {
	
	private Long medicalFieldId;
	private String medicalFieldName;
	private String description;

}
