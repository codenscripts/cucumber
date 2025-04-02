package ApachePOI;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    
    /**
     * Reads data from an Excel file
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet to read
     * @return List of rows, where each row is a Map of column names and values
     */
    public static List<Map<String, String>> readExcel(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in the workbook");
            }

            // Get header row
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            // Read data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = "";
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    value = cell.getDateCellValue().toString();
                                } else {
                                    value = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case BOOLEAN:
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            default:
                                value = "";
                        }
                    }
                    rowData.put(headers.get(j), value);
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage());
        }
        
        return data;
    }

    /**
     * Creates a new Excel file with sample data
     * @param filePath Path where to save the Excel file
     */
    public static void createExcel(String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a sheet
            Sheet sheet = workbook.createSheet("Sample Sheet");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Name", "Age", "Email"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Create data rows
            String[][] data = {
                    {"John Doe", "30", "john@example.com"},
                    {"Jane Smith", "25", "jane@example.com"},
                    {"Bob Johnson", "35", "bob@example.com"}
            };

            for (int i = 0; i < data.length; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(data[i][j]);
                }
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save the workbook
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating Excel file: " + e.getMessage());
        }
    }

    /**
     * Example of how to iterate through rows and columns
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet to iterate
     */
    public static void iterateExcel(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in the workbook");
            }

            System.out.println("Iterating through rows and columns:");
            
            // Iterate through rows
            for (Row row : sheet) {
                System.out.println("Row " + row.getRowNum() + ":");
                
                // Iterate through cells in the row
                for (Cell cell : row) {
                    String cellValue;
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
                    System.out.println("  Column " + cell.getColumnIndex() + ": " + cellValue);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error iterating Excel file: " + e.getMessage());
        }
    }

    /**
     * Example of how to update an existing Excel file
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet to update
     * @param rowIndex Row index to update (0-based)
     * @param columnIndex Column index to update (0-based)
     * @param newValue New value to set
     */
    public static void updateExcel(String filePath, String sheetName, int rowIndex, int columnIndex, String newValue) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in the workbook");
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }

            Cell cell = row.getCell(columnIndex);
            if (cell == null) {
                cell = row.createCell(columnIndex);
            }

            cell.setCellValue(newValue);

            // Save the changes
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating Excel file: " + e.getMessage());
        }
    }

    // Example usage
    public static void main(String[] args) {
        String filePath = "sample.xlsx";
        
        // Create a new Excel file
        createExcel(filePath);
        System.out.println("Created new Excel file: " + filePath);
        
        // Read data from the Excel file
        List<Map<String, String>> data = readExcel(filePath, "Sample Sheet");
        System.out.println("\nRead data from Excel:");
        for (Map<String, String> row : data) {
            System.out.println(row);
        }
        
        // Iterate through the Excel file
        System.out.println("\nIterating through Excel:");
        iterateExcel(filePath, "Sample Sheet");
        
        // Update a cell in the Excel file
        updateExcel(filePath, "Sample Sheet", 1, 1, "31");
        System.out.println("\nUpdated Excel file");
        
        // Read the updated data
        data = readExcel(filePath, "Sample Sheet");
        System.out.println("\nRead updated data from Excel:");
        for (Map<String, String> row : data) {
            System.out.println(row);
        }
    }
} 