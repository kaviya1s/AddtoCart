package UtilitiesAD;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestCase.head_page;


public class ExtentReportAD implements ITestListener{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repname;
	 
	public void onStart(ITestContext testContext)  {
		
		String timestamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repname="Test-Report-" + timestamp + ".html";
		sparkReporter=new ExtentSparkReporter(".\\Reports\\" + repname);
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Testing");
		sparkReporter.config().setReportName("Opencart Functional Test");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module","Admin" );
		extent.setSystemInfo("Sub Module","Customers");
		extent.setSystemInfo("Username",System.getProperty("user.name"));
		extent.setSystemInfo("Environment","QA");
		
		String os=testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups= testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups",includedGroups.toString());
		}
		
		
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS,result.getName()+"got successfully Executed");
		
	}
	
	public void onTestFailure(ITestResult result) {
		
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName()+"got failed");
		test.log(Status.FAIL,result.getThrowable().getMessage());
		
		try {
			//String imgpath= new head_page().captureScreen(result.getName());
			String imgPath = ((head_page) result.getInstance()).captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}
		
		
   }
	
	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+"got Skipped");
		test.log(Status.SKIP,result.getThrowable().getMessage());
		
	}
	
	public void onFinish(ITestContext testContext) {
		
		extent.flush();
		
		String PathofExtentReport=System.getProperty("user.dir")+"\\Reports\\"+repname;
		File extentReport=new File(PathofExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
