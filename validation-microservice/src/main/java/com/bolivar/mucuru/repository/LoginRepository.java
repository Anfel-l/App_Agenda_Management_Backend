package com.bolivar.mucuru.repository;

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.bolivar.mucuru.dto.LoginResponseDTO;

@Repository
public class LoginRepository {
	private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public LoginRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public LoginResponseDTO userLogin(int documentTypeId, String document, String password) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PCK_USER_LOGIN")
                .withProcedureName("Proc_User_Login")
                .declareParameters(
                        new SqlOutParameter("Op_UserId", Types.INTEGER),
                        new SqlOutParameter("Op_Result", Types.VARCHAR),
                        new SqlParameter("Ip_document_type_id", Types.INTEGER),
                        new SqlParameter("Ip_Document", Types.VARCHAR),
                        new SqlParameter("Ip_Password", Types.VARCHAR)
                );
        
        MapSqlParameterSource in = new MapSqlParameterSource();
        		in.addValue("Ip_document_type_id", documentTypeId);
                in.addValue("Ip_Document", document);
                in.addValue("Ip_Password", password);
        
        Map<String, Object> out = jdbcCall.execute(in);
        
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUserId(((Number) out.get("Op_UserId")).longValue());
        response.setResultMessage((String) out.get("Op_Result"));
        
        return response;
    }

    
}
