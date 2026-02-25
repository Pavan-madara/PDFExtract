package com.madara.security.service.impl;

import com.madara.security.response.DTO.PDFFilePathAndUserIDDTO;
import com.madara.security.service.PDFService;
import com.madara.security.utility.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PDFServiceImpl implements PDFService {

    private final KafkaTemplate<String, PDFFilePathAndUserIDDTO> kafkaTemplate;

    @Override
    public void Store(MultipartFile pdf) throws IOException {
        String uploadDir = "C:/Users/nichiuser/Desktop/PDF Extracter Project/uploads/";
        String filePath = uploadDir + pdf.getOriginalFilename();
        long userId = SecurityUtils.getCurrentUserId();
        pdf.transferTo(new File(filePath));
        kafkaTemplate.send("pdf-topic", new PDFFilePathAndUserIDDTO(filePath, userId));
    }
}
