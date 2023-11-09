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

import com.bolivar.mucuru.model.MedicalUser;

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
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public MedicalUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    

    public MedicalUser getMedicalUserById(Long userId) {
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withCatalogName("PCK_MEDICAL_USER")
        		.withProcedureName("Proc_Get_MEDICAL_USER_BY_ID")
                .declareParameters(
                        new SqlOutParameter("Op_medical_user", OracleTypes.CURSOR, new MedicalUserRowMapper()),
                        new SqlParameter("Ip_user_id", Types.INTEGER)
                    );


        SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_user_id", userId);

        Map<String, Object> out = jdbcCall.execute(in);
        List<MedicalUser> userList = (List<MedicalUser>) out.get("Op_medical_user");

        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    public List<MedicalUser> getAllMedicalUsers() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PCK_MEDICAL_USER")
                .withProcedureName("Proc_Get_All_MEDICAL_USER")
                .declareParameters(new SqlOutParameter("Op_medical_users", OracleTypes.CURSOR, new MedicalUserRowMapper()));

        Map<String, Object> out = jdbcCall.execute();
        return (List<MedicalUser>) out.get("Op_medical_users");
    }

    public MedicalUser getMedicalUserByDocument(String document) {
        
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withCatalogName("PCK_MEDICAL_USER")
        		.withProcedureName("Proc_Get_MEDICAL_USER_BY_DOCUMENT")
                .declareParameters(
                        new SqlOutParameter("Op_medical_user", OracleTypes.CURSOR, new MedicalUserRowMapper()),
                        new SqlParameter("Ip_document", Types.VARCHAR)
                    );


        SqlParameterSource in = new MapSqlParameterSource().addValue("Ip_document", document);

        Map<String, Object> out = jdbcCall.execute(in);
        List<MedicalUser> userList = (List<MedicalUser>) out.get("Op_medical_user");

        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    public void insertMedicalUser(MedicalUser user) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
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

        // Create Map containing parameter values
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

    public void updateMedicalUser(MedicalUser user) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
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

        // Create Map containing parameter values
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
    
    
    private static final class MedicalUserRowMapper implements RowMapper<MedicalUser> {
        @Override
        public MedicalUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MedicalUser(
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


