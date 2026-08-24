package epic_Epicon_GenericLibrary;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class File_Utilities_TestData implements IPath {

	public String PropertyFileData(String Keys) throws IOException {
		FileInputStream fis = new FileInputStream(PROPERTY_FILE_PATH);
		Properties Propertyfiledata = new Properties();
		Propertyfiledata.load(fis);
		String keys = Propertyfiledata.getProperty(Keys);
		fis.close();
		return keys;
	}

	public String ExcelTestData(String sheetName, int rowNo, int columnNo) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
		Workbook wb = WorkbookFactory.create(fis);
		String TestData = wb.getSheet(sheetName).getRow(rowNo).getCell(columnNo).getStringCellValue();
		wb.close();
		fis.close();
		return TestData;
	}

	/**
	 * Reads all rows from Excel sheet and returns as 2D Object array.
	 * Used as DataProvider for data-driven testing.
	 * Row 0 is treated as header and skipped.
	 */
	public Object[][] getExcelDataForDataProvider(String sheetName) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheet(sheetName);

		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();

		// Exclude header row (row 0), so data starts from row 1
		Object[][] data = new Object[totalRows - 1][totalCols];

		for (int i = 1; i < totalRows; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < totalCols; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					data[i - 1][j] = "";
				} else if (cell.getCellType() == CellType.NUMERIC) {
					data[i - 1][j] = String.valueOf((long) cell.getNumericCellValue());
				} else {
					data[i - 1][j] = cell.getStringCellValue();
				}
			}
		}

		wb.close();
		fis.close();
		return data;
	}
}
