package extractKeywords;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import dbConnect.ConnRawBlogs;
import dbConnect.ConnRawKeywords;

public class ExtractKeywords {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private ConnRawBlogs cr = new ConnRawBlogs();
	private ConnRawKeywords ck = new ConnRawKeywords();
	
	public void setUp() throws Exception {
//		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");		// Only for Windows
		driver = new ChromeDriver();
		baseUrl = "http://nlp.kookmin.ac.kr/cgi-bin/indexT.cgi";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void nlpExtractKeywords() throws Exception {
		HashMap<Integer, String> contentsList = cr.selectContents();
		driver.get(baseUrl);
		for (int key : contentsList.keySet()) {
			String contents = contentsList.get(key);
			driver.findElement(By.name("Question")).clear();
			((JavascriptExecutor)driver).executeScript("document.search.Question.value=arguments[0];", contents.replaceAll("[^가-힣]", " "));
			driver.findElement(By.name("sendbutton")).click();
			for (String Handle : driver.getWindowHandles())
				driver.switchTo().window(Handle);
			WebElement result = driver.findElement(By.tagName("pre"));
			String [] tempList = result.getText().replaceAll("[^가-힣]", " ").split(" ");
			String [] keywords = new String [10];
			int index = 0;
			for (String temp : tempList) {
				if (index == 10)
					break;
				if (temp.trim().length() > 0) {
					keywords[index] = temp.trim();
					index++;
				}
			}
			ck.insert(key, keywords);
			System.out.println("+++");
		}
	}

	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			System.out.println(verificationErrorString);
		}
	}
}
