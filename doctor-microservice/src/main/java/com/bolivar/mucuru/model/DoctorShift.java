package com.bolivar.mucuru.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorShift {
	
	private Long doctorShiftId;
	private Integer doctorId;
	private Date shiftDate;
	private Timestamp startTime;
	private Timestamp endTime;
}
