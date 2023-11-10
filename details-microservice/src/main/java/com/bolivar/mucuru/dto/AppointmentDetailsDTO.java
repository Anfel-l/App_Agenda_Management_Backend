package com.bolivar.mucuru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailsDTO {
	private Integer medicalAppointmentId;
	private String appointmentType;
	private String symptomName;
	private Double medicalPriority;
}
