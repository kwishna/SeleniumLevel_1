import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

public class Sel {

	public static void main(String[] args) {

		
		 FirefoxOptions f = new FirefoxOptions(); // f.addArguments("--headless");
		  
		 WebDriver d = new FirefoxDriver(f);
		  
		 d.get("https://google.com"); System.out.println(d.getCurrentUrl());
		 //((JavascriptExecutor) d).executeScript("window.open('https://google.com');");
		 new Actions(d).keyDown(Keys.CONTROL).keyDown(Keys.chord("t")).build().perform();
		 
		 
		 
//		char[] a = "fjgbsfgnkdflng".toCharArray();
//		List l = Arrays.asList(a).stream().filter(m -> m.length != 1).collect(Collectors.toList());
//		System.out.println(l.size());
//		Arrays.asList(a).forEach(k -> System.out.println(k));
//		d.close();
	}
}
