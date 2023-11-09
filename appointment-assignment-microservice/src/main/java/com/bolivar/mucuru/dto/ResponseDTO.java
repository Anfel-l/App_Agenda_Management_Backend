package com.bolivar.mucuru.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

	private Long detailId;
	private	Integer userId;
	private Integer doctoId;
	private Integer medicalAppointmentId;
	private Integer appointmentFeeId;
	private Integer medicalAppointmentStatusId;
	private Timestamp appointmentTime;
}


