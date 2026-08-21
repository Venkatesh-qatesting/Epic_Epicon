package epic_Epicon_GenericLibrary;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class File_Utilities_TestData {
	
	public String PropertyFileData(String Keys)  throws IOException {
	
	FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/TestData/Properties.property");
	Properties Propertyfiledata = new Properties();
	Propertyfiledata.load(fis);
	String keys = Propertyfiledata.getProperty(Keys);
	return keys;
	}
	
	public String ExcelTestData(String Sheet, int rowNo, int columnNo) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/TestData/TestData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		String TestData= wb.getSheet(Sheet).getRow(rowNo).getCell(columnNo).getStringCellValue();
		wb.close();
		fis.close();
		return TestData;
		
	}
}
