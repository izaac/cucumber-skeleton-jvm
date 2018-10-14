package cucumberskeleton.pages;

import cucumberskeleton.config.DriverFactory;
import cucumberskeleton.utils.GetHostUrl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResults extends BasePage {

    private DriverFactory driverFactory;

    public SearchResults() {
        super();
    }

    @FindBy(id = "s-result-info-bar")
    private WebElement searchresultsinfobar;

    @Override
    protected ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOf(searchresultsinfobar);
    }

    public SearchResults open() {
        this.driverFactory = new DriverFactory();
        this.driver = this.driverFactory.getDriver();
        this.driver.navigate().to(new GetHostUrl().getUrl());
        return (SearchResults) openPage(SearchResults.class);
    }
}
