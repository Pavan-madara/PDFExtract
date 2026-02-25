package com.madara.security.controller;

import com.madara.security.response.DTO.ApiResponse;
import com.madara.security.service.PDFService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("pdf")
@RequiredArgsConstructor
public class PDFController {

    private final PDFService pdfService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Void>> uploadPDF(
            @RequestParam("file") MultipartFile pdfFile
            ) throws IOException {

        pdfService.Store(pdfFile);
        ApiResponse<Void> response =
                ApiResponse.success(null, "PDF Uploaded", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
