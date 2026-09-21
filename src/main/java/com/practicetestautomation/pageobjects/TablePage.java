package com.practicetestautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class TablePage extends BasePage {

    private final By resetButtonLocator = By.id("resetFilters");
    private final By noDataLocator = By.id("noData");
    private final By enrollDropdownLocator = By.id("enrollDropdown");
    private final By sortByLocator = By.id("sortBy");
    private final By rowsLocator = By.cssSelector("#courses_table tbody tr");

    public TablePage(WebDriver driver) {
        super(driver);
    }

    public void visit() {
        super.visit("https://practicetestautomation.com/practice-test-table/");
    }

    public void selectLanguage(String language) {
        By languageRadioLocator = By.cssSelector("input[name='lang'][value='" + language + "']");
        WebElement languageRadio = wait.until(ExpectedConditions.elementToBeClickable(languageRadioLocator));
        languageRadio.click();
    }

    public void setLevelFilter(String level, boolean selected) {
        By levelCheckboxLocator = By.cssSelector("input[name='level'][value='" + level + "']");
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(levelCheckboxLocator));
        if (checkbox.isSelected() != selected) {
            checkbox.click();
        }
    }

    public void setMinEnrollments(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(enrollDropdownLocator));
        dropdown.click();

        By optionLocator = By.cssSelector("li[data-value='" + value + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public void selectSortBy(String value) {
        Select sortSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortByLocator)));
        sortSelect.selectByValue(value);
    }

    public boolean isResetButtonVisible() {
        return driver.findElement(resetButtonLocator).isDisplayed();
    }

    public void resetFilters() {
        driver.findElement(resetButtonLocator).click();
    }

    public boolean isNoDataVisible() {
        return driver.findElement(noDataLocator).isDisplayed();
    }

    public String getNoDataText() {
        return driver.findElement(noDataLocator).getText();
    }

    public boolean isAnyLanguageSelected() {
        return driver.findElement(By.cssSelector("input[name='lang'][value='Any']")).isSelected();
    }

    public boolean areAllLevelsSelected() {
        return driver.findElement(By.cssSelector("input[name='level'][value='Beginner']")).isSelected()
            && driver.findElement(By.cssSelector("input[name='level'][value='Intermediate']")).isSelected()
            && driver.findElement(By.cssSelector("input[name='level'][value='Advanced']")).isSelected();
    }

    public String getSelectedMinEnrollments() {
        return driver.findElement(enrollDropdownLocator).getAttribute("data-value");
    }

    public List<String> getVisibleRowTexts() {
        List<String> rows = new ArrayList<>();
        for (WebElement row : driver.findElements(rowsLocator)) {
            String rowText = row.getText().trim();
            if (!rowText.isEmpty()) {
                rows.add(rowText);
            }
        }
        return rows;
    }

    public List<String> getVisibleCourseNames() {
        List<String> courseNames = new ArrayList<>();
        for (WebElement row : driver.findElements(rowsLocator)) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String courseText = cells.get(1).getText();
            if (!courseText.isEmpty()) {
                courseNames.add(courseText);
            }
        }
        return courseNames;
    }

    public List<Integer> getVisibleEnrollmentValues() {
        List<Integer> enrollments = new ArrayList<>();
        for (WebElement row : driver.findElements(rowsLocator)) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String enrollmentText = cells.get(4).getText();
            if (!enrollmentText.isEmpty()) {
                enrollments.add(Integer.parseInt(enrollmentText));
            }
        }
        return enrollments;
    }

    public int getVisibleRowCount() {
        return getVisibleRowTexts().size();
    }
}
