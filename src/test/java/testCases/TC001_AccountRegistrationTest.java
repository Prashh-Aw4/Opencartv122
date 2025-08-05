package testCases;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test (groups= {"Regression","Master"})
	public void verify_account_registration() {

		logger.info("*** Started TC001_AccountRegistrationTest ***");

		try {

			HomePage h = new HomePage(driver);
			h.clickMyAccount();
			logger.info("Clicked on My Account link");
			h.clickRegister();
			logger.info("Clicked on My Registration link");
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			logger.info("Provide Registration Information");
			regpage.setFirstName(randomString().toUpperCase());
			regpage.setLastName(randomString().toUpperCase());
			regpage.setEmail(randomString() + "@" + "gmail.com");
			regpage.setTelephone(randomNumber());

			String passs = randomPassword();
			regpage.setPassword(passs);
			regpage.setConfirmPassword(passs);
			regpage.setPrivacyPloicy();
			regpage.clickContinue();

			logger.info("validate Expected Msg");
			String confmsg = regpage.getConfirmation();

			if (confmsg.equals("Your Account Has Been Created!")) {

				Assert.assertTrue(true);
			} else {
				logger.info("Test Case Failed...");
				logger.debug("Debug Logs");
				Assert.assertTrue(false);
			}
			// Assert.assertEquals(confmsg, "Your Account Has Been Created!!");
		} catch (Exception e) {

			Assert.fail();
		}

		logger.info("*** Finished TC001_AccountRegistrationTest*** ");
	}

}
