package com.madara.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madara.security.response.DTO.PDFFilePathAndUserIDDTO;
import com.madara.security.service.PDFService;
import com.madara.security.utility.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PDFServiceImpl implements PDFService {

    @Value("${application.file.upload-dir}")
    private String uploadDir;

    private final KafkaTemplate<String, PDFFilePathAndUserIDDTO> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void Store(MultipartFile pdf) throws IOException {
        String filePath = uploadDir + pdf.getOriginalFilename();
        long userId = SecurityUtils.getCurrentUserId();
        pdf.transferTo(new File(filePath));
        kafkaTemplate.send(
                "pdf-topic",
                String.valueOf(System.currentTimeMillis()),
                PDFFilePathAndUserIDDTO.builder()
                        .jobID(System.currentTimeMillis())
                        .userId(userId)
                        .pdfFilePath(filePath)
                        .build()
        );
    }

    @Override
    @KafkaListener(topics = "pdf-response", groupId = "spring-pdf-group")
    public void receivesData(ConsumerRecord<String, String> record) {
        try {
            String key = record.key();
            String value = record.value();
            System.out.println("Received Data from Python workers" + key);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
