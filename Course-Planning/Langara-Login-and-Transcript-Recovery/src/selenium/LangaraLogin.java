package selenium;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
    	String actualUrl, expectedUrl;
	
	@Parameters("browser")
	@BeforeClass
	public void BeforeClass(String browser) 
	{
		if(browser.equalsIgnoreCase("google chrome")) {
			System.setProperty("webdriver.chrome.driver", ".\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();  
		}else if (browser.equalsIgnoreCase("ie")) { 
			System.setProperty("webdriver.ie.driver", ".\\BrowserDrivers\\IEDriverServer_Win32_3.150.1\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();;
		} else {
	        System.out.println("The Browser Type is Undefined");
	    }
		driver.manage().window().maximize();
		actualUrl = "https://swing.langara.bc.ca/prod/twbkwbis.P_WWWLogin"; 
	    	driver.get(actualUrl);
		expectedUrl = driver.getCurrentUrl();
		Assert.assertTrue(expectedUrl.equals(actualUrl));
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
	}	
	@Test(priority = 1)
	public void Login()
	{
	    	String userName, passWord;
	    	WebElement username, password, submitBtn;
	    	wait = new WebDriverWait(driver,2);
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
		submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));;
		submitBtn.click();
		//Added a bit of checking in case you entered the wrong values
		actualUrl = "https://swing.langara.bc.ca/prod/twbkwbis.P_GenMenu?"; 
		expectedUrl = driver.getCurrentUrl();
		Assert.assertTrue(expectedUrl.contains(actualUrl));
	}
	
	@Test(priority = 2)
	public void PullTranscripts()
	{
		wait = new WebDriverWait(driver,2);
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
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dddefault")));
	    	List<WebElement> courses = driver.findElements(By.className("dddefault"));
	    	Iterator<WebElement> iter = courses.iterator();
		// This will check whether list has some element or not
		while (iter.hasNext()) {
			WebElement item = iter.next();
			String label = item.getText();
			System.out.println(label);
		}
	}
	
	@AfterClass
	public void afterClass()
	{		
		driver.quit();	
	}
}
