package cucumberskeleton.pages;

import cucumberskeleton.config.DriverFactory;
import cucumberskeleton.utils.GetHostUrl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private DriverFactory driverFactory;

    public HomePage() {
        super();
    }

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchbox;

    @Override
    protected ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOf(searchbox);
    }
    public HomePage open(String url) {
        this.driverFactory = new DriverFactory();
        this.driver = this.driverFactory.getDriver();
        this.driver.navigate().to(url);
        return (HomePage) openPage(HomePage.class);
    }

    public HomePage open() {
        this.driverFactory = new DriverFactory();
        this.driver = this.driverFactory.getDriver();
        this.driver.navigate().to(new GetHostUrl().getUrl());
        return (HomePage) openPage(HomePage.class);
    }
}
