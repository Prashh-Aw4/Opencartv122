package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BaseClass {

	public static  WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass (groups= {"Sanity","Regression","Master","DataDriven"})
	@Parameters ({"os" ,"browser"})
	
	public void setUp(String os ,String br) throws IOException {
		
		//loading config.properties file 
		
		FileReader file = new FileReader("./src//test//resources//config.properties");
		
		p = new Properties();
		p.load(file);
		
		logger = LogManager.getLogger(this.getClass());
		
				
		// setting up Platform
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			DesiredCapabilities capabilites = new DesiredCapabilities();
			
			if (os.equalsIgnoreCase("Windows")) {
				capabilites.setPlatform(Platform.WIN11);
			}
			
			else if (os.equalsIgnoreCase("Linux")) {
				capabilites.setPlatform(Platform.LINUX);
			}
			else if (os.equalsIgnoreCase("Mac")) {
				capabilites.setPlatform(Platform.MAC);
			}
			else {
				System.out.println("invalid operating system");
				return;
			}
			switch (br.toLowerCase()) {
			case "chrome":capabilites.setBrowserName("chrome");break;
			case "edge" :capabilites.setBrowserName("MicrosoftEdge");break;
			default:System.out.println("invalid browser");return;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilites);
		}
		
		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			
			switch (br.toLowerCase()) {
			case "chrome":driver = new ChromeDriver();break;
			case "edge" :driver = new EdgeDriver();break;
			default:System.out.println("invalid browser");return;
			}
		}
	
			
						
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appUrl1"));
		driver.manage().window().maximize();

	}
		

	@AfterClass (groups= {"Sanity","Regression","Master","DataDriven"})
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {

		String generatedstring = RandomStringUtils.randomAlphabetic(4);
		return generatedstring;

	}

	public String randomNumber() {

		String generaterandomnumber = RandomStringUtils.randomNumeric(9);
		return generaterandomnumber;
	}

	public String randomPassword() {

		String generatedstring = RandomStringUtils.randomAlphabetic(3);
		String generaterandomnumber = RandomStringUtils.randomNumeric(3);
		return (generatedstring + generaterandomnumber);
		
	}
	
	public String captureScreen (String tname) throws IOException  {
		
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File sourceFile =takeScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + tname + "__" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}

}
