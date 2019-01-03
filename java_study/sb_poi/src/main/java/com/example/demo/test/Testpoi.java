package com.example.demo.test;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Testpoi {
    public static void main(String[] args) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet0");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("haha");
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,3));
        HSSFRow row1 = sheet.createRow(2);
        row1.createCell(0).setCellValue("杨");
        row1.createCell(1).setCellValue("y");
        row1.createCell(2).setCellValue("豆豆");
        row1.createCell(3).setCellValue("yy");
        FileOutputStream outputStream = new FileOutputStream("E:\\testpoi.xls");
        workbook.write(outputStream);
        outputStream.flush();
    }
}
