package service;

//import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchService {
	private WebDriver driver;
	private String baseUrl = "https://search.naver.com/search.naver?where=post&sm=tab_jum&query=";
	private StringBuffer verificationErrors = new StringBuffer();
	
//	public void setUp() throws Exception {
//		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");		// Only for Windows
//		driver = new ChromeDriver();
//		baseUrl = "https://search.naver.com/search.naver?where=post&sm=tab_jum&query=";
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//	}

	public void searchDirect(String ... keywords) throws Exception {
//		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");		// Only for Windows
		String searchWord = searchWord(keywords);
		driver = new ChromeDriver();
		baseUrl += searchWord;
		driver.get(baseUrl);
	}
	
	public void searchUrl(String url) {
//		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");		// Only for Windows
		driver = new ChromeDriver();
		driver.get(url);
	}
	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			System.out.println(verificationErrorString);
		}
	}
	
	public String searchWord(String [] keywords) {
		String searchWord = "대만+여행+";
		for (int i = 0; i < keywords.length; i++) {
			if (i == keywords.length - 1) {
				searchWord += keywords[i];
				break;
			}
			searchWord += keywords[i] + "+";
		}
		return searchWord;
	}
}
