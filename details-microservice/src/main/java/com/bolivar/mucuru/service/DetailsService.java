package com.bolivar.mucuru.service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bolivar.mucuru.dto.AppointmentDetailDetailsDTO;
import com.bolivar.mucuru.dto.AppointmentDetailsDTO;
import com.bolivar.mucuru.repository.DetailsRepository;

import com.opencsv.CSVWriter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service
public class DetailsService {

	private DetailsRepository detailsRepository;
	
	@Autowired
	public DetailsService(DetailsRepository detailsRepository) {
		this.detailsRepository = detailsRepository;
	}
	
	public AppointmentDetailsDTO getAppointmentDetail(Integer appointmentId) {
		return detailsRepository.getAppointmentDetail(appointmentId);
	}
	public AppointmentDetailDetailsDTO getAppointmentDetailDetails(Integer appointmentDetailId) {
		return detailsRepository.getAppointmentDetailDetails(appointmentDetailId);
	}
	
	public List<AppointmentDetailDetailsDTO> getAgendaDetail(Integer doctorId) {
		return detailsRepository.getAgendaDetail(doctorId);
	}
	
	public List<AppointmentDetailDetailsDTO> getMassiveAgenda(){
		return detailsRepository.getMassiveAgenda();
	}

    public Path generateCsvFile(Integer doctorId, String fileName) {
        List<AppointmentDetailDetailsDTO> details = getAgendaDetail(doctorId);
        Path filePath = Paths.get(fileName);

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath.toFile()))) {
            String[] header = { "Detail ID", "User Name", "Doctor Name", "Medical Appointment ID", "Fee Value", "Status", "Appointment Time" };
            writer.writeNext(header);

            for (AppointmentDetailDetailsDTO detail : details) {
                String[] data = {
                    detail.getDetailId().toString(),
                    detail.getUserName(),
                    detail.getDoctorName(),
                    detail.getMedicalAppointmentId().toString(),
                    detail.getFeeValue().toString(),
                    detail.getStatus(),
                    detail.getAppointmentTime().toString()
                };
                writer.writeNext(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }
    
    
    public Path generateMassiveXlsxFile(String fileName) {
        List<AppointmentDetailDetailsDTO> details = getMassiveAgenda();
        String userHomeFolder = System.getProperty("user.home");
        String relativePath = userHomeFolder + "\\Desktop\\Files\\" + fileName;
        Path filePath = Paths.get(relativePath);

        try (Workbook workbook = new XSSFWorkbook()) {
            Map<String, Sheet> sheets = new HashMap<>();
            String[] header = { "Detail ID", "User Name", "Doctor Name", "Medical Appointment ID", "Fee Value", "Status", "Appointment Time", "Symptom"};

            for (AppointmentDetailDetailsDTO detail : details) {
                String doctorName = detail.getDoctorName();
                Sheet sheet = sheets.computeIfAbsent(doctorName, k -> {
                    Sheet newSheet = workbook.createSheet(doctorName);
                    Row headerRow = newSheet.createRow(0);
                    for (int i = 0; i < header.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(header[i]);
                        newSheet.autoSizeColumn(i);
                    }
                    return newSheet;
                });

                int lastRowNum = sheet.getLastRowNum();
                Row row = sheet.createRow(lastRowNum + 1);

                row.createCell(0).setCellValue(detail.getDetailId());
                row.createCell(1).setCellValue(detail.getUserName());
                row.createCell(2).setCellValue(detail.getDoctorName());
                row.createCell(3).setCellValue(detail.getMedicalAppointmentId().toString());
                row.createCell(4).setCellValue(detail.getFeeValue().toString());
                row.createCell(5).setCellValue(detail.getStatus());
                row.createCell(6).setCellValue(detail.getAppointmentTime().toString());
                row.createCell(7).setCellValue(detail.getSymptomName().toString());
            }
            
            sheets.values().forEach(sheet -> {
                for (int i = 0; i < header.length; i++) {
                    sheet.autoSizeColumn(i);
                }
            });
            
            try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
                workbook.write(outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }
    
    @Scheduled(cron = "0 0 0 * * ?") 
    public void generateDailyReport() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "reporte_diario_" + dateFormat.format(new Date()) + ".xlsx";
        generateMassiveXlsxFile(fileName);
    }
    
    
    
}
