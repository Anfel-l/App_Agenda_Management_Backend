package com.bolivar.mucuru.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bolivar.mucuru.dto.DoctorShiftDTO;
import com.bolivar.mucuru.repository.DoctorShiftRepository;

@Service
public class DoctorShiftService {
	
	private final DoctorShiftRepository doctorShiftRepository;
	
	@Autowired
	public DoctorShiftService(DoctorShiftRepository doctorShiftRepository) {
		this.doctorShiftRepository = doctorShiftRepository;
	}
	
	public DoctorShiftDTO getDoctorShiftById(Long doctorShiftId) {
		return doctorShiftRepository.getDoctorShiftById(doctorShiftId);
	}
	
	public List<DoctorShiftDTO> getAllDoctorShifts(){
		return doctorShiftRepository.getAllDoctorShifts();
	}
	
	public void insertDoctorShift(DoctorShiftDTO doctorShift) {
		doctorShiftRepository.insertDoctorShift(doctorShift);
	}
	
	public void updateDoctorShift(DoctorShiftDTO doctorShift) {
		doctorShiftRepository.updateDoctorShift(doctorShift);
	}
	
	
    public void processDoctorShiftsFile(MultipartFile file) throws Exception {
        List<DoctorShiftDTO> shifts = parseCsvFile(file);
        doctorShiftRepository.bulkInsertDoctorShifts(shifts);
    }

    private List<DoctorShiftDTO> parseCsvFile(MultipartFile file) throws Exception {
        List<DoctorShiftDTO> shifts = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                ZonedDateTime shiftDateZoned = ZonedDateTime.parse(data[1], dateFormatter);
                ZonedDateTime startTimeZoned = ZonedDateTime.parse(data[2], dateFormatter);
                ZonedDateTime endTimeZoned = ZonedDateTime.parse(data[3], dateFormatter);

                DoctorShiftDTO shift = new DoctorShiftDTO(
                    null, 
                    Integer.parseInt(data[0]), 
                    Date.valueOf(shiftDateZoned.toLocalDate()), 
                    Timestamp.valueOf(startTimeZoned.toLocalDateTime()), 
                    Timestamp.valueOf(endTimeZoned.toLocalDateTime())
                );
                shifts.add(shift);
            }
        }
        return shifts;
    }
}
