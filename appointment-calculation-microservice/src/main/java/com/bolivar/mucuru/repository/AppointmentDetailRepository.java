package com.bolivar.mucuru.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.bolivar.mucuru.dto.AppointmentDetailDTO;
import oracle.jdbc.OracleTypes;

@Repository
public class AppointmentDetailRepository {

	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;
    
    @Autowired
    public AppointmentDetailRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public int insertAppointmentDetails(AppointmentDetailDTO appointment) {
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
    			.withCatalogName("PCK_APPOINTMENT_DETAILS")
    			.withProcedureName("Proc_Enter_Appointment_Details")
    			.declareParameters(
    					new SqlParameter("Ip_User_Id", OracleTypes.NUMBER),
    					new SqlParameter("Ip_MedicalAppointmentTypeId", OracleTypes.NUMBER),
    					new SqlParameter("Ip_Medical_fieldId", OracleTypes.NUMBER),
    					new SqlParameter("Ip_SymptomId", OracleTypes.NUMBER),
    					new SqlOutParameter("Op_AppointmentId", OracleTypes.NUMBER)
    					);
  
    	MapSqlParameterSource in = new MapSqlParameterSource();
    		in.addValue("Ip_User_Id", appointment.getUserId());
    		in.addValue("Ip_MedicalAppointmentTypeId", appointment.getMedicalAppointmentTypeId());
    		in.addValue("Ip_Medical_fieldId", appointment.getMedicalFieldId());
    		in.addValue("Ip_SymptomId", appointment.getSymptomId());
    		
    		Map<String, Object> out = jdbcCall.execute(in);
    		Integer appointmentId = (Integer) out.get("Op_AppointmentId");
    		
    	    return appointmentId;    	
    }
}








