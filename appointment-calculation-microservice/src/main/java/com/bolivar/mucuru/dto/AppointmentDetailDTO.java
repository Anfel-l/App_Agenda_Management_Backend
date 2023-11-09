package com.bolivar.mucuru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailDTO {
	private Integer userId;
	private Integer medicalAppointmentTypeId;
	private Integer medicalFieldId;
	private Integer symptomId;
}


