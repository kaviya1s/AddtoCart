package TestCase;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import WebElement.LoginPage;

public class head_page {
WebDriver driver;
	
	@BeforeClass
	@Parameters({"browser"})
	public void setup(String br) {
		
		
		switch(br.toLowerCase()) {
		case "chrome" :driver=new ChromeDriver();break;
		case "edge" :driver=new EdgeDriver();break;
		default: System.out.println("invalid browser");return;
		}
		
		//driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		
		LoginPage LP=new LoginPage(driver);
		LP.getusername("standard_user");
		LP.getpassword("secret_sauce");
		LP.getclick(); 

	}	
	
	@AfterClass
	public void close() {
		driver.quit();
		
	}
	
public String captureScreen(String tname) {
		
		String timestamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(0));

		
		TakesScreenshot takesScreenshot=(TakesScreenshot)driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilepath=System.getProperty("user.dir")+"\\screenshortAD\\" + tname + "_" +timestamp +".png";
		File targetFile= new File(targetFilepath);
		
		sourceFile.renameTo(targetFile);
		return targetFilepath;
				
	}

}
