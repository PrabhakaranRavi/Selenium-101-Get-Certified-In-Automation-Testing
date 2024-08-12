import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ScreenShots {
    RemoteWebDriver driver;
    Dotenv dotenv = Dotenv.load();

    String userName = dotenv.get("LT_USERNAME");
    String accessKey = dotenv.get("LT_ACCESS_KEY");

//    @Parameters({"browsername", "testName"})
    @BeforeMethod
    public void setUp(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "127");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", userName);
        ltOptions.put("accessKey", accessKey);
        ltOptions.put("platformName", "Windows 11");
        ltOptions.put("build", "ScreenShots");
        ltOptions.put("name", "Name");
        capabilities.setCapability("LT:Options", ltOptions);

        try{
            driver = new RemoteWebDriver(new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub/"), capabilities);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void screenShotsValidate(){
        driver.get("https://www.lambdatest.com/selenium-playground/");
        File screenShotAs = driver.getScreenshotAs(OutputType.FILE);

        try{
            FileHandler.copy(screenShotAs, new File("./screenshots/ig1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

        WebElement ele = driver.findElement(By.xpath("//button[text()='Book a Demo']"));
        File src = ele.getScreenshotAs(OutputType.FILE);

        try{
            FileHandler.copy(src, new File("./screenshots/ele.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
