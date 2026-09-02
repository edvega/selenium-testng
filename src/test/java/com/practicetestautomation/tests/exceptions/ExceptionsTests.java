package com.practicetestautomation.tests.exceptions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionsTests {

    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        logger = Logger.getLogger(ExceptionsTests.class.getName());
        logger.setLevel(Level.INFO);

        logger.info("Running test in: " + browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                logger.warning("Configuration for " + browser + " is missing, so running tests in Chrome by default");
                driver = new ChromeDriver();
                break;
        }

        // Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
        logger.info("Browser is closed");
    }

    @Test
    public void noSuchElementExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        logger.info("Click add button");
        addButton.click();

        WebElement row2InputField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        // Verify Row 2 input field is displayed
        Assert.assertTrue(row2InputField.isDisplayed(), "Row 2 input field is not displayed");
    }

    @Test
    public void timeoutExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        logger.info("Click add button");
        addButton.click();

        WebElement row2InputField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        // Verify Row 2 input field is displayed
        Assert.assertTrue(row2InputField.isDisplayed(), "Row 2 input field is not displayed");
    }

    @Test
    public void elementNotInteractableExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        logger.info("Click add button");
        addButton.click();

        // Wait for the second row to load
        WebElement row2InputField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        // Type text into the second input field
        row2InputField.sendKeys("sushi");

        // Push Save button using locator By.name(“Save”)
        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
        saveButton.click();

        // Verify text saved
        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = successMessage.getText();
        String expectedMessage = "Row 2 was saved";
        Assert.assertEquals(actualMessage, expectedMessage, "Message is not expected");
    }

    @Test
    public void invalidElementStateExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement editButton = driver.findElement(By.id("edit_btn"));
        editButton.click();

        // Clear input field
        WebElement row1InputField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']/input")));
        row1InputField.clear();

        // Type text into the input field
        row1InputField.sendKeys("sushi");

        WebElement saveButton = driver.findElement(By.id("save_btn"));
        saveButton.click();

        // Verify text saved
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = successMessage.getText();
        String expectedMessage = "Row 1 was saved";
        Assert.assertEquals(actualMessage, expectedMessage, "Message is not expected");
    }

    @Test
    public void staleElementReferenceExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Find the instructions text element
        WebElement instructionsText = driver.findElement(By.id("instructions"));

        // Push add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        // Verify instruction text element is no longer displayed
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))),
                "Instructions text is still displayed");
    }
}
