package com.bolivar.mucuru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeDTO {
    private Long documenTypeId;
    private String documentTypeAbbreviation;
    private String description;
}
