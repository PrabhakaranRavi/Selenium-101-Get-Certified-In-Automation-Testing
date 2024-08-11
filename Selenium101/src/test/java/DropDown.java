import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class DropDown {

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
        ltOptions.put("build", "Testing Dropdown Page");
        ltOptions.put("project", "DropDown");
        ltOptions.put("name", "DropDown Validate");
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
    public void dropDownValidate(){
        driver.get("https://www.lambdatest.com/selenium-playground/select-dropdown-demo");
        String title = driver.getTitle();
        System.out.println(title);
        WebElement selectDemo = driver.findElement(By.id("select-demo"));
        Select sel = new Select(selectDemo);
        sel.selectByVisibleText("Tuesday");
        WebElement firstSelectedOption = sel.getFirstSelectedOption();
        System.out.println("First Selected: " + firstSelectedOption.getText());

        sel.selectByIndex(2);
        sel.selectByValue("Saturday");

        //Multiple dropdown
        WebElement multiDropdown = driver.findElement(By.id("multi-select"));
        Select countries = new Select(multiDropdown);
        countries.selectByIndex(2);
        countries.selectByValue("New York");

        List<WebElement> allSelectedOptions = countries.getAllSelectedOptions();
        for(WebElement country: allSelectedOptions){
            System.out.println(country.getText());
        }
    }

    @Test
    public void handleAlert(){
        driver.get("https://www.lambdatest.com/selenium-playground/javascript-alert-box-demo");
        driver.findElement(By.xpath("//p[text()='JavaScript Alerts']//following-sibling::button")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        driver.findElement(By.xpath("(//button[text()='Click Me'])[2]")).click();
        alert.accept();
        driver.findElement(By.xpath("(//button[text()='Click Me'])[3]")).click();
        alert.sendKeys("Prabhakaran Ravi");
        System.out.println(alert.getText());
        alert.accept();
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
