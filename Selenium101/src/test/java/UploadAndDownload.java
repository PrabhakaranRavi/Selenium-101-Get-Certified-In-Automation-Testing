import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static utils.SoftAssertionUtil.assertTrue;

public class UploadAndDownload {

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
        ltOptions.put("build", "UploadAndDownload");
        ltOptions.put("name", "Name");
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
    public void uploadAndDownloadValidate(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        WebElement ele = driver.findElement(By.xpath("//input[@type='file']"));

        File file = new File("./resume.png");
        System.out.println(file.getAbsolutePath());
        ele.sendKeys(file.getAbsolutePath());

        driver.findElement(By.xpath("//span[.='Start upload']")).click();
        boolean displayed = driver.findElement(By.cssSelector("button[data-type='DELETE']")).isDisplayed();
        assertTrue(displayed, "Passed");

//        ChromeOptions options = new ChromeOptions();
//        HashMap<String, Object> prefs = new HashMap<String, Object>();
//        prefs.put("download.prompt_for_download", false);
//        options.setExperimentalOption("prefs", prefs);
//
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.get("Download website");
//        driver.findElement(By.linkText("")).click();
//        Thread.sleep(10000);
    }
}
