package com.madara.security.response.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PDFFilePathAndUserIDDTO {
    private String pdfFilePath;
    private long userId;
}
