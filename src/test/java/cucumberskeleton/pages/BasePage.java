package cucumberskeleton.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.time.Duration;

public abstract class BasePage<T> {
    WebDriver driver;
    private static final Logger LOGGER = Logger
            .getLogger(BasePage.class);
    private long LOAD_TIMEOUT = 30;
    private long REFRESH_RATE = 2;
    private int AJAX_ELEMENT_TIMEOUT = 10;

    BasePage() { }

    public BasePage(long loadTimeout, long pollingRate) {
        this.LOAD_TIMEOUT = loadTimeout;
        this.REFRESH_RATE = pollingRate;
    }

    public BasePage(long loadTimeout, long pollingRate, int ajaxElemTimeout) {
        this.LOAD_TIMEOUT = loadTimeout;
        this.REFRESH_RATE = pollingRate;
        this.AJAX_ELEMENT_TIMEOUT = ajaxElemTimeout;
    }

    T openPage(Class<T> clazz) {
        T page;
        String fileSeparator = FileSystems.getDefault().getSeparator();
        try {
            AjaxElementLocatorFactory ajaxElemFactory = new AjaxElementLocatorFactory(this.driver, AJAX_ELEMENT_TIMEOUT);
            page = PageFactory.initElements(this.driver, clazz);
            PageFactory.initElements(ajaxElemFactory, page);
            ExpectedCondition pageLoadCondition = ((BasePage) page).getPageLoadCondition();
            waitForPageToLoad(pageLoadCondition);
        } catch (NoSuchElementException e) {
            String error_screenshot = System.getProperty("user.dir") + fileSeparator + "target" + fileSeparator + "screenshots" + fileSeparator + clazz.getSimpleName() + "_error.png";
            this.takeScreenShot(error_screenshot);
            throw new IllegalStateException(String.format("This is not the %s page", clazz.getSimpleName()));
        }
        return page;
    }

    public void quitDriver(){
        if (null != this.driver) {
            this.driver.quit();
            this.driver = null;
        }
    }

    private void waitForPageToLoad(ExpectedCondition pageLoadCondition) {
        Wait wait = new FluentWait(this.driver)
                .withTimeout(Duration.ofSeconds(LOAD_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(REFRESH_RATE));

        wait.until(pageLoadCondition);
    }

    private void takeScreenShot(String imageName){
        try {
            String start_time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
            File imageFolder = new File(System.getProperty("user.dir") + "/target/screenshots");
            boolean created = false;
            if(!imageFolder.exists()){
                created = imageFolder.mkdir();
            }
            if (created) {
                File scrFile = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(imageName + '-' + start_time));
            } else {
                LOGGER.error("Error, screenshots folder was not created");
            }

        } catch (IOException e) {
            LOGGER.error("Error",e);
            throw new IllegalStateException("Error taking screenshot");
        }
    }

    protected abstract ExpectedCondition getPageLoadCondition();
}
