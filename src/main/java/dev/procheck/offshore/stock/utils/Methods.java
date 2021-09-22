package dev.procheck.offshore.stock.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.monitorjbl.xlsx.StreamingReader;

import dev.procheck.offshore.stock.models.Carton;

public class Methods {

	public static List<Carton> dataCarton(File file, String name) throws IOException, InvalidFormatException {
		System.out.println("Start methode");
		//FileInputStream fileExcel = new FileInputStream(file);
		OPCPackage pkg = OPCPackage.open(new FileInputStream(file.getAbsolutePath()));
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(pkg);
		XSSFSheet sheet = wb.getSheet(name);
		List<Carton> cartons = new ArrayList<Carton>();
		Carton carton = new Carton();
		Iterator<Row> itr = sheet.iterator();
		String typeContrat = "";
		String numCarton = "";
		String dateCaractirisation = "";
		int i = 0;
		try {
			while (itr.hasNext()) {
				Row row = itr.next();
				XSSFCell numC = (XSSFCell) row.getCell(0);
				XSSFCell dateC = (XSSFCell) row.getCell(14);
				XSSFCell typeC = (XSSFCell) row.getCell(23);
				if (i > 0) {
					if (checkCellString(numC)) {
						numCarton = numC.getStringCellValue();
						dateCaractirisation = dateC.getStringCellValue();
						typeContrat = typeC.getStringCellValue();
						Date DateC = new SimpleDateFormat("dd/MM/yyyy").parse(dateCaractirisation);
						carton = new Carton();
						carton.setNumero(numCarton);
						carton.setTypeCarton(typeContrat);
						carton.setDateCaractirisation(DateC);
						cartons.add(carton);
					}

				}
				i++;

			}
		} catch (Exception ex) {
			System.out.println("Erreur : " + ex);
		}

		return cartons;
	}

	public static Properties loadFileInit() {
		Properties configProp = new Properties();
		try {
			String settingsPropertyFile = Methods.class.getResource("/Config.properties").getFile();
			FileReader fReader = new FileReader(settingsPropertyFile);
			configProp.load(fReader);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return configProp;
	}

	public static XSSFSheet getSheetFromFile(File file, String name) throws IOException {
		try {
			FileInputStream fileExcel = new FileInputStream(file);
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(fileExcel);
			XSSFSheet sheet = wb.getSheet(name);
			return sheet;
			
		} catch (Exception ex) {
			System.out.println("Erreur : " + ex);
		}

		return null;
	}
	public static boolean checkSheet(File file, String name) throws IOException
	{
		boolean existe=true;/*
		FileInputStream fileExcel = new FileInputStream(file);
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fileExcel);
		if (wb.getSheetIndex(name)!=-1) {
		    existe=true;
		  } */
		return existe;
	}
	public static boolean checkCellString(Cell cell) {
		try {

			cell.getStringCellValue();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean checkCellDate(Cell cell) {
		try {

			cell.getDateCellValue();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean checkCellDoule(Cell cell) {
		try {

			cell.getNumericCellValue();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	
public static List<Carton> dataCarton2(File file, String name) throws IOException, InvalidFormatException, ParseException {
	FileInputStream is = new FileInputStream(file);
	StreamingReader reader = StreamingReader.builder()
	        .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
	        .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
	        .sheetName(name)        // index of sheet to use (defaults to 0)
	        .read(is);            // InputStream or File for XLSX file (required)
	String typeContrat = "";
	String numCarton = "";
	String dateCaractirisation = "";
	List<Carton> cartons = new ArrayList<Carton>();
	Carton carton = new Carton();
	int i = 0;
	for (Row row : reader) {
		Cell numC =  row.getCell(0);
		Cell dateC =  row.getCell(14);
		Cell typeC =  row.getCell(23);
		if (checkCellString(numC)) 
		  		//System.out.println(numC.getStringCellValue()+" : "+dateC.getStringCellValue()+" : "+typeC.getStringCellValue());  
				if (i > 0) {
					if (checkCellString(numC)) {
						numCarton = numC.getStringCellValue();
						dateCaractirisation = dateC.getStringCellValue();
						typeContrat = typeC.getStringCellValue();
						Date DateC = new SimpleDateFormat("dd/MM/yyyy").parse(dateCaractirisation);
						carton = new Carton();
						carton.setNumero(numCarton);
						carton.setTypeCarton(typeContrat);
						carton.setDateCaractirisation(DateC);
						cartons.add(carton);
					}

				}
				i++;
		  
		}    
		/*@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheet(name);
		List<Carton> cartons = new ArrayList<Carton>();
		Carton carton = new Carton();
		Iterator<Row> itr = sheet.iterator();
		String typeContrat = "";
		String numCarton = "";
		String dateCaractirisation = "";
		int i = 0;
		try {
			while (itr.hasNext()) {
				Row row = itr.next();
				XSSFCell numC = (XSSFCell) row.getCell(0);
				XSSFCell dateC = (XSSFCell) row.getCell(14);
				XSSFCell typeC = (XSSFCell) row.getCell(23);
				if (i > 0) {
					if (checkCellString(numC)) {
						numCarton = numC.getStringCellValue();
						dateCaractirisation = dateC.getStringCellValue();
						typeContrat = typeC.getStringCellValue();
						Date DateC = new SimpleDateFormat("dd/MM/yyyy").parse(dateCaractirisation);
						carton = new Carton();
						carton.setNumero(numCarton);
						carton.setTypeCarton(typeContrat);
						carton.setDateCaractirisation(DateC);
						cartons.add(carton);
					}

				}
				i++;

			}
		} catch (Exception ex) {
			System.out.println("Erreur : " + ex);
		}*/

		return cartons;
	}
}
