package com.madara.security.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PDFService {
    void Store(MultipartFile pdf) throws IOException;
}
