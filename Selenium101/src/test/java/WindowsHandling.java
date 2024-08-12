import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WindowsHandling {

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
        ltOptions.put("build", "Windows Page");
        ltOptions.put("project", "Windows");
        ltOptions.put("name", "Windows Handling Validate");
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
    public void windowsHandlingValidate(){
        driver.get("https://www.lambdatest.com/selenium-playground/window-popup-modal-demo");
        String parentWindow = driver.getWindowHandle();
        String parentTitle = driver.getTitle();
        System.out.println("Parent window ID and Title: " + parentWindow + parentTitle);
        driver.findElement(By.linkText("Follow On Twitter")).click();

        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windows = new ArrayList<String>(windowHandles);

        driver.switchTo().window(windows.get(1));
        String childWindow1 = driver.getWindowHandle();
        String childTitle1 = driver.getTitle();
        System.out.println("Parent window ID and Title: " + childWindow1 + childTitle1);

        //Close current Tab
//        driver.close();

        driver.switchTo().window(parentWindow);
        System.out.println("Back to Parent window " + driver.getTitle());

        //Close all Tabs
        driver.quit();

    }

    @Test
    public void iframeValidate(){
        driver.get("https://www.lambdatest.com/selenium-playground/iframe-demo/");
        driver.switchTo().frame("iFrame1");

        driver.findElement(By.xpath("//div[text()='Your content.']")).sendKeys("IFrame");

        driver.switchTo().parentFrame();
        driver.switchTo().frame("iFrame2");
        driver.findElement(By.linkText("API Reference")).click();
        driver.findElement(By.linkText("FAQ")).click();

        //It goes to Parent frame, Not to default content ,If you are at 2 iframes inside , it goes to first iframe
        driver.switchTo().parentFrame();

        //But going to default content, irrespective of IFrame use
        driver.switchTo().defaultContent();

        //Close all Tabs
        driver.quit();

    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
