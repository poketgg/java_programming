package com.example;

import org.apache.poi.xssf.usermodel.*;
import java.io.FileInputStream;

public class XLSXTextExtractor {
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream("파일이름.xlsx"); // 같은 폴더에 넣을 것
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheet.getRow(i);
                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                    XSSFCell cell = row.getCell(j);
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println();
            }

            workbook.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
