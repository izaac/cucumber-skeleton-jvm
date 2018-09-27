package cucumberskeleton.pages;

import cucumberskeleton.config.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

public abstract class BasePage<T> {
    private WebDriver driver;
    private static final Logger LOGGER = Logger
            .getLogger(BasePage.class);
    private long LOAD_TIMEOUT = 30;
    private long REFRESH_RATE = 2;
    private int AJAX_ELEMENT_TIMEOUT = 10;

    public BasePage() {
        this.driver = new DriverFactory().getDriver();
    }

    public BasePage(long loadTimeout, long pollingRate) {
        this.driver = new DriverFactory().getDriver();

        this.LOAD_TIMEOUT = loadTimeout;
        this.REFRESH_RATE = pollingRate;
    }

    public T openPage(Class<T> clazz) {
        T page = null;
        try {
            AjaxElementLocatorFactory ajaxElemFactory = new AjaxElementLocatorFactory(driver, AJAX_ELEMENT_TIMEOUT);
            page = PageFactory.initElements(driver, clazz);
            PageFactory.initElements(ajaxElemFactory, page);
            ExpectedCondition pageLoadCondition = ((BasePage) page).getPageLoadCondition();
            waitForPageToLoad(pageLoadCondition);
        } catch (NoSuchElementException e) {
            String error_screenshot = System.getProperty("user.dir") + "\\target\\screenshots\\" + clazz.getSimpleName() + "_error.png";
            this.takeScreenShot(error_screenshot);
            throw new IllegalStateException(String.format("This is not the %s page", clazz.getSimpleName()));
        }
        return page;
    }

    private void waitForPageToLoad(ExpectedCondition pageLoadCondition) {
        Wait wait = new FluentWait(new DriverFactory().getDriver())
                .withTimeout(Duration.ofSeconds(LOAD_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(REFRESH_RATE));

        wait.until(pageLoadCondition);
    }

    private void takeScreenShot(String imageName){
        try {
            String start_time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
            File imageFolder = new File(System.getProperty("user.dir") + "/target/screenshots");
            if(!imageFolder.exists()){
                imageFolder.mkdir();
            }
            String imagePath = imageFolder.getAbsolutePath() + "/" + imageName;
            driver = new DriverFactory().getDriver();
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(imageName));

        } catch (IOException e) {
            LOGGER.error("Error",e);
            throw new IllegalStateException("Error taking screenshot");
        }
    }


    protected abstract ExpectedCondition getPageLoadCondition();
}