package com.bolivar.mucuru.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.bolivar.mucuru.dto.AppointmentTypeDTO;


import com.bolivar.mucuru.dto.MedicalFieldDTO;
import com.bolivar.mucuru.dto.SymptomDTO;


import oracle.jdbc.OracleTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

@Repository
public class SoftDetailRepository {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall jdbcCall;
	
	@Autowired
	public SoftDetailRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public List<SymptomDTO> getAllSymptoms(){
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_GET_APPOINTMENT_ITEMS")
				.withProcedureName("Proc_Get_All_Sympmtoms")
				.declareParameters(new SqlOutParameter("Op_symptoms", OracleTypes.CURSOR, new SymptomRowMapper()));
		
		Map<String, Object> out = jdbcCall.execute();
		return (List<SymptomDTO>) out.get("Op_symptoms");
	}
	
	public List<MedicalFieldDTO> getAllMedicalField(){
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_GET_APPOINTMENT_ITEMS")
				.withProcedureName("Proc_Get_All_Medical_Field")
				.declareParameters(new SqlOutParameter("Op_medical_fields", OracleTypes.CURSOR, new MedicalFieldRowMapper()));
		
		Map<String, Object> out = jdbcCall.execute();
		return (List<MedicalFieldDTO>) out.get("Op_medical_fields");
	}
	
	public List<AppointmentTypeDTO> getAllAppointmentType(){
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_GET_APPOINTMENT_ITEMS")
				.withProcedureName("Proc_Get_All_Appointment_Type")
				.declareParameters(new SqlOutParameter("Op_appointment_types", OracleTypes.CURSOR, new AppointmentTypeRowMapper()));
		
		Map<String, Object> out = jdbcCall.execute();
		return (List<AppointmentTypeDTO>) out.get("Op_appointment_types");
	}
	
	
	public static final class MedicalFieldRowMapper implements RowMapper<MedicalFieldDTO>{

		@Override
		public MedicalFieldDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new MedicalFieldDTO(
	                rs.getLong("medical_field_id"),
	                rs.getString("medical_field_name"),
	                rs.getString("description")
					);
		}
		
	}
	
	public static final class SymptomRowMapper implements RowMapper<SymptomDTO>{

		@Override
		public SymptomDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new SymptomDTO(
	                rs.getLong("symptom_id"),
	                rs.getString("symptom_name"),
	                rs.getInt("symptom_priority")
					);
		}
		
	}
	
	public static final class AppointmentTypeRowMapper implements RowMapper<AppointmentTypeDTO>{

		@Override
		public AppointmentTypeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new AppointmentTypeDTO(
	                rs.getLong("medical_appointment_type_id"),
	                rs.getString("medical_appointment_type_name"),
	                rs.getString("description")
					);
		}
	}
	

}
