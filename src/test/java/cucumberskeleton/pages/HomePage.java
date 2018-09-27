package cucumberskeleton.pages;

import cucumberskeleton.config.DriverFactory;
import cucumberskeleton.utils.GetHostUrl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchbox;

    @Override
    protected ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOf(searchbox);
    }

    public HomePage open(String url) {

        RemoteWebDriver driver = new DriverFactory().getDriver();
        driver.navigate().to(url);
        return (HomePage) openPage(HomePage.class);
    }

    public HomePage open() {

        RemoteWebDriver driver = new DriverFactory().getDriver();
        String url = new GetHostUrl().getUrl();
        driver.navigate().to(url);
        return (HomePage) openPage(HomePage.class);
    }
}
