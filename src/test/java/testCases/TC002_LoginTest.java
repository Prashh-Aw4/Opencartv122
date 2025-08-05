package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test (groups = {"Sanity","Master"})
	public void verify_login() {
		
		logger.info("*** TC002_LoginTest Started ***");
		try {
		HomePage h = new HomePage(driver);
		logger.info("Click on My Account Page");
		h.clickMyAccount();
		logger.info("Click On Login");
		h.clickLogin();
		
		logger.info("On Login Page");
		LoginPage lp = new LoginPage(driver);
		logger.info("Login page opens");
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		MyAccountPage macc =new MyAccountPage(driver);
		logger.info("Check if Account page is displayed or not");
		boolean targetpage =macc.isMyAccountPageExists();
		
		Assert.assertEquals(targetpage, true,"Login Failed");
		}
		catch(Exception e) {
			Assert.fail();
		}
		logger.info("*** TC002_LoginTest Finished ***");
	}

}
