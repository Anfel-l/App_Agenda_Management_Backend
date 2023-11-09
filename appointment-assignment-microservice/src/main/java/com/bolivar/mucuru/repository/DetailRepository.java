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
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.bolivar.mucuru.dto.DetailDTO;
import com.bolivar.mucuru.dto.ResponseDTO;

import oracle.jdbc.OracleTypes;

@Repository
public class DetailRepository {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	@Autowired
	public DetailRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public ResponseDTO assignAppointment(DetailDTO detail) {
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DETAIL_ASSIGNMENT")
				.withProcedureName("Proc_Assign_Appointment")
				.declareParameters(
						new SqlOutParameter("Op_detail", OracleTypes.CURSOR, new ResponseDTORowMapper()),
						new SqlParameter("Ip_user_id", OracleTypes.NUMBER),
						new SqlParameter("Ip_medical_appointment_id", OracleTypes.NUMBER),
						new SqlParameter("Ip_doctor_id", OracleTypes.NUMBER)
						);
		
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("Ip_user_id", detail.getUserId());
		in.addValue("Ip_medical_appointment_id", detail.getAppointmentId());
		in.addValue("Ip_doctor_id", detail.getDoctorId());
	
		Map<String, Object> out = simpleJdbcCall.execute(in);
		List<ResponseDTO> details = (List<ResponseDTO>) out.get("Op_detail");
		
	    if (!details.isEmpty()) {
	        return details.get(0);
	    }
	    return null;	
	}
	
	
	public static final class ResponseDTORowMapper implements RowMapper<ResponseDTO>{
		@Override
		public ResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ResponseDTO(
					rs.getLong("detail_id"),
					rs.getInt("user_id"),
					rs.getInt("doctor_id"),
					rs.getInt("medical_appointment_id"),
					rs.getInt("appointment_fee_id"),
					rs.getInt("medical_appointment_status_id"),
					rs.getTimestamp("appointment_time")
			);
		}	
	}
}
