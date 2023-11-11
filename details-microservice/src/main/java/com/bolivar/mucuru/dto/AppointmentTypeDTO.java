package com.bolivar.mucuru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTypeDTO {
	private Long medicalAppointmentTypeId;
	private String medicalAppointmentTypeName;
	private String description;

}
