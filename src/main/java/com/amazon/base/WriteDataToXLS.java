package com.amazon.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.JsonProcessingException;

public class WriteDataToXLS extends TestUtilities {
	private String k1;
	private String v1;

	public WriteDataToXLS() {
		this.k1 = k1;
		this.v1 = v1;
	}

	public void write(String outputfilename, Map<String, String[]> a) {
		System.out.println("*** Inside new Reader ***");
		try {
			File outputfile = new File(outputfilename);
			System.out.println("Output File from write Data to XLS: " + outputfilename);

			FileInputStream inputStream = new FileInputStream(outputfilename);
			Workbook wb = null;

			String fileExtensionName = fileName.substring(fileName.indexOf("."));

			if (fileExtensionName.equals(".xlsx")) {
				System.out.println("File extension is .xlsx :" + fileExtensionName);
				wb = new XSSFWorkbook(inputStream);
			} else if (fileExtensionName.equals(".xls")) {
				System.out.println("File extension is .xls  :" + fileExtensionName);
				wb = new HSSFWorkbook(inputStream);
			}

			Sheet sh = wb.getSheet("Sheet1");

			int rowId = 0;
			int rowCount = sh.getLastRowNum() - sh.getFirstRowNum();
			Row r = sh.getRow(rowId);
			int cellId = r.getLastCellNum();
			//System.out.println("RowId"+rowId);
			//System.out.println("Last cell in RowId "+rowId+":"+cellId);
			Cell cell;

						// Print Map
						System.out.println("Printing result Map");
						// Iterate through the entries in the map
				        for (Map.Entry<String, String[]> entry : results.entrySet()) {
				            String key = entry.getKey();
				            String[] values = entry.getValue();
			
				            System.out.print("Key: " + key + ", Values: [");
			
				            // Iterate through the string array and print the values
				            for (int i = 0; i < values.length; i++) {
				                System.out.print(values[i]);
				                if (i < values.length - 1) {
				                    System.out.print(", ");
				                }
				            }
			
				            System.out.println("]");
				        }
				        System.out.println("Printed result Map");

			// Write to excel file						
			for (int i = 0; i <= rowCount; i++) {
				Row r2 = sh.getRow(i);
				String key = r2.getCell(1).getStringCellValue();
//				System.out.println("Key Element for testscenario name: " + key);
				if (results.containsKey(key)) {
					String[] colVal = results.get(key);
//					System.out.println("String array for column to be added: " + Arrays.asList(colVal));
					int cellToWrite = cellId;
//					System.out.println("Cellto write:" + cellToWrite);
					for (int j = 0; j < colVal.length; j++) {

						r2.createCell(cellToWrite).setCellValue(colVal[j]);
						cellToWrite++;
					}
				}
			}
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(outputfile);

			wb.write(out);
			out.close();
			System.out.println("Test result written successfully");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void printTree(TreeMap<String, String[]> a) {
		Set<Map.Entry<String, String[]>> entries = a.entrySet();

		// printing keys and values using for loop
		for (Map.Entry<String, String[]> entry : entries) {
			System.out.println(entry.getKey().toString() + "=>" + entry.getValue().toString());
		}
	}

	public String getId() {
		return k1;
	}

	public String getName() {
		return v1;
	}

	 // Override this method in your custom class used as key or value in the TreeMap
	
	public String toString() {
		return "[" + this.getId() + "=>" + this.getName() + "]";
	}
}
