import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LaunchBrowser {
    RemoteWebDriver driver;
    Dotenv dotenv = Dotenv.load();

    String userName = dotenv.get("LT_USERNAME");
    String accessKey = dotenv.get("LT_ACCESS_KEY");

    @BeforeTest
    public void setUp(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "127");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", userName);
        ltOptions.put("accessKey", accessKey);
        ltOptions.put("platformName", "Windows 11");
        ltOptions.put("timezone", "Kolkata");
        ltOptions.put("build", "Testing Form Page");
        ltOptions.put("project", "Form");
        ltOptions.put("name", "Input Validate");
        ltOptions.put("selenium_version", "3.141.59");
        ltOptions.put("driver_version", "127.0");
        capabilities.setCapability("LT:Options", ltOptions);

        try{
            driver = new RemoteWebDriver(new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub/"), capabilities);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

    }

    @Test
    public void launchBrowser(){
//        System.setProperty("webdriver.chrome.driver", "./drivers/chrome/chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
        driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");
        String title = driver.getTitle();
        System.out.println(title);
        WebElement aInput = driver.findElement(By.id("sum1"));
        aInput.sendKeys("10");
        driver.findElement(By.id("sum2")).sendKeys("20");
        driver.findElement(By.xpath("//button[text()='Get Sum']")).click();
        String addedMessage = driver.findElement(By.id("addmessage")).getText();

        System.out.println("Final response: " + addedMessage);
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
