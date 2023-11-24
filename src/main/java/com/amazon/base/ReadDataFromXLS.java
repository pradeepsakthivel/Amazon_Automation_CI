package com.amazon.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ReadDataFromXLS extends TestUtilities {
//	private Map<String, String> keyVals = new LinkedHashMap<>();
	List<Map<String, String>> response = new LinkedList<Map<String, String>>();

	static int inputRowCount;
	List<Object[]> listObj = new LinkedList<Object[]>();

	@DataProvider(name = "readCSVFile1")
	public Iterator<Object[]> storeDataInMap(ITestNGMethod method, ITestContext context) {
		System.out.println("*** Inside new Reader ***");
		try {
			System.out.println("*** Inside DataProvider : Fetching Data From CSV and Stored the Values in Map***");
			String runtype = context.getCurrentXmlTest().getParameter("runType");
			String env = context.getCurrentXmlTest().getParameter("ENV");
			String className = method.getRealClass().getSimpleName();

			fileName = new TestUtilities().getInputFileNameForTestClass(env, runtype, className);

			System.out.println("*** CSV File Name:  " + runtype + "_" + className + " ***");
			System.out.println("*** File Path: " + fileName + " ***");

			
			File file = new File(fileName);

			// Create an object of FileInputStream class to read excel file
			FileInputStream inputStream = new FileInputStream(file);

			Workbook wb = null;
			DataFormatter objDefaultFormat = new DataFormatter();
			FormulaEvaluator objFormulaEvaluator = null;

			// Check condition if the file is xlsx file
			String fileExtensionName = fileName.substring(fileName.indexOf("."));
			if (fileExtensionName.equals(".xlsx")) {
				//create object of XSSFWorkbook class
				wb = new XSSFWorkbook(inputStream);
				objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
			}

			else if (fileExtensionName.equals(".xls")) {
				//create object of HSSFWorkbook class
				wb = new HSSFWorkbook(inputStream);
				objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
			}

			Sheet sh = wb.getSheet("Sheet1");

			int rowCount = sh.getLastRowNum() - sh.getFirstRowNum();

			ArrayList<String> columnNameList = new ArrayList<String>();
			Row row = sh.getRow(0);
			for (Cell cell : row) {
				columnNameList.add(cell.getStringCellValue());
				System.out.println("Column List:" + columnNameList + " and size:" + columnNameList.size());
			}

			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i < rowCount + 1; i++) {
				Row row1 = sh.getRow(i);
				System.out.println("i value:" + i);
				// Create a loop to print cell values in a row
				HashMap<String, String> temp = new HashMap<String, String>();
				ArrayList<String> columnValueList = new ArrayList<String>();

				for (int j = 0; j < row1.getLastCellNum(); j++) {
					if (row1.getCell(j) == null) {
						columnValueList.add(null);
					} else {
						System.out.println("Cell Value:" + row1.getCell(j).toString());
						Cell cellValue = row1.getCell(j);
						//Evaluate the cell and any type of cell will return string value
						objFormulaEvaluator.evaluate(cellValue); 
						String cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
						columnValueList.add(cellValueStr);
					}
				}

				// populate map
				int len = columnNameList.size();
				System.out.println("columnNameList size:" + len);
				for (int i1 = 0; i1 < len; i1++) {
					temp.put(columnNameList.get(i1), columnValueList.get(i1));
				}
				System.out.println("Map populated succesfully");
				listObj.add((new Object[] { temp }));
				System.out.println("listObj size:" + listObj.size());

				inputRowCount = listObj.size();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listObj.iterator();
	}

}
