package com.bolivar.mucuru.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import oracle.jdbc.OracleTypes;

@Repository
public class DoctorManagementRepository {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	@Autowired
	public DoctorManagementRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public DoctorDTO getDoctorsAvailable(int medicalAppointmentId, int userId){
		
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR_AVAILABILITY")
				.withProcedureName("Proc_Get_Doctors_By_Appointment")
				.declareParameters(
						new SqlOutParameter("Op_doctor", OracleTypes.CURSOR, new DoctorDTORowMapper()),
						new SqlParameter("Ip_medical_appointment_id", OracleTypes.NUMBER),
						new SqlParameter("Ip_user_id", OracleTypes.NUMBER)
						);
		
		MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("Ip_medical_appointment_id", medicalAppointmentId);
			in.addValue("Ip_user_id", userId);
		
		Map<String, Object> out = simpleJdbcCall.execute(in);
		List<DoctorDTO> doctorList = (List<DoctorDTO>) out.get("Op_doctor");
		
        if (!doctorList.isEmpty()) {
            return doctorList.get(0);
        }
        return null;		
	}
	
	public static final class DoctorDTORowMapper implements RowMapper<DoctorDTO>{
		@Override
		public DoctorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new DoctorDTO(
					rs.getInt("doctor_id"),
		            rs.getString("first_name"),
		            rs.getString("second_name"),
		            rs.getString("last_name"),
		            rs.getInt("medical_field_id"),
		            rs.getInt("medical_center_id")
			);
		}	
	}
}
