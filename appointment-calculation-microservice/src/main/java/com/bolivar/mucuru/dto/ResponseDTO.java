package com.bolivar.mucuru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
	
	private Long medicalAppointmentId;
    private Integer medicalAppointmenTypeId;
    private Integer symptomId;
    private Double medicalPriority;
    private Integer medicalFieldId;
}