# Skeleton for Webdriver 3 Cucumber 3 TestNG and Parallel Plugin

## Based on work

- [temyers/cucumber-jvm-parallel-plugin](https://github.com/temyers/cucumber-jvm-parallel-plugin)
- [PacktPublishing/Mastering-Selenium-WebDriver-3.0-Second-Edition](https://github.com/PacktPublishing/Mastering-Selenium-WebDriver-3.0-Second-Edition)
- Cucumber [10 minute tutorial](https://medium.com/@mlvandijk/getting-started-with-cucumber-in-java-a-10-minute-tutorial-586652d2c82)
- [Cucumber Selenium Tutorial](http://www.automationtestinghub.com/cucumber-selenium-testing-tutorial/)

## How to run it

### Pre-requirements

I did all the installation on Mac OS X using [Homebrew](https://github.com/Homebrew/brew) but use your preferred way.

* JDK 8
* Maven 3.5.4

### Execution
```bash
 $ git clone https://github.com/izaac/cucumber-skeleton-jvm
 $ cd cucumber-skeleton-jvm
 $ mvn clean verify
```

That will take care of the dependencies installation and the webdriver drivers installation automatically.
And it should also execute the current dummy tests.
