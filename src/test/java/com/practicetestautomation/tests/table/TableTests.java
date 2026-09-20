package com.practicetestautomation.tests.table;

import com.practicetestautomation.pageobjects.TablePage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
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
        Assert.assertEquals(visibleEnrollmentValues.size(), 4, "10,000+ filter should show four rows");
        Assert.assertTrue(visibleEnrollmentValues.stream().allMatch(enrollment -> enrollment >= 10000));
    }

    @Test
    public void testCombinedPythonBeginnerAndMinEnrollmentsFilter() {
        tablePage.selectLanguage("Python");
        tablePage.setLevelFilter("Intermediate", false);
        tablePage.setLevelFilter("Advanced", false);
        tablePage.setMinEnrollments("10000");

        Assert.assertEquals(tablePage.getVisibleRowCount(), 1, "Python + Beginner + 10,000+ should show 1 row");
        Assert.assertEquals(tablePage.getVisibleCourseNames().get(0), "Selenium with Python");
    }

    @Test
    public void testNoResultsState() {
        tablePage.selectLanguage("Python");
        tablePage.setLevelFilter("Beginner", false);
        tablePage.setLevelFilter("Intermediate", false);
        tablePage.setLevelFilter("Advanced", false);

        Assert.assertTrue(tablePage.isNoDataVisible(), "No matching courses message should be shown");
        Assert.assertEquals(tablePage.getNoDataText(), "No matching courses.");
    }

    @Test
    public void testResetButtonVisibilityAndBehavior() {
        tablePage.selectLanguage("Java");
        Assert.assertTrue(tablePage.isResetButtonVisible(), "Reset button should appear after a filter change");

        tablePage.resetFilters();

        Assert.assertTrue(tablePage.isAnyLanguageSelected(), "Language should reset to Any");
        Assert.assertTrue(tablePage.areAllLevelsSelected(), "All level filters should be restored");
        Assert.assertEquals(tablePage.getSelectedMinEnrollments(), "any", "Minimum enrollments should reset to Any");
        Assert.assertFalse(tablePage.isResetButtonVisible(), "Reset button should be hidden after reset");
        Assert.assertEquals(tablePage.getVisibleRowCount(), 9, "All default rows should be visible after reset");
    }

    @Test
    public void testSortByEnrollmentsAscending() {
        tablePage.selectSortBy("col_enroll");

        List<Integer> enrollments = tablePage.getVisibleEnrollmentValues();
        Assert.assertTrue(isSortedAscending(enrollments), "Sort by enrollments should be ascending");
        Assert.assertEquals(enrollments.get(0).intValue(), 1365, "Lowest enrollment should be first");
    }

    @Test
    public void testSortByCourseNameAlphabetical() {
        tablePage.selectSortBy("col_course");

        List<String> courseNames = tablePage.getVisibleCourseNames();
        Assert.assertTrue(isSortedAlphabetically(courseNames), "Course names should be sorted alphabetically");
    }

    private boolean isSortedAscending(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i - 1) > values.get(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedAlphabetically(List<String> values) {
        List<String> sortedValues = new ArrayList<>(values);
        sortedValues.sort(Comparator.naturalOrder());
        return sortedValues.equals(values);
    }
}
