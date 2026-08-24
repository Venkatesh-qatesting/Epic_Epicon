package epic_Epicon_GenericLibrary;

/**
 * Interface to store test data file paths securely.
 * All file paths are referenced through this interface
 * so locations can be changed in one place.
 */
public interface IPath {

	String PROPERTY_FILE_PATH = System.getProperty("user.dir") + "/TestData/Properties.property";
	String EXCEL_FILE_PATH = System.getProperty("user.dir") + "/TestData/TestData.xlsx";
	String EXCEL_SHEET_NAME = "Login";
	String SCREENSHOT_FOLDER_PATH = System.getProperty("user.dir") + "/Screenshots/";

}
