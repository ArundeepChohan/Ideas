package selenium;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LangaraLogin {
	WebDriver driver = null;
	WebDriverWait wait = null;
   	String expectedUrl;
    	String [] actualUrls = {"https://swing.langara.bc.ca/prod/twbkwbis.P_WWWLogin","https://swing.langara.bc.ca/prod/twbkwbis.P_GenMenu?"};
    	@Parameters("browser")
	@BeforeClass
	public void BeforeClass(String browser) 
	{
		switch(browser)
		{
			case "google chrome":
				System.setProperty("webdriver.chrome.driver", ".\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver(); 
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", ".\\BrowserDrivers\\geckodriver-v0.26.0-win32\\geckodriver.exe");
				FirefoxOptions fo = new FirefoxOptions();
				fo.setAcceptInsecureCerts(true);
				driver = new FirefoxDriver(fo);
				break;
			case "ie":
				System.setProperty("webdriver.ie.driver", ".\\BrowserDrivers\\iedriverserver_win32_3.150.1\\iedriverserver.exe");
				driver = new InternetExplorerDriver();
				break;
			default:
				System.out.println("The Browser Type is Undefined.");
				break;
		}
	    	driver.get(actualUrls[0]);
		driver.manage().window().maximize();
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		expectedUrl = driver.getCurrentUrl();
		Assert.assertTrue(expectedUrl.contains(actualUrls[0])); 
		//System.out.println("Am I at the login:"+expectedUrl.contains(actualUrls[0]));
		Screenshot("LangaraLoginHomePage.png");
	}	
	@Test(priority = 1)
	public void Login() 
	{
	    	String userName, passWord;
	    	WebElement username, password, submitBtn;
	    	wait = new WebDriverWait(driver,5);
		//<input type="text" name="sid" size="11" maxlength="9" id="UserID">
	    	username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("sid")));
		//Enter your username and password
		userName = "";
		username.clear();
		username.sendKeys(userName);
		//<input type="password" name="PIN" size="31" maxlength="30">
		password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("PIN")));
		passWord = "";
		password.clear();
		password.sendKeys(passWord);
		//<input type="submit" value="Login">	    
		submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
		submitBtn.click();
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Added a bit of checking in case you entered the wrong values
		expectedUrl = driver.getCurrentUrl();
		Assert.assertTrue(expectedUrl.contains(actualUrls[1]));
		//System.out.println("Did I login: "+(expectedUrl.contains(actualUrls[1])));
		Screenshot("LangaraSignedIn.png");
	}
	@Test(dependsOnMethods={"Login"})
	public void PullTranscripts()
	{
		//<a href="/prod/twbkwbis.P_GenMenu?name=bmenu.P_StuMainMnu" onmouseover="window.status='Students'; return true" onmouseout="window.status=''; return true" onfocus="window.status='Students'; return true" onblur="window.status=''; return true">Students</a>
		WebElement students = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Students")));
	    	students.click();
	    	WebElement studentrecords = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Student Records")));
	    	studentrecords.click();
	    	WebElement viewyourtranscript = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View Your Transcript")));
	    	viewyourtranscript.click();
	    	///html/body/p/table/tbody/tr/td[2]/div[2]/p/table[1]/tbody
	    	//WebElement transcript = driver.findElement(By.xpath("/html/body/p/table/tbody/tr/td[2]/div[2]/p/table[1]/tbody"));
	    	//System.out.println(transcript.getText());
	    	//Would like to pull out the information and store it into a class with Subject, No, Title, Credit, Grade, GPA. Remove the last column as well.
	    	//<td class="dddefault">CPSC</td>
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	Screenshot("LangaraTranscripts.png");
	    	List<WebElement> courses = driver.findElements(By.className("dddefault"));
	    	Assert.assertTrue(courses.size()>0);
	    	//System.out.println("Do I have courses: "+(courses.size()>0));
	    	PrintWebElements(courses);
	}
	@AfterClass
	public void afterClass()
	{		
		driver.close();
	}
	public void Screenshot(String dest)
	{   
	    	try {
	    		TakesScreenshot ts = (TakesScreenshot)driver;
	 		File source = ts.getScreenshotAs(OutputType.FILE); 
			FileHandler.copy(source, new File("./Screenshots/"+dest));
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Screen shot fails");
			e.printStackTrace();
		}
	}
	public void PrintWebElements(List<WebElement> listofelements)
	{
		Iterator<WebElement> iter = listofelements.iterator();
		// This will check whether list has some element or not
		while (iter.hasNext()) {
			WebElement item = iter.next();
			String label = item.getText();
			System.out.println(label);
		}
	}
}
