package selenium;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LangaraProgramRequirements {
	WebDriver driver = null;
	WebDriverWait wait = null;
    String actualUrl, expectedUrl;
	String programName = "Computer Science";
	String degreeName = "Associate of Science Degree in Computer Science";
	@BeforeClass
	public void BeforeClass() 
	{
		System.setProperty("webdriver.chrome.driver", ".\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();  
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		actualUrl = "https://langara.ca/programs-and-courses/index.html"; 
	    driver.get(actualUrl);
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		expectedUrl = driver.getCurrentUrl();
		Assert.assertTrue(expectedUrl.equals(actualUrl));
		//System.out.println(expectedUrl.equals(actualUrl));
	}
	@Test(priority = 1)
	public void NavigateToRequiredCourses() {
		wait = new WebDriverWait(driver,5);
		WebElement programname = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(programName)));
		programname.click();
		// <h1>Computer Science</h1>
		// /html/body/div[3]/div[3]/div[3]/div/div/div[1]/h1
		WebElement h1 =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[3]/div[3]/div/div/div[1]/h1")));
		Assert.assertTrue(h1.getText().equals(programName));
		// System.out.println("Am I at the right program requirements: "+h1.getText().equals(programName));
		// <a class="btn-box-light" href="program-curriculum.html">Program Curriculum</a>
		WebElement programcurriculum = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Program Curriculum")));
		programcurriculum.click();
		// <a class="accordion-title" href="#">Associate of Science Degree in Computer Science</a>
		WebElement degreename = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(degreeName)));
		degreename.click();
	}
	@Test(dependsOnMethods={"NavigateToRequiredCourses"})
	public void PullRequireCourses()
	{
		/*
	    <div class="course-selection-title">All of</div>
	    <div class="course-group">
	    <table class="course-details">
	    <td class="course-number">
	    <a class="course-toggler"> CPSC 1050
	    <td class="course-name">
	    <a class="course-toggler"> Introduction to Computer Science
	    <td class="course-credit">
	    <a class="course-toggler"> 3
	    */
	    /*So I want to create a list of certain courses.
	      If it's All : Just insert into a list incrementing each add
	      If it's Two of: 
	     */
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
	    List<WebElement> listofprogramrequirements = driver.findElements(By.className("course-selection-title"));
	    Assert.assertTrue(listofprogramrequirements.size()>0);
	    for (WebElement col: listofprogramrequirements)
	    {
	        System.out.println(col.getText());
	        List<WebElement> coursenumbers = col.findElements(By.xpath(".//..//td[@class='course-number']"));
	    	if(coursenumbers.size()>0)
			{
				PrintWebElements(coursenumbers);
			}
			else
			{
				//<div class="wysiwyg-content default_program-curriculum">
				WebElement message = col.findElement(By.xpath(".//..//div[@class='wysiwyg-content default_program-curriculum']"));
				System.out.println(message.getText());
				//Need to parse out the bottom.
			}
	    }
	}
	@AfterClass
	public void AfterClass()
	{	
		driver.quit();	
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
	/*
	public class Course{
		private String subject, title, grade;
		private int no, credit, gpa;		
		public Course(String subject, int no, String title, int credit, String grade, int gpa)
		{
			this.subject = subject;
			this.no = no;
			this.title = title;
			this.credit = credit;
			this.grade = grade;
			this.gpa = gpa;
		}
		public String ToString()
		{
			return this.subject+this.no+this.title+this.credit+this.grade+this.gpa;
		}
	}
	public class Courses{
		private int howManyNeedtoPass = 0;
	    private ArrayList<Course> courses = new ArrayList<Course>();
	    public Courses(Course c)
	    {
	    	courses.add(c);
	    }
	    public void IncrementPass(int size)
	    {
	    	howManyNeedtoPass+=size;
	    }
	    public Object ReturnCourses()
	    {
	    	return courses;
	    }
	    public int ReturnPassNumber()
	    {
	    	return howManyNeedtoPass;
	    }	
	}
	*/
}
