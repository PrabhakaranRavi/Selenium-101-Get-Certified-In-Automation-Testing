import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class HandleAuthentication {

    RemoteWebDriver driver;
    Dotenv dotenv = Dotenv.load();

    String userName = dotenv.get("LT_USERNAME");
    String accessKey = dotenv.get("LT_ACCESS_KEY");

    @Parameters({"browsername", "testName"})
    @BeforeMethod
    public void setUp(String browser, String testName){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("browserVersion", "127");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", userName);
        ltOptions.put("accessKey", accessKey);
        ltOptions.put("platformName", "Windows 11");
        ltOptions.put("build", "Handle Auth");
        ltOptions.put("name", testName);
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
    public void handleAuthenticationValidate(){
        String username = "admin";
        String password = "admin";

        driver.get("https://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth");

        String text = driver.findElement(By.cssSelector("div#content>div>p")).getText();
        System.out.println(text);
        String expected = "Congratulations! You must have the proper credentials.";

        Assert.assertEquals(text.trim(), expected);

//        try{
//            Thread.sleep(5000);
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }

    }
}
