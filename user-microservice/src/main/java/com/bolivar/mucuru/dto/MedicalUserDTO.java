package com.bolivar.mucuru.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalUserDTO {

    private Long userId;
    private String firstName;
    private String secondName;
    private String lastName;
    private Integer documentTypeId;
    private String document;
    private String password;
    private Integer contractTypeId;
    private Integer locationId;
    private String email;
}
