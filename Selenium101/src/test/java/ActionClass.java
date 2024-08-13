import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static utils.SoftAssertionUtil.assertTrue;

public class ActionClass {

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
        ltOptions.put("build", "Action");
        ltOptions.put("name", "Functions");
        capabilities.setCapability("LT:Options", ltOptions);

        try{
            driver = new RemoteWebDriver(new URL("https://" + userName + ":" + accessKey + "@hub.lambdatest.com/wd/hub/"), capabilities);
            driver.setFileDetector(new LocalFileDetector());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void actionClassValidate(){
        driver.get("https://www.lambdatest.com/");
        WebElement ele = driver.findElement(By.xpath("//button[text()='Developers ']"));

        Actions builder = new Actions(driver);
        builder.moveToElement(ele).perform();
        driver.findElement(By.xpath("(//h3[text()='API'])[2]")).click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.lambdatest.com/support/api-doc/");
    }

    @Test
    public void dragAndDrop(){
        driver.get("https://www.lambdatest.com/");
        WebElement eleSource = driver.findElement(By.id("draggable"));
        WebElement eleTarget = driver.findElement(By.id("droppable"));

        Actions builder = new Actions(driver);
        builder.dragAndDrop(eleSource, eleTarget).perform();
    }

    @Test
    public void dragAndDropByLocation(){
        driver.get("https://jqueryui.com/draggable/");
        WebElement eleSource = driver.findElement(By.id("draggable"));
        Point location = eleSource.getLocation();
        int x = location.getX();
        int y = location.getY();

        Actions builder = new Actions(driver);
        builder.dragAndDropBy(eleSource, x + 30, y+ 20).perform();
    }
}
