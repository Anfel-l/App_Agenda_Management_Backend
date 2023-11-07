package com.bolivar.mucuru.repository;

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
import org.springframework.stereotype.Repository;

import com.bolivar.mucuru.model.Doctor;

import oracle.jdbc.OracleTypes;

@Repository
public class DoctorRepository {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	@Autowired
	public DoctorRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Doctor getDoctorById(Long doctorId){
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR")
				.withProcedureName("Proc_Get_DOCTOR_BY_ID")
				.declareParameters(
							new SqlOutParameter("Op_doctor", OracleTypes.CURSOR, new DoctorRowMapper()),
							new SqlParameter("Ip_doctor_id", Types.INTEGER)
						);
		
		SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_doctor_id", doctorId);
		
		Map<String, Object> out = jdbcCall.execute(in);
		List<Doctor> doctorList = (List<Doctor>) out.get("Op_doctor");
		
		if (!doctorList.isEmpty()) {
			return doctorList.get(0);
		}
		
		return null;
	}
	
	public List<Doctor> getAllDoctors(){
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR")
				.withProcedureName("Proc_Get_All_DOCTOR")
				.declareParameters(new SqlOutParameter("Op_doctor", OracleTypes.CURSOR, new DoctorRowMapper()));
		
		Map<String, Object> out = jdbcCall.execute();
		return (List<Doctor>) out.get("Op_doctor");
	}
	
	public void insertDoctor(Doctor doctor) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
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
				in.addValue("Ip_medical_field_id", doctor.getMedicalFielId());
				in.addValue("Ip_medical_center_id", doctor.getMedicalCenterId());
		jdbcCall.execute(in);
	}
	
	public void updateDoctor(Doctor doctor) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
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
				in.addValue("Ip_first_name", doctor.getFirstName());
				in.addValue("Ip_second_name", doctor.getSecondName());
				in.addValue("Ip_last_name", doctor.getLastName());
				in.addValue("Ip_medical_field_id", doctor.getMedicalFielId());
				in.addValue("Ip_medical_center_id", doctor.getMedicalCenterId());
		jdbcCall.execute(in);
		
	}
	
	public static final class DoctorRowMapper implements RowMapper<Doctor>{

		@Override
		public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Doctor(
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
