package com.bolivar.mucuru.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.bolivar.mucuru.dto.AppointmentDetailDetailsDTO;
import com.bolivar.mucuru.dto.AppointmentDetailsDTO;

import oracle.jdbc.OracleTypes;

@Repository
public class DetailsRepository {
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall jdbcCall;
	
	@Autowired
	public DetailsRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	
	public AppointmentDetailsDTO getAppointmentDetail(Integer appointmentId) {
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_GET_DETAILS")
				.withProcedureName("Proc_GET_APPOINTMENT_DETAILS")
				.declareParameters(
						new SqlParameter("Ip_appointment_id", OracleTypes.INTEGER),
						new SqlOutParameter("Op_details", OracleTypes.CURSOR, new AppointmentDetailRowMapper())
						);
		SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_appointment_id", appointmentId);
		
		Map<String, Object> out = jdbcCall.execute(in);
		List<AppointmentDetailsDTO> appointmentList = (List<AppointmentDetailsDTO>) out.get("Op_details");
		
		if (!appointmentList.isEmpty()) {
			return appointmentList.get(0);
		} else {
			return null;
		}
	}
	
	public AppointmentDetailDetailsDTO getAppointmentDetailDetails(Integer appointmentDetailId) {
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_GET_DETAILS")
				.withProcedureName("Proc_GET_APPOINTMENT_DETAIL_DETAILS")
				.declareParameters(
						new SqlParameter("Ip_appointment_detail_id", OracleTypes.INTEGER),
						new SqlOutParameter("Op_details", OracleTypes.CURSOR, new AppointmentDetailDetailsRowMapper())
						);
		SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_appointment_detail_id", appointmentDetailId);
		
		Map<String, Object> out = jdbcCall.execute(in);
		List<AppointmentDetailDetailsDTO> appointmentList = (List<AppointmentDetailDetailsDTO>) out.get("Op_details");
		
		if (!appointmentList.isEmpty()) {
			return appointmentList.get(0);
		} else {
			return null;
		}
	}
	
	public List<AppointmentDetailDetailsDTO> getAgendaDetail(Integer doctorId) {
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_GET_DETAILS")
				.withProcedureName("Proc_GET_AGENDA_DETAILS")
				.declareParameters(
						new SqlParameter("Ip_doctor_id", OracleTypes.INTEGER),
						new SqlOutParameter("Op_agenda", OracleTypes.CURSOR, new AppointmentDetailDetailsRowMapper())
						);
		SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_doctor_id", doctorId);
		
		Map<String, Object> out = jdbcCall.execute(in);
		List<AppointmentDetailDetailsDTO> appointmentList = (List<AppointmentDetailDetailsDTO>) out.get("Op_agenda");
		
		return appointmentList;
	}
	
	public List<AppointmentDetailDetailsDTO> getMassiveAgenda(){
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_MASSIVE_DOCTOR_AGENDA")
				.withProcedureName("Proc_GET_AGENDA")
				.declareParameters(
						new SqlOutParameter("Op_agenda", OracleTypes.CURSOR, new AppointmentDetailDetailsRowMapper()));
		Map<String, Object> out = jdbcCall.execute();
		return (List<AppointmentDetailDetailsDTO>) out.get("Op_agenda");
	}
	
	
	public static final class AppointmentDetailRowMapper implements RowMapper<AppointmentDetailsDTO>{

		@Override
		public AppointmentDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AppointmentDetailsDTO(
					rs.getInt("medical_appointment_id"),
	                rs.getString("appointment_type"),
	                rs.getString("symptom_name"),
	                rs.getDouble("medical_priority")
					);
		}
		
	}
	
	public static final class AppointmentDetailDetailsRowMapper implements RowMapper<AppointmentDetailDetailsDTO>{

		@Override
		public AppointmentDetailDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Timestamp timestamp = rs.getTimestamp("appointment_time");
	        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZoneId.of("America/Bogota")); 
	        String formattedDate = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			return new AppointmentDetailDetailsDTO(
					rs.getInt("detail_id"),
					rs.getString("user_name"),
	                rs.getString("doctor_name"),
	                rs.getInt("medical_appointment_id"),
	                rs.getLong("fee_value"),
	                rs.getString("status"),
	                formattedDate
					);
		}
		
	}
	
	
}
