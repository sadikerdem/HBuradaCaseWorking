package com.hburada.base;

import com.hburada.dataReader.ExcelReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel = new ExcelReader(
            System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
    public static WebDriverWait wait;
    public static String browser;
    public static SessionId driverSession;

    @BeforeTest
    public void setUp() {

        if (driverSession == null) {

            try {

                fis = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                config.load(fis);
                log.debug("Config file loaded !!!");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

                browser = System.getenv("browser");
            } else {

                browser = config.getProperty("browser");

            }

            config.setProperty("browser", browser);

            if (config.getProperty("browser").equals("firefox")) {

                // System.setProperty("webdriver.gecko.driver", "gecko.exe");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();

            } else if (config.getProperty("browser").equals("chrome")) {

                System.setProperty("webdriver.chrome.driver",
                        System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");

                //               WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                log.debug("Chrome Launched !!!");
            } else if (config.getProperty("browser").equals("ie")) {

                System.setProperty("webdriver.ie.driver",
                        System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();

            }

            driver.get(config.getProperty("testsiteurl"));
            log.debug("Navigated to : " + config.getProperty("testsiteurl"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
                    TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 5);
        }

    }

    public void click(String locator) {

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_XPATH") || locator.endsWith("_Xpath")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).click();
        } else {
            driver.findElement(By.xpath(locator)).click();
        }
    }

    public void scrollAndClick(String locator) {
        //objeye kadar scroll yapar
        try {
            WebElement returnShoppingLink = driver.findElement(By.xpath(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", returnShoppingLink);
        } catch (Exception e) {
        }

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_XPATH") || locator.endsWith("_Xpath")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).click();
        } else {
            driver.findElement(By.xpath(locator)).click();
        }
    }

    public void type(String locator, String value) {

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_XPATH") || locator.endsWith("_Xpath")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        } else {
            driver.findElement(By.xpath(locator)).sendKeys(value);
        }
    }

    private String textContent;

    public String getText(String locator) {

        if (locator.endsWith("_CSS")) {
            textContent = driver.findElement(By.cssSelector(OR.getProperty(locator))).getText();
        } else if (locator.endsWith("_XPATH") || locator.endsWith("_Xpath")) {
            textContent = driver.findElement(By.xpath(OR.getProperty(locator))).getText();
        } else if (locator.endsWith("_ID")) {
            textContent = driver.findElement(By.id(OR.getProperty(locator))).getText();
        } else {
            textContent = driver.findElement(By.xpath(locator)).getText();
        }
        return textContent;
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void moveToPointOnTree(String menuOption, String subMenuOption, String selectMenuOption) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath(menuOption))).perform();
        Thread.sleep(1000);
        actions.moveToElement(driver.findElement(By.xpath(subMenuOption))).perform();
        Thread.sleep(1000);
        driver.findElement(By.xpath(selectMenuOption)).click();
        Thread.sleep(1000);
    }

    public void moveToPoint(String menuOption) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath(menuOption))).perform();
        Thread.sleep(1000);
    }

    @AfterTest
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driverSession = ((ChromeDriver) driver).getSessionId();
        }
        log.debug("test execution completed !!!");
    }
}

