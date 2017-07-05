package scrape2;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scrape2 {

    public static void main(String[] args) throws InterruptedException {
        File file = new File("C:/Products/phantomjs-2.1.1-windows/bin/phantomjs.exe");
        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
        //WebDriver wd = new PhantomJSDriver();
        
//        wd.navigate().to("http://www.searchcraigslist.org/");
//        WebElement element = wd.findElement(By.name("search"));
//        element.sendKeys("bmw m3");
//        
//        element.submit();
//        WebElement searchButton = wd.findElement(By.className("gsc-search-button"));
//        searchButton.submit();
//        
//        System.out.println("Page title is: " + wd.getTitle());
//        List<WebElement> items = wd.findElements(By.cssSelector(".gsc-webResult.gsc-result"));
//        
//        System.out.println(" =============== CraigsList M3 Results ================= ");
//        for (int j = 0; j < items.size(); j++) {
//            System.out.println( "\t - " + items.get(j).getText() ) ;
//        }
        System.setProperty("webdriver.gecko.driver","C:/Products/gecko/geckodriver.exe");

        WebDriver driver = new FirefoxDriver();  
        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait waiter = new WebDriverWait(driver, 1);
        driver.get("http://www.searchcraigslist.org/");         
        WebElement element = driver.findElement(By.id("gsc-i-id1"));  
        element.sendKeys("BMW M3 e46");
        element.sendKeys(Keys.RETURN);
        System.out.println("Page title is: " + driver.getTitle());
        waiter.until(d -> d.getTitle().contains("Custom"));
        waitForReady(driver,waiter);

        waiter.until(d -> d.findElement(By.className("gsc-selected-option-container")));
        if (driver instanceof JavascriptExecutor) {
            Object result = ((JavascriptExecutor)driver).executeScript("return document.readyState;");
            System.out.println(result);
        }
        element = driver.findElement(By.className("gsc-selected-option-container"));  
        element.click();
        waitForReady(driver,waiter);

        
        List<WebElement> sortOrder = driver.findElements(By.className("gsc-option"));
        for(WebElement ele:sortOrder){
            if("Date".equals(ele.getAttribute("innerHTML")))
            {
                ele.click();
                waitForReady(driver,waiter);
                break;
            }
        }

        waiter.until(d -> d.findElement(By.cssSelector(".gsc-webResult.gsc-result")));
        waitForReady(driver,waiter);
        Thread.sleep(200);
        List<WebElement> items = driver.findElements(By.cssSelector(".gsc-webResult.gsc-result"));
        
        System.out.println(" =============== CraigsList M3 Results ================= ");
        for (int j = 0; j < items.size(); j++) {
            System.out.println( "\t - " + items.get(j).getText() ) ;
        }
        driver.close();
        driver.quit();      

        //wd.close();
        //wd.quit();



        

    }
    
    public static void waitForReady(WebDriver driver, WebDriverWait wait) {
        if (driver instanceof JavascriptExecutor) {
            wait.until(d -> ((JavascriptExecutor)d).executeScript("return document.readyState;").equals("complete"));
        } 
    }

}
