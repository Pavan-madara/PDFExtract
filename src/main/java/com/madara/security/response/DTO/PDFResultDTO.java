package com.madara.security.response.DTO;

import lombok.Data;

@Data
public class PDFResultDTO {
    private String jobId;
    private String status;
    private String extractedText;
    private long processedAt;
}
