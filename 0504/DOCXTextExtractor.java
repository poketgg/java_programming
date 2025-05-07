package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.List;

public class DOCXTextExtractor {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("파일이름.docx"); // 같은 폴더에 넣을 것
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph para : paragraphs) {
                System.out.println(para.getText());
            }

            document.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
