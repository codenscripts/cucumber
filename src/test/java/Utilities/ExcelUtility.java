package Utilities;

import io.cucumber.java.Scenario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ExcelUtility class provides reusable methods for Excel file operations.
 * It supports reading from and writing to Excel files using Apache POI.
 */
public class ExcelUtility {
    
    /**
     * Reads data from an Excel file and returns it as a List of Lists.
     * Each inner List represents a row from the Excel sheet.
     *
     * @param path Path to the Excel file
     * @param sheetName Name of the sheet to read from
     * @param columnCount Number of columns to read
     * @return List of Lists containing the Excel data
     */
    public static List<List<String>> getListData(String path, String sheetName, int columnCount) {
        List<List<String>> returnList = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(path);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in the workbook");
            }

            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 0; i < rowCount; i++) {
                List<String> rowList = new ArrayList<>();
                Row row = sheet.getRow(i);
                
                if (row != null) {
                    for (int j = 0; j < columnCount; j++) {
                        Cell cell = row.getCell(j);
                        String cellValue = "";
                        
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    cellValue = cell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        cellValue = cell.getDateCellValue().toString();
                                    } else {
                                        cellValue = String.valueOf(cell.getNumericCellValue());
                                    }
                                    break;
                                case BOOLEAN:
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                default:
                                    cellValue = "";
                            }
                        }
                        rowList.add(cellValue);
                    }
                }
                returnList.add(rowList);
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }

        return returnList;
    }

    /**
     * Writes test execution results to an Excel file.
     * Creates a new file if it doesn't exist, or appends to existing file.
     *
     * @param path Path to the Excel file
     * @param scenario Cucumber scenario object containing test information
     * @param browserName Name of the browser used for the test
     * @param time Time of test execution
     */
    public static void writeExcel(String path, Scenario scenario, String browserName, String time) {
        File file = new File(path);
        String sheetName = "TestResults";

        try {
            if (!file.exists()) {
                // Create new workbook and sheet
                try (XSSFWorkbook workbook = new XSSFWorkbook();
                     FileOutputStream outputStream = new FileOutputStream(path)) {
                    
                    XSSFSheet sheet = workbook.createSheet(sheetName);
                    
                    // Create header row
                    Row headerRow = sheet.createRow(0);
                    String[] headers = {"Scenario ID", "Status", "Browser", "Time"};
                    for (int i = 0; i < headers.length; i++) {
                        headerRow.createCell(i).setCellValue(headers[i]);
                    }
                    
                    // Create data row
                    Row dataRow = sheet.createRow(1);
                    dataRow.createCell(0).setCellValue(scenario.getId());
                    dataRow.createCell(1).setCellValue(scenario.getStatus().toString());
                    dataRow.createCell(2).setCellValue(browserName);
                    dataRow.createCell(3).setCellValue(time);
                    
                    workbook.write(outputStream);
                }
            } else {
                // Append to existing file
                try (FileInputStream inputStream = new FileInputStream(path);
                     Workbook workbook = WorkbookFactory.create(inputStream);
                     FileOutputStream outputStream = new FileOutputStream(path)) {
                    
                    Sheet sheet = workbook.getSheet(sheetName);
                    if (sheet == null) {
                        sheet = workbook.createSheet(sheetName);
                    }
                    
                    int rowCount = sheet.getPhysicalNumberOfRows();
                    Row row = sheet.createRow(rowCount);
                    
                    row.createCell(0).setCellValue(scenario.getId());
                    row.createCell(1).setCellValue(scenario.getStatus().toString());
                    row.createCell(2).setCellValue(browserName);
                    row.createCell(3).setCellValue(time);
                    
                    workbook.write(outputStream);
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Example usage of the ExcelUtility class.
     * Demonstrates how to read data from an Excel file.
     */
    public static void main(String[] args) {
        // Example: Read data from Excel file
        List<List<String>> data = getListData(
            "src/test/resources/testData.xlsx",
            "TestData",
            4
        );
        
        // Display the data
        for (List<String> row : data) {
            System.out.println(row);
        }
    }
} 