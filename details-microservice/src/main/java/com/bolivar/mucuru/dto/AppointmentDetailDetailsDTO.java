package com.bolivar.mucuru.dto;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailDetailsDTO {
	
	private Integer detailId;
	private String userName;
	private String doctorName;
	private Integer medicalAppointmentId;
	private Long feeValue;
	private String status;
	private String appointmentTime;
	
}
