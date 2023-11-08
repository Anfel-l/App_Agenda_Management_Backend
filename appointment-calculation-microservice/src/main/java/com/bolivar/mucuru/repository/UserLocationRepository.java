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

import com.bolivar.mucuru.dto.MedicalCenterDTO;

import oracle.jdbc.OracleTypes;

@Repository
public class UserLocationRepository {
	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public UserLocationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    public List<MedicalCenterDTO> getCentersByUserLocation(Long userId){
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
    			.withCatalogName("PCK_USER_LOCATION")
    			.withProcedureName("Proc_Get_Centers_By_User_Location")
    			.declareParameters(
    					new SqlOutParameter("Op_MEDICAL_CENTERS", OracleTypes.CURSOR, new MedicalCenterRowMapper()),
    					new SqlParameter("Ip_user_id", OracleTypes.NUMBER)
    					);
    	
    	SqlParameterSource in =  new MapSqlParameterSource()
    			.addValue("Ip_user_id", userId);
    	
    	Map<String, Object> out = jdbcCall.execute(in);
    	List<MedicalCenterDTO> medicalCenterList = (List<MedicalCenterDTO>) out.get("Op_MEDICAL_CENTERS");
    	
    	
    	return medicalCenterList;
    }
    
    

    public static final class MedicalCenterRowMapper implements RowMapper<MedicalCenterDTO>{

		@Override
		public MedicalCenterDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new MedicalCenterDTO(
					rs.getLong("medical_center_id"),
					rs.getString("medical_center_name"),
					rs.getInt("location_id")
					);
		}	
    }
}
