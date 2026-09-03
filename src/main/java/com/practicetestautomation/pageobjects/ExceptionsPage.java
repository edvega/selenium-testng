package com.practicetestautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExceptionsPage extends BasePage {

    private final By addButtonLocator = By.id("add_btn");
    private final By editButtonLocator = By.id("edit_btn");
    private final By row1SaveButtonLocator = By.xpath("//div[@id='row1']/button[@name='Save']");
    private final By row2SaveButtonLocator = By.xpath("//div[@id='row2']/button[@name='Save']");
    private final By row1InputLocator = By.xpath("//div[@id='row1']/input");
    private final By row2InputLocator = By.xpath("//div[@id='row2']/input");
    private final By successMessageLocator = By.id("confirmation");
    private final By instructionsLocator = By.id("instructions");

    public ExceptionsPage(WebDriver driver) {
        super(driver);
    }

    public void visit() {
        super.visit("https://practicetestautomation.com/practice-test-exceptions/");
    }

    public void pushAddButton() {
        driver.findElement(addButtonLocator).click();
    }

    public void pushEditButton() {
        driver.findElement(editButtonLocator).click();
    }

    public boolean isRow2DisplayedAfterWait() {
        return waitForIsDisplayed(row2InputLocator);
    }

    public void enterFoodInRow1(String name) {
        WebElement row1Input = driver.findElement(row1InputLocator);
        row1Input.clear();
        row1Input.sendKeys(name);
    }

    public void enterFoodInRow2(String name) {
        driver.findElement(row2InputLocator).sendKeys(name);
    }

    public void saveRow1() {
        driver.findElement(row1SaveButtonLocator).click();
    }

    public void saveRow2() {
        driver.findElement(row2SaveButtonLocator).click();
    }

    public String getSuccessMessage() {
        return waitForElement(successMessageLocator).getText();
    }

    public boolean isInstructionsElementHiddenAfterWait() {
        return waitForIsHidden(instructionsLocator);
    }
}
