package com.translator.service;

import com.translator.GoogleTranslateService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class PdfTranslateService {

    private final GoogleTranslateService googleTranslateService;

    public PdfTranslateService(GoogleTranslateService googleTranslateService) {
        this.googleTranslateService = googleTranslateService;
    }

    // PDF 파일에서 텍스트 추출
    private String extractTextFromPdf(MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream();
             PDDocument document = PDDocument.load(is)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    // PDF 파일을 번역하는 메서드
        public String translatePdf(MultipartFile file) {
        try {
            // 1. PDF 텍스트 추출
            String originalText = extractTextFromPdf(file).trim();

            // 2. 줄바꿈 통일
            originalText = originalText.replaceAll("\\r\\n|\\r|\\n", "\n");

            // 3. Google Translate API 호출
            String translatedText = googleTranslateService.translateText(originalText, "en", "ko");

            // 4. URL 디코딩 (예: %0A → 실제 줄바꿈 문자)
            String decodedText = java.net.URLDecoder.decode(translatedText, java.nio.charset.StandardCharsets.UTF_8);

            // 5. 줄바꿈 → <br> 처리 (HTML 표시용)
            decodedText = decodedText.replaceAll("\n", "<br>");

            return decodedText;

        } catch (Exception e) {
            e.printStackTrace();
            return "PDF 번역 중 오류 발생: " + e.getMessage();
    }
}
}
