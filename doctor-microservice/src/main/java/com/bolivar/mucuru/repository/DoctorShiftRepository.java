package com.bolivar.mucuru.repository;

import java.sql.Connection;
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

import com.bolivar.mucuru.dto.DoctorShiftDTO;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class DoctorShiftRepository {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall jdbcCall;
	
	@Autowired
	public DoctorShiftRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public DoctorShiftDTO getDoctorShiftById(Long doctorShiftId) {
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR_SHIFT")
				.withProcedureName("Proc_Get_DOCTOR_SHIFT_BY_ID")
				.declareParameters(
						new SqlOutParameter("Op_doctor_shift", OracleTypes.CURSOR, new DoctorShiftRowMapper()),
						new SqlParameter("Ip_doctor_shift_id", Types.INTEGER)
						);
		
		SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_doctor_shift_id", doctorShiftId);
		
		Map<String, Object> out = jdbcCall.execute(in);
		List<DoctorShiftDTO> doctorShiftList = (List<DoctorShiftDTO>) out.get("Op_doctor_shift");
		if (!doctorShiftList.isEmpty()) {
			return doctorShiftList.get(0);
		}
		return null;
	}
	
	
	public List<DoctorShiftDTO> getAllDoctorShifts(){
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR_SHIFT")
				.withProcedureName("Proc_Get_All_DOCTOR_SHIFT")
				.declareParameters(
						new SqlOutParameter("Op_doctor_shift", OracleTypes.CURSOR, new DoctorShiftRowMapper())
						);
		
		Map<String, Object> out = jdbcCall.execute();
		return (List<DoctorShiftDTO>) out.get("Op_doctor_shift");
	}
	
	public void insertDoctorShift(DoctorShiftDTO doctorShift) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR_SHIFT")
				.withProcedureName("Proc_Insert_DOCTOR_SHIFT")
				.declareParameters(
						
						new SqlParameter("Ip_doctor_id", Types.INTEGER),
						new SqlParameter("Ip_shift_date", OracleTypes.DATE),
						new SqlParameter("Ip_start_time", OracleTypes.TIMESTAMP),
						new SqlParameter("Ip_end_time", OracleTypes.TIMESTAMP)
						);
		
		MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("Ip_doctor_id", doctorShift.getDoctorId());
			in.addValue("Ip_shift_date", doctorShift.getShiftDate());
			in.addValue("Ip_start_time", doctorShift.getStartTime());
			in.addValue("Ip_end_time", doctorShift.getEndTime());
		jdbcCall.execute(in);
	}
	
	public void updateDoctorShift(DoctorShiftDTO doctorShift) {
		jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("PCK_DOCTOR_SHIFT")
				.withProcedureName("Proc_Update_DOCTOR_SHIFT")
				.declareParameters(
						new SqlParameter("Ip_doctor_shift_id", Types.INTEGER),
						new SqlParameter("Ip_doctor_id", OracleTypes.INTEGER),
						new SqlParameter("Ip_shift_date", OracleTypes.DATE),
						new SqlParameter("Ip_start_time", OracleTypes.TIMESTAMP),
						new SqlParameter("Ip_end_time", OracleTypes.TIMESTAMP)
						);
		
		MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("Ip_doctor_shift_id", doctorShift.getDoctorShiftId());
			in.addValue("Ip_doctor_id", doctorShift.getDoctorId());
			in.addValue("Ip_shift_date", doctorShift.getShiftDate());
			in.addValue("Ip_start_time", doctorShift.getStartTime());
			in.addValue("Ip_end_time", doctorShift.getEndTime());
			jdbcCall.execute(in);
	}
	
	
	
	public void bulkInsertDoctorShifts(List<DoctorShiftDTO> shifts) {
	    try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
	        // Desenvolver la conexión para obtener OracleConnection
	        OracleConnection oracleConn = conn.unwrap(OracleConnection.class);

	        StructDescriptor structDescriptor = StructDescriptor.createDescriptor("T_DOCTOR_SHIFT_REC", oracleConn);
	        ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("T_DOCTOR_SHIFT_TAB", oracleConn);

	        STRUCT[] structs = new STRUCT[shifts.size()];
	        for (int i = 0; i < shifts.size(); i++) {
	            DoctorShiftDTO shift = shifts.get(i);
	            Object[] shiftObj = new Object[] {
	                shift.getDoctorId(),
	                new java.sql.Date(shift.getShiftDate().getTime()),
	                shift.getStartTime(),
	                shift.getEndTime()
	            };
	            structs[i] = new STRUCT(structDescriptor, oracleConn, shiftObj);
	        }

	        ARRAY shiftArray = new ARRAY(arrayDescriptor, oracleConn, structs);

	        SqlParameterSource in = new MapSqlParameterSource()
	            .addValue("Ip_doctor_shifts", shiftArray);

	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withCatalogName("PCK_MASSIVE_DOCTOR_SHIFT")
	            .withProcedureName("Proc_Insert_Bulk_DOCTOR_SHIFT");

	        jdbcCall.execute(in);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public static final class DoctorShiftRowMapper implements RowMapper<DoctorShiftDTO>{

		@Override
		public DoctorShiftDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new DoctorShiftDTO(
					rs.getLong("doctor_shift_id"),
					rs.getInt("doctor_id"),
					rs.getDate("shift_date"),
					rs.getTimestamp("start_time"),
					rs.getTimestamp("end_time")
					);
		}
		
	}

}
