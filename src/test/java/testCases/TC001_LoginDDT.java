package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;


import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC001_LoginDDT extends BaseClass {
	
	@Test (dataProvider="LoginData",dataProviderClass=DataProviders.class , groups={"DataDriven"})
	public void verify_LoginDDT (String email , String pwd , String exp) {
		 
		logger.info("*** Starting TC001_LoginDDT ****");
		try {
		//Homepage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//My Account Page
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetpage = macc.isMyAccountPageExists();
		//macc.clickLogout();
		
		if (exp.equalsIgnoreCase("valid")) {
			
		   if (targetpage==true) {
			   
			   macc.clickLogout();
			   Assert.assertTrue(true);
		   }
		   else {
			   Assert.assertTrue(false);
		   }
		   
		   if (exp.equalsIgnoreCase("invalid")) {
			   
			   if (targetpage==true) {
				   macc.clickLogout();
				  Assert.assertTrue(false);
				  }
			   else {
				   Assert.assertTrue(true);
			   }
		   }
			
		}
		}
		catch (Exception e) {
			Assert.fail();
			
		}
		
		logger.info("*** Ending TC001_LoginDDT ****");
		
	}

}
