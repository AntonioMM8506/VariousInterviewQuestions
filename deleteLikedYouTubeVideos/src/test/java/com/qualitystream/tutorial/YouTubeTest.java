package com.qualitystream.tutorial;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class YouTubeTest {
		
	private WebDriver driver;
	private WebDriverWait wait;
	
	//Auxiliar methods-------------------------------------------------------------
	//Method used in order to wait a certain quantity of seconds
	public void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}//End of sleep

	
	//Method user to read from a csv file, in this case is used to read the data for the credentials
	//in order to log into YouTube
	public static List<String[]> readCSV() {
		List<String[]> resultList = new ArrayList<>();

	    try (BufferedReader reader = new BufferedReader(new FileReader("./src/test/resources/files/credentials.csv"))) {
	    	String line;
	    	while((line = reader.readLine()) != null) {
	    		String[] data = line.split(",");
	    		resultList.add(data);
	    	}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return resultList;
	}//End of readCSV
	
	
	
	//Automation -----------------------------------------------------------------
	@Before
	public void setUp() {
		//Set up the driver properties 
		//Download the chromedriver and import it to the mentioned path
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		//navigate to YouTube and then wait up to 20 seconds
		driver.get("https://www.youtube.com/");
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		
	}//End of setUp
	
	
	
	@Test
	public void testDeleteLikedVideos() {
		//set an implicit wait of 5 seconds, if the element is not found, then the test fails
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		
		//Wait for the logo of YouTube to be displayed
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logo")));
		driver.findElement(By.id("buttons")).findElement(By.tagName("yt-button-shape")).findElement(By.tagName("a")).click();
		
		
		//Google LogIn
		//first screen. Read from the CSV and fulfill the first form
		List<String[]> csvData = readCSV();	
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("headingText")));
		driver.findElement(By.id("identifierId")).sendKeys(csvData.get(0)[0]);
		driver.findElement(By.id("identifierNext")).findElement(By.tagName("button")).click();
		//Second screen, request only the password
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("forgotPassword")));
		driver.findElement(By.id("password")).findElement(By.tagName("input")).sendKeys(csvData.get(0)[1]);
		driver.findElement(By.id("passwordNext")).findElement(By.tagName("button")).click();
		sleep(3);
		
		
		//YouTube => Navigate to Liked Videos
		//in the left menu, look for the Liked Videos options an then click it
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sections")));
		WebElement historyMenu = driver.findElement(By.id("sections")).findElement(By.id("items")).findElement(By.tagName("ytd-guide-collapsible-section-entry-renderer")).findElement(By.id("section-items"));
		List<WebElement> historyMenuOptions = historyMenu.findElements(By.tagName("ytd-guide-entry-renderer"));
		historyMenuOptions.get(3).findElement(By.tagName("a")).click();
		sleep(3);
		
		
		//YouTube - LIked Videos - Deleting liked videos
		//If no videos are found, just print in the console that there are no liked videos.
		//Otherwise, get all the references of the Liked videos into a List. Then in a for cycle
		//iterate over the list, click into the 3 dots icon, menu, and then choose the option for
		//deleting the video from the list. After all the videos are removed, then refresh the page
		if(driver.findElements(By.id("message")).size()!=0) {
			System.out.println("No liked videos found");
		}else {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("spinner-container")));
			List<WebElement> likedVideos = driver.findElements(By.tagName("ytd-playlist-video-renderer"));
			for(int i=0; i<likedVideos.size(); i++) {
				likedVideos.get(i).findElement(By.id("menu")).click();
				WebElement modalMenu = driver.findElement(By.tagName("ytd-popup-container")).findElement(By.tagName("ytd-menu-popup-renderer")).findElement(By.tagName("tp-yt-paper-listbox"));
				List<WebElement> popMenuOptions = modalMenu.findElements(By.tagName("ytd-menu-service-item-renderer"));
				popMenuOptions.get(popMenuOptions.size()-1).click();
				sleep(1);
			}	
			driver.navigate().refresh();
		}
		sleep(2);
		
		
		//YouTube - LogOut
		//Clicking in the avatar icon, collapses the user menu, look for the LogOut option and
		//click it in order to end the session.
		driver.findElement(By.id("avatar-btn")).click();
		List<WebElement> subMenus = driver.findElements(By.tagName("yt-multi-page-menu-section-renderer"));
		List<WebElement> firstSubMenu = subMenus.get(0).findElements(By.tagName("ytd-compact-link-renderer"));
		firstSubMenu.get(firstSubMenu.size()-1).click();
		sleep(3);
		
	}//End of testDeleteLikedVideos
	

	
	@After
	public void closeWindow() {
		//When finishing all the test steps, just close the driver
		driver.quit();
	}
	
}//End of class

