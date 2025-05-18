package com.translator.controller;

import com.translator.service.PdfTranslateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/translate")
@CrossOrigin(origins = "http://127.0.0.1:5500") // 라이브서버 주소 설정
public class PdfController {

    private final PdfTranslateService pdfTranslateService;

    public PdfController(PdfTranslateService pdfTranslateService) {
        this.pdfTranslateService = pdfTranslateService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<String> translatePdf(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어있습니다.");
        }

        String result = pdfTranslateService.translatePdf(file);
        return ResponseEntity.ok(result);
    }
}
