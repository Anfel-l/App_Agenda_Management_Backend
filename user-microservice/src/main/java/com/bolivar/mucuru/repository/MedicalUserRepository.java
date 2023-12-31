package com.bolivar.mucuru.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bolivar.mucuru.dto.MedicalUserDTO;
import com.bolivar.mucuru.dto.MedicalUserDetailDTO;

import oracle.jdbc.OracleTypes;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MedicalUserRepository {

	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall jdbcCall;

    @Autowired
    public MedicalUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public MedicalUserDTO getMedicalUserById(Long userId) {
    	jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withCatalogName("PCK_MEDICAL_USER")
        		.withProcedureName("Proc_Get_MEDICAL_USER_BY_ID")
                .declareParameters(
                        new SqlOutParameter("Op_medical_user", OracleTypes.CURSOR, new MedicalUserRowMapper()),
                        new SqlParameter("Ip_user_id", Types.INTEGER)
                    );


        SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_user_id", userId);

        Map<String, Object> out = jdbcCall.execute(in);
        List<MedicalUserDTO> userList = (List<MedicalUserDTO>) out.get("Op_medical_user");

        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    public List<MedicalUserDTO> getAllMedicalUsers() {
        jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PCK_MEDICAL_USER")
                .withProcedureName("Proc_Get_All_MEDICAL_USER")
                .declareParameters(new SqlOutParameter("Op_medical_users", OracleTypes.CURSOR, new MedicalUserRowMapper()));

        Map<String, Object> out = jdbcCall.execute();
        return (List<MedicalUserDTO>) out.get("Op_medical_users");
    }

    public MedicalUserDTO getMedicalUserByDocument(String document) {
        
        jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withCatalogName("PCK_MEDICAL_USER")
        		.withProcedureName("Proc_Get_MEDICAL_USER_BY_DOCUMENT")
                .declareParameters(
                        new SqlOutParameter("Op_medical_user", OracleTypes.CURSOR, new MedicalUserRowMapper()),
                        new SqlParameter("Ip_document", Types.VARCHAR)
                    );


        SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_document", document);

        Map<String, Object> out = jdbcCall.execute(in);
        List<MedicalUserDTO> userList = (List<MedicalUserDTO>) out.get("Op_medical_user");

        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    public void insertMedicalUser(MedicalUserDTO user) {
        jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withCatalogName("PCK_MEDICAL_USER")
        		.withProcedureName("Proc_Insert_MEDICAL_USER")
                .declareParameters(
                    new SqlParameter("Ip_first_name", Types.VARCHAR),
                    new SqlParameter("Ip_second_name", Types.VARCHAR),
                    new SqlParameter("Ip_last_name", Types.VARCHAR),
                    new SqlParameter("Ip_document_type_id", Types.INTEGER),
                    new SqlParameter("Ip_document", Types.VARCHAR),
                    new SqlParameter("Ip_password", Types.VARCHAR),
                    new SqlParameter("Ip_contract_type_id", Types.INTEGER),
                    new SqlParameter("Ip_location_id", Types.INTEGER),
                    new SqlParameter("Ip_email", Types.VARCHAR)
                );

        MapSqlParameterSource in = new MapSqlParameterSource();
                in.addValue("Ip_first_name", user.getFirstName());
                in.addValue("Ip_second_name", user.getSecondName());
                in.addValue("Ip_last_name", user.getLastName());
                in.addValue("Ip_document_type_id", user.getDocumentTypeId());
                in.addValue("Ip_document", user.getDocument());
                in.addValue("Ip_password", user.getPassword());
                in.addValue("Ip_contract_type_id", user.getContractTypeId());
                in.addValue("Ip_location_id", user.getLocationId());
                in.addValue("Ip_email", user.getEmail());
        jdbcCall.execute(in);
    }

    public void updateMedicalUser(MedicalUserDTO user) {
        jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withCatalogName("PCK_MEDICAL_USER")
        		.withProcedureName("Proc_Update_MEDICAL_USER")
                .declareParameters(
                    new SqlParameter("Ip_user_id", Types.INTEGER),
                    new SqlParameter("Ip_first_name", Types.VARCHAR),
                    new SqlParameter("Ip_second_name", Types.VARCHAR),
                    new SqlParameter("Ip_last_name", Types.VARCHAR),
                    new SqlParameter("Ip_document_type_id", Types.INTEGER),
                    new SqlParameter("Ip_document", Types.VARCHAR),
                    new SqlParameter("Ip_password", Types.VARCHAR),
                    new SqlParameter("Ip_contract_type_id", Types.INTEGER),
                    new SqlParameter("Ip_location_id", Types.INTEGER),
                    new SqlParameter("Ip_email", Types.VARCHAR)
                );
        
        MapSqlParameterSource in = new MapSqlParameterSource();
        		in.addValue("Ip_user_id", user.getUserId());
                in.addValue("Ip_first_name", user.getFirstName());
                in.addValue("Ip_second_name", user.getSecondName());
                in.addValue("Ip_last_name", user.getLastName());
                in.addValue("Ip_document_type_id", user.getDocumentTypeId());
                in.addValue("Ip_document", user.getDocument());
                in.addValue("Ip_password", user.getPassword());
                in.addValue("Ip_contract_type_id", user.getContractTypeId());
                in.addValue("Ip_location_id", user.getLocationId());
                in.addValue("Ip_email", user.getEmail());
        jdbcCall.execute(in);
    }
    
    public MedicalUserDetailDTO getMedicalUserDetail(Long userId) {
    	jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withCatalogName("PCK_GET_DETAILS")
        		.withProcedureName("Proc_GET_USER_DETAILS")
                .declareParameters(
                        new SqlOutParameter("Op_details", OracleTypes.CURSOR, new MedicalUserDetailRowMapper()),
                        new SqlParameter("Ip_user_id", Types.INTEGER)
                    );

        SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_user_id", userId);
        Map<String, Object> out = jdbcCall.execute(in);
        List<MedicalUserDetailDTO> userList = (List<MedicalUserDetailDTO>) out.get("Op_details");

        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }
    
    private static final class MedicalUserDetailRowMapper implements RowMapper<MedicalUserDetailDTO>{

		@Override
		public MedicalUserDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MedicalUserDetailDTO(             
                rs.getString("first_name"),
                rs.getString("second_name"),
                rs.getString("last_name"),
                rs.getString("document"),
                rs.getString("document_type"),
                rs.getString("contract_type_name"),
                rs.getString("location_name")
            );
		}    	
    }
    
    private static final class MedicalUserRowMapper implements RowMapper<MedicalUserDTO> {
        @Override
        public MedicalUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MedicalUserDTO(
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("second_name"),
                rs.getString("last_name"),
                rs.getInt("document_type_id"),
                rs.getString("document"),
                rs.getString("password"),
                rs.getInt("contract_type_id"),
                rs.getInt("location_id"),
                rs.getString("email")
            );
        }
    }
}


