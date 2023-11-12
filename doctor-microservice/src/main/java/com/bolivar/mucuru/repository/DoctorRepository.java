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

import com.bolivar.mucuru.dto.DoctorDTO;
import com.bolivar.mucuru.dto.DoctorDetailDTO;

import oracle.jdbc.OracleTypes;

@Repository
public class DoctorRepository {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall jdbcCall;
	
	@Autowired
	public DoctorRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public DoctorDTO getDoctorById(Long doctorId){
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR")
				.withProcedureName("Proc_Get_DOCTOR_BY_ID")
				.declareParameters(
							new SqlOutParameter("Op_doctor", OracleTypes.CURSOR, new DoctorRowMapper()),
							new SqlParameter("Ip_doctor_id", Types.INTEGER)
						);
		
		SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_doctor_id", doctorId);
		
		Map<String, Object> out = jdbcCall.execute(in);
		List<DoctorDTO> doctorList = (List<DoctorDTO>) out.get("Op_doctor");
		
		if (!doctorList.isEmpty()) {
			return doctorList.get(0);
		}
		
		return null;
	}
	
	public List<DoctorDTO> getAllDoctors(){
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR")
				.withProcedureName("Proc_Get_All_DOCTOR")
				.declareParameters(new SqlOutParameter("Op_doctor", OracleTypes.CURSOR, new DoctorRowMapper()));
		
		Map<String, Object> out = jdbcCall.execute();
		return (List<DoctorDTO>) out.get("Op_doctor");
	}
	
	public void insertDoctor(DoctorDTO doctor) {
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR")
				.withProcedureName("Proc_Insert_DOCTOR")
				.declareParameters(
						new SqlParameter("Ip_first_name", Types.VARCHAR),
						new SqlParameter("Ip_second_name", Types.VARCHAR),
						new SqlParameter("Ip_last_name", Types.VARCHAR),
						new SqlParameter("Ip_medical_field_id", Types.INTEGER),
						new SqlParameter("Ip_medical_center_id", Types.INTEGER)
						);
		
		MapSqlParameterSource in = new MapSqlParameterSource();
				in.addValue("Ip_first_name", doctor.getFirstName());
				in.addValue("Ip_second_name", doctor.getSecondName());
				in.addValue("Ip_last_name", doctor.getLastName());
				in.addValue("Ip_medical_field_id", doctor.getMedicalFieldId());
				in.addValue("Ip_medical_center_id", doctor.getMedicalCenterId());
		jdbcCall.execute(in);
	}
	
	public void updateDoctor(DoctorDTO doctor) {
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR")
				.withProcedureName("Proc_Update_DOCTOR")
				.declareParameters(
						new SqlParameter("Ip_doctor_id", Types.INTEGER),
						new SqlParameter("Ip_first_name", Types.VARCHAR),
						new SqlParameter("Ip_second_name", Types.VARCHAR),
						new SqlParameter("Ip_last_name", Types.VARCHAR),
						new SqlParameter("Ip_medical_field_id", Types.INTEGER),
						new SqlParameter("Ip_medical_center_id", Types.INTEGER)
						);
		
		MapSqlParameterSource in = new MapSqlParameterSource();
				in.addValue("Ip_doctor_id", doctor.getDoctorId());
				in.addValue("Ip_first_name", doctor.getFirstName());
				in.addValue("Ip_second_name", doctor.getSecondName());
				in.addValue("Ip_last_name", doctor.getLastName());
				in.addValue("Ip_medical_field_id", doctor.getMedicalFieldId());
				in.addValue("Ip_medical_center_id", doctor.getMedicalCenterId());
		jdbcCall.execute(in);
		
	}
	
	public DoctorDetailDTO getDoctorDetail(Long doctorId){
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_GET_DETAILS")
				.withProcedureName("Proc_GET_DOCTOR_DETAILS")
				.declareParameters(
							new SqlOutParameter("Op_details", OracleTypes.CURSOR, new DoctorDetailRowMapper()),
							new SqlParameter("Ip_doctor_id", Types.INTEGER)
						);
		
		SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_doctor_id", doctorId);
		
		Map<String, Object> out = jdbcCall.execute(in);
		List<DoctorDetailDTO> doctorList = (List<DoctorDetailDTO>) out.get("Op_details");
		
		if (!doctorList.isEmpty()) {
			return doctorList.get(0);
		}
		
		return null;
	}
	
	public static final class DoctorDetailRowMapper implements RowMapper<DoctorDetailDTO>{

		@Override
		public DoctorDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Timestamp startTimestamp = rs.getTimestamp("start_time");
	    Timestamp endTimestamp = rs.getTimestamp("end_time");
	    
	    ZonedDateTime zonedDateTime1 = startTimestamp.toInstant().atZone(ZoneId.of("America/Bogota")); 
	    ZonedDateTime zonedDateTime2 = endTimestamp.toInstant().atZone(ZoneId.of("America/Bogota"));
	    String startformattedDate = zonedDateTime1.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	    String endformattedDate = zonedDateTime2.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);	    
			return new DoctorDetailDTO(
	                rs.getString("first_name"),
	                rs.getString("last_name"),
	                rs.getString("medical_field_name"),
	                rs.getString("medical_center_name"),
	                startformattedDate,
	                endformattedDate
					);
		}
		
	}
	public static final class DoctorRowMapper implements RowMapper<DoctorDTO>{

		@Override
		public DoctorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new DoctorDTO(
					rs.getLong("doctor_id"),
	                rs.getString("first_name"),
	                rs.getString("second_name"),
	                rs.getString("last_name"),
	                rs.getInt("medical_field_id"),
	                rs.getInt("medical_center_id")
					);
			
		}
	
	}

}
