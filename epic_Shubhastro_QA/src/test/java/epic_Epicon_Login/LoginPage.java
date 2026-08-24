package epic_Epicon_Login;

import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import epic_Epicon_GenericLibrary.BaseClass;
import epic_Epicon_GenericLibrary.ListenerClass;

@Listeners(ListenerClass.class)
public class LoginPage extends BaseClass {

	@Test
	public void verifyLogin() {
		Reporter.log("Login test executed successfully.", true);
	}
	
	@Test
	public void verifyLoginWithInvalidCredentials() {
		Reporter.log("Login with invalid credentials test executed successfully.", true);
	}
	
}
