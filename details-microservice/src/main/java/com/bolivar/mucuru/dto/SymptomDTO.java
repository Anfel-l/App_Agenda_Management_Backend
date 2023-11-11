package com.bolivar.mucuru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymptomDTO {
	
	private Long symptomId;
	private String symptomName;
	private Integer symptomPriority;
}
