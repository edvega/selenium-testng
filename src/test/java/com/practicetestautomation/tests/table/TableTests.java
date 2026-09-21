package com.practicetestautomation.tests.table;

import com.practicetestautomation.pageobjects.TablePage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TableTests extends BaseTest {

    private TablePage tablePage;

    @BeforeMethod(alwaysRun = true)
    public void setUpTablePage() {
        tablePage = new TablePage(driver);
        tablePage.visit();
    }

    @Test
    public void testLanguageFilterJava() {
        logger.info("Starting testLanguageFilterJava");
        tablePage.selectLanguage("Java");
        logger.info("Verify rows for Java courses only");
        List<String> visibleRowTexts = tablePage.getVisibleRowTexts();
        Assert.assertEquals(tablePage.getVisibleRowCount(), 6, "Java filter should show 6 rows");
        Assert.assertTrue(visibleRowTexts.stream().allMatch(rowText -> rowText.contains("Java")));
    }

    @Test
    public void testLevelFilterBeginnerOnly() {
        logger.info("Starting testLevelFilterBeginnerOnly");
        tablePage.setLevelFilter("Intermediate", false);
        tablePage.setLevelFilter("Advanced", false);
        logger.info("Verify that only Beginner courses are visible");
        Assert.assertEquals(tablePage.getVisibleRowCount(), 5, "Beginner-only filter should show 5 rows");
        Assert.assertTrue(tablePage.getVisibleRowTexts().stream().allMatch(rowText -> rowText.contains("Beginner")));
    }

    @Test
    public void testMinEnrollmentsFilter() {
        logger.info("Starting testMinEnrollmentsFilter");
        tablePage.setMinEnrollments("10000");
        List<Integer> visibleEnrollmentValues = tablePage.getVisibleEnrollmentValues();
        logger.info("Verify every visible row shows enrollments ≥ 10,000");
        Assert.assertEquals(visibleEnrollmentValues.size(), 4, "10,000+ filter should show four rows");
        Assert.assertTrue(visibleEnrollmentValues.stream().allMatch(enrollment -> enrollment >= 10000));
    }

    @Test
    public void testCombinedPythonBeginnerAndMinEnrollmentsFilter() {
        logger.info("Starting testCombinedPythonBeginnerAndMinEnrollmentsFilter");
        tablePage.selectLanguage("Python");
        tablePage.setLevelFilter("Intermediate", false);
        tablePage.setLevelFilter("Advanced", false);
        tablePage.setMinEnrollments("10000");
        logger.info("Verify only Python Beginner courses with ≥ 10,000 enrollments are visible");
        Assert.assertEquals(tablePage.getVisibleRowCount(), 1, "Python + Beginner + 10,000+ should show 1 row");
        Assert.assertTrue(tablePage.getVisibleEnrollmentValues().stream().allMatch(enrollment -> enrollment >= 10000));
        Assert.assertTrue(tablePage.getVisibleCourseNames().stream().allMatch(name -> name.equals("Selenium with Python")));
    }

    @Test
    public void testNoResultsState() {
        logger.info("Starting testNoResultsState");
        tablePage.selectLanguage("Python");
        tablePage.setLevelFilter("Beginner", false);
        tablePage.setLevelFilter("Intermediate", false);
        logger.info("Verify 'No matching courses.' is shown");
        Assert.assertTrue(tablePage.isNoDataVisible(), "No matching courses message should be shown");
        Assert.assertEquals(tablePage.getNoDataText(), "No matching courses.");
    }

    @Test
    public void testResetButtonVisibilityAndBehavior() {
        logger.info("Starting testResetButtonVisibilityAndBehavior");
        tablePage.selectLanguage("Java");
        logger.info("Verify the Reset button becomes visible");
        Assert.assertTrue(tablePage.isResetButtonVisible(), "Reset button should appear after a filter change");
        tablePage.resetFilters();
        logger.info("Verify Language = Any, all Levels checked, Min enrollments = Any");
        Assert.assertTrue(tablePage.isAnyLanguageSelected(), "Language should reset to Any");
        Assert.assertTrue(tablePage.areAllLevelsSelected(), "All level filters should be restored");
        Assert.assertEquals(tablePage.getSelectedMinEnrollments(), "any", "Minimum enrollments should reset to Any");
        logger.info("Verify the Reset button is hidden and all rows are visible");
        Assert.assertFalse(tablePage.isResetButtonVisible(), "Reset button should be hidden after reset");
        Assert.assertEquals(tablePage.getVisibleRowCount(), 9, "All default rows should be visible after reset");
    }

    @Test
    public void testSortByEnrollmentsAscending() {
        logger.info("Starting testSortByEnrollmentsAscending");
        tablePage.selectSortBy("col_enroll");
        List<Integer> actualEnrollments = tablePage.getVisibleEnrollmentValues();
        List<Integer> expectedSortedEnrollments = tablePage.getVisibleEnrollmentValues().stream().sorted().toList();
        logger.info("Verify visible rows are ordered from smallest to largest enrollment");
        Assert.assertEquals(actualEnrollments, expectedSortedEnrollments, "Sort by enrollments should be ascending");
    }

    @Test
    public void testSortByCourseNameAlphabetical() {
        logger.info("Starting testSortByCourseNameAlphabetical");
        List<String> courseNamesBeforeSorting = tablePage.getVisibleCourseNames();
        tablePage.selectSortBy("col_course");
        List<String> courseNamesAfterSorting = tablePage.getVisibleCourseNames();
        logger.info("Verify visible rows are ordered A→Z by course name");
        Assert.assertEquals(courseNamesAfterSorting, courseNamesBeforeSorting.stream().sorted().toList(), "Course names should be sorted alphabetically");
    }
}
