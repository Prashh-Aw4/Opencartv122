package utilities;



import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener  {

	public ExtentSparkReporter sparkReporter;// UI of the Report
	public ExtentReports extent;//Populate Common Info on the Report
	public ExtentTest test;//Creating test case entries in the report and update status of the test methods
	
	String repName;
	
	public void onStart(ITestContext testcontext) {
		
		 /*SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		 Date dt = new Date();
		 String currentdatetimestamp =df.format(dt);*/
		
		 System.out.println("Test Started....");
		
		 String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		 repName = "Test-Report" + timeStamp + ".html";
		 
		 
		 sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);//specific location where extent report will generate
	    
		 sparkReporter.config().setDocumentTitle("OpenCart Automation Report");
		 sparkReporter.config().setReportName("OpenCart Functional Testing");
		 sparkReporter.config().setTheme(Theme.DARK);
		 
		 extent = new ExtentReports();
		 
		 extent.attachReporter(sparkReporter);
		 
		 extent.setSystemInfo("Application", "OpenCart");
		 extent.setSystemInfo("Module", "Admin");
		 extent.setSystemInfo("Sub Module", "Customer");
		 extent.setSystemInfo("User Name ", System.getProperty("user.name"));
		 extent.setSystemInfo("Environment", "QA");
		 
		 String os = testcontext.getCurrentXmlTest().getParameter("os");
		 extent.setSystemInfo("Operating Sytem", os);
		 
		 String browesr = testcontext.getCurrentXmlTest().getParameter("browser");
		 extent.setSystemInfo("Browser", browesr);
		 
		  List<String>includeGroups =testcontext.getCurrentXmlTest().getIncludedGroups();
		 if (!includeGroups.isEmpty()) {
			 extent.setSystemInfo("Groups",includeGroups.toString());
		 }
		 
		 
	  }
	
	public void onTestSuccess(ITestResult result) {
		
		 System.out.println("Test Success....");
		
		test = extent.createTest(result.getTestClass().getName()); 
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS, result.getName()+ " Got Succesfully Excecuted");
		
	    
	  }
	
	public void onTestFailure (ITestResult result) {
		
		 System.out.println("Test failed....");
	   
		test = extent.createTest(result.getTestClass().getName()); 
		test.assignCategory(result.getMethod().getGroups()); 
		
		test.log(Status.FAIL, result.getName() + " Got Failed");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		catch(IOException e1) {
			e1.printStackTrace();
		}
	  }
	
	 public void onTestSkipped(ITestResult result) {
		    
		    System.out.println("Test Skipped....");
		   
			test = extent.createTest(result.getTestClass().getName()); 
			test.assignCategory(result.getMethod().getGroups());
			test.log(Status.SKIP, result.getName() + " Got Skipped");
			test.log(Status.INFO,result.getThrowable().getMessage());
		 
		  }
	 
	 public void onFinish(ITestContext context) {
		 
		 System.out.println("Test Excetuion Finish......");
		 
		 extent.flush();
		 
		 String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		 File extentReport = new File(pathOfExtentReport);
		 
		/* try {
			 Desktop.getDesktop().browse(extentReport.toURI());
		 }
		 catch (IOException e){
			 e.printStackTrace();
			 
		 }
		 
		/* try {
			 URL url = new URL ("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
			 
			 //Create email msg 
			 ImageHtmlEmail email = new ImageHtmlEmail();
			 email.setDataSourceResolver(new DataSourceUrlResolver(url));
			 email.setHostName("smtp.googleemail.com");
			 email.setSmtpPort(465);
			 email.setAuthenticator(new DefaultAuthenticator("prashant25awchar@gmail.com", "Password"));
			 email.setSSLOnConnect(true);
			 email.setFrom("prashant25awchar@gmail.com");//sender
			 email.setSubject("Test Result");
			 email.setMsg("Please Find Attched Report ....");
			 email.addTo("prashant96awchar@gmail.com");//
             email.attach(url,"extent report","please check report...");
             email.send();
			  
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 
		 }*/
		 
		    
		  }


}

