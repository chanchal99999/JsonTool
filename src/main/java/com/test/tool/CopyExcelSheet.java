package com.test.tool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

<<<<<<< HEAD
public class CopyExcelSheet {
	public static void main(String[] args) {

		CopyExcelSheet ces = new CopyExcelSheet();

		String excelFilePath = "D:\\chanchal\\AutomationTool\\Spring Tool Suite 4\\JsonTool\\JsonTool\\Test1.xlsx";
//"C:\Users\MahiWay\Desktop\Tool\Test1.xlsx"
		String copySheetName = "Sheet1";

		List<List<String>> selectedRowDataList = ces.getExcelData(excelFilePath, copySheetName, 3, 6);

		ces.createExcelSheetWithData(excelFilePath, selectedRowDataList);

	}

	public static List<List<String>> getExcelData(String excelFilePath, String excelSheetName, int startRow, int endRow) {
=======

public class CopyExcelSheet {
	
	public List<List<String>> getExcelData(String excelFilePath, String excelSheetName) {
>>>>>>> 5cb85a51e0255dbf9f9438876adc0e9c8cd9591b
		List<List<String>> ret = new ArrayList();
		if (excelFilePath != null && !"".equals(excelFilePath.trim()) && excelSheetName != null
				&& !"".equals(excelSheetName.trim())) {
			try {
				FileInputStream fis = new FileInputStream(excelFilePath.trim());
				Workbook excelWookBook = new XSSFWorkbook(fis);
				Sheet copySheet = excelWookBook.getSheet(excelSheetName);
				int fRowNum = copySheet.getFirstRowNum();
				int lRowNum = copySheet.getLastRowNum();
				for (int i = fRowNum + 1; i < lRowNum + 1; i++) {
				//	if (i >= startRow && i <= endRow) {
						Row row = copySheet.getRow(i);
						int fCellNum = row.getFirstCellNum();
						int lCellNum = row.getLastCellNum();
						List<String> rowDataList = new ArrayList<String>();
						for (int j = fCellNum; j < lCellNum; j++) {
							Cell cell = row.getCell(j);
							int cellType = cell.getCellType().getCode();
							if (j == 5) {
								if (cellType == CellType.STRING.getCode()) {
									String cellValue = cell.getStringCellValue();
									rowDataList.add(cellValue);
								}
							}
							if (j == 2) {
								Cell cell4 = row.getCell(4);
								int cellType4 = cell4.getCellType().getCode();
								if (cellType == CellType.STRING.getCode() && cellType4 == CellType.NUMERIC.getCode()) {
									// double numberValue1 = cell4.getNumericCellValue();
									String cellValue = cell.getStringCellValue() + " (" + cell4.getNumericCellValue()
											+ ")";
									rowDataList.add(cellValue);
								}
							}
						}
						ret.add(rowDataList);
				//	}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}

	public void createExcelSheetWithData(String excelFilePath, List<List<String>> dataList) {
		if (excelFilePath != null && !"".equals(excelFilePath.trim())) {
			try {
				FileInputStream fis = new FileInputStream(excelFilePath.trim());
				Workbook wookBook = new XSSFWorkbook(fis);
				Sheet newSheet = null;
				Iterator sheetIterator = wookBook.iterator();
				if (sheetIterator.hasNext()) {
					newSheet = wookBook.cloneSheet(0);
					newSheet.setSelected(true);
					Iterator<Row> rowIterator = newSheet.iterator();
					while (rowIterator.hasNext()) {
						Row cloneRow = rowIterator.next();
						newSheet.removeRow(cloneRow);
						rowIterator = newSheet.iterator();
					}
				} else {
					newSheet = wookBook.createSheet("NewSheet");
				}
				Row headerRow = newSheet.createRow(0);
				headerRow.createCell(0).setCellValue("dist_refid");
				headerRow.createCell(1).setCellValue("name");
				headerRow.createCell(2).setCellValue("features");
				if (dataList != null) {
					int size = dataList.size();
					for (int i = 0; i < size; i++) {
						List<String> cellDataList = dataList.get(i);
						Row row = newSheet.createRow(i + 1);
						int columnNumber = cellDataList.size();
						for (int j = 0; j < columnNumber; j++) {
							if (j == 0) {
								String cellValue = cellDataList.get(j + 1);
								row.createCell(j).setCellValue(cellValue);
							} else {
								String cellValue = cellDataList.get(j - 1);
								row.createCell(j).setCellValue(cellValue);
							}
							String cellValue = "[{\"name\":\"share\",\"options\":[\"google|microsoft|hmh|other\"]}]";
							row.createCell(j + 2).setCellValue(cellValue);
						}
					}
				}
				FileOutputStream fOut = new FileOutputStream(excelFilePath);
				wookBook.write(fOut);
				fOut.close();
				System.out.println("New sheet added in excel file " + excelFilePath + " successfully. ");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}