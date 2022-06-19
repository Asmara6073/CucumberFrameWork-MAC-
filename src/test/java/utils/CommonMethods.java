package utils;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import steps.PageInitializers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

 public  class CommonMethods extends PageInitializers {

    public static WebDriver driver;

    public void openBrowserAndLaunchApplication(){
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);
        switch(ConfigReader.getPropertyValue("browser")){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver=new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver=new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("Invalid browser name");
        }
        driver.get(ConfigReader.getPropertyValue("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
        initPageObjects();
    }

    /**
     * This method sends text to any textbox
     * @param element
     * @param textToSend
     */
    public static void sendText(WebElement element, String textToSend){
        element.clear();
        element.sendKeys(textToSend);
    }

    /**
     * creates an object of WebDriverWait
     * @return WebDriverWait
     */
    public static WebDriverWait getWait(){
        WebDriverWait wait=new WebDriverWait(driver,Constants.EXPLICIT_WAIT);
        return wait;
    }

    public static void waitForClickability(WebElement element){
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void click(WebElement element){
        waitForClickability(element);
        element.click();
    }

    public static JavascriptExecutor getJSExecutor(){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        return js;
    }

    public static void jsClick(WebElement element){
        getJSExecutor().executeScript("arguments[0].click();",element);
    }


    public static void switchToFrame(WebElement element) {
            driver.switchTo().frame(element);
    }

    public static void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }

    public static void switchToFrame(String nameOrId) {
            driver.switchTo().frame(nameOrId);
    }

    public static void selectDdValue(WebElement element, String textToSelectOrValue) {
        Select select=new Select(element);
        select.selectByVisibleText(textToSelectOrValue);
    }

    public static void selectDdValue(WebElement element, int index) {
        Select select=new Select(element);
        select.selectByIndex(index);
    }


    public static byte[] takeScreenshot(String fileName){
        TakesScreenshot ts=(TakesScreenshot)driver;
        byte[] picBytes=ts.getScreenshotAs(OutputType.BYTES);
        File sourceFile=ts.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(sourceFile,new File(Constants.SCREENSHOT_FILEPATH+fileName
            +" "+getTimeStamp("MM-dd-yyyy-HH-mm-ss")+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picBytes;
    }

    public static String getTimeStamp(String pattern){
        Date date=new Date();
        //to format the date according to our choice we want to implement in this function
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


    public static void verifyRowText(List<WebElement> list, String expectedRowText){

        Iterator<WebElement> itr=list.iterator();

        while(itr.hasNext()){
            WebElement row=itr.next();
            String actualRowText=row.getText();
            Assert.assertEquals(actualRowText,expectedRowText);
        }

    }


    public static void tearDown(){
        driver.quit();
    }




}
