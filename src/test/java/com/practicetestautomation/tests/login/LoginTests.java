package com.practicetestautomation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests {

    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality() {
        // Open page
        WebDriver driver = new FirefoxDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // Type username student into Username field
        WebElement usernameInput = driver.findElement(By.id("username"));
         usernameInput.sendKeys("student");

        // Type password Password123 into Password field
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("Password123");

        // Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        String expectedUrl = "https://practicetestautomation.com/logged-in-successfully/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);

        // Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        String expectedMessage = "Congratulations student. You successfully logged in!";
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedMessage));

        // Verify button Log out is displayed on the new page
        WebElement logOutButton  = driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logOutButton.isDisplayed());

        driver.quit();
    }

    @Test(groups = {"negative", "regression"})
    public void incorrectUsernameTest() {
        // Open page
        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // Type username incorrectUser into Username field
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("incorrectUser");

        // Type password Password123 into Password field
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("Password123");

        // Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verify error message is displayed
        WebElement errorMessage = driver.findElement(By.id("error"));
        Assert.assertTrue(errorMessage.isDisplayed());

        // Verify error message text is Your username is invalid!
        String expectedErrorMessage = "Your username is invalid!";
        String actualErrorMessage = errorMessage.getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);

        driver.quit();
    }

    @Test(groups = {"negative", "regression"})
    public void incorrectPasswordTest() {
        // Open page
        WebDriver driver = new SafariDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // Type username student into Username field
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("student");

        // Type password incorrectPassword into Password field
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("incorrectPassword");

        // Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verify error message is displayed
        WebElement errorMessage = driver.findElement(By.id("error"));
        Assert.assertTrue(errorMessage.isDisplayed());

        // Verify error message text is Your password is invalid!
        String expectedErrorMessage = "Your password is invalid!";
        String actualErrorMessage = errorMessage.getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);

        driver.quit();
    }
}
