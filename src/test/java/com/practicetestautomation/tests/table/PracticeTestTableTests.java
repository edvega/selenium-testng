package com.practicetestautomation.tests.table;

import com.practicetestautomation.pageobjects.PracticeTestTablePage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PracticeTestTableTests extends BaseTest {

    private PracticeTestTablePage practiceTestTablePage;

    @BeforeMethod(alwaysRun = true)
    public void setUpTablePage() {
        practiceTestTablePage = new PracticeTestTablePage(driver);
        practiceTestTablePage.visit();
    }

    @Test
    public void testLanguageFilterJava() {
        practiceTestTablePage.selectLanguage("Java");

        Assert.assertEquals(practiceTestTablePage.getVisibleRowCount(), 6, "Java filter should show 6 rows");
        for (String rowText : practiceTestTablePage.getVisibleRowTexts()) {
            Assert.assertTrue(rowText.contains("Java"), "Java rows should include the Java language: " + rowText);
        }
    }

    @Test
    public void testLevelFilterBeginnerOnly() {
        practiceTestTablePage.setLevelFilter("Intermediate", false);
        practiceTestTablePage.setLevelFilter("Advanced", false);

        Assert.assertEquals(practiceTestTablePage.getVisibleRowCount(), 5, "Beginner-only filter should show 5 rows");
        for (String rowText : practiceTestTablePage.getVisibleRowTexts()) {
            Assert.assertTrue(rowText.contains("Beginner"), "Beginner-only rows should all be Beginner: " + rowText);
        }
    }

    @Test
    public void testMinEnrollmentsFilter() {
        practiceTestTablePage.setMinEnrollments("10000");

        List<Integer> visibleEnrollmentValues = practiceTestTablePage.getVisibleEnrollmentValues();
        Assert.assertEquals(visibleEnrollmentValues.size(), 4, "10,000+ filter should show four rows");
        for (Integer enrollment : visibleEnrollmentValues) {
            Assert.assertTrue(enrollment >= 10000, "Each visible enrollment should be 10,000 or greater: " + enrollment);
        }
    }

    @Test
    public void testCombinedPythonBeginnerAndMinEnrollmentsFilter() {
        practiceTestTablePage.selectLanguage("Python");
        practiceTestTablePage.setLevelFilter("Intermediate", false);
        practiceTestTablePage.setLevelFilter("Advanced", false);
        practiceTestTablePage.setMinEnrollments("10000");

        Assert.assertEquals(practiceTestTablePage.getVisibleRowCount(), 1, "Python + Beginner + 10,000+ should show 1 row");
        Assert.assertEquals(practiceTestTablePage.getVisibleCourseNames().get(0), "Selenium with Python");
    }

    @Test
    public void testNoResultsState() {
        practiceTestTablePage.selectLanguage("Python");
        practiceTestTablePage.setLevelFilter("Beginner", false);
        practiceTestTablePage.setLevelFilter("Intermediate", false);
        practiceTestTablePage.setLevelFilter("Advanced", false);

        Assert.assertTrue(practiceTestTablePage.isNoDataVisible(), "No matching courses message should be shown");
        Assert.assertEquals(practiceTestTablePage.getNoDataText(), "No matching courses.");
    }

    @Test
    public void testResetButtonVisibilityAndBehavior() {
        practiceTestTablePage.selectLanguage("Java");
        Assert.assertTrue(practiceTestTablePage.isResetButtonVisible(), "Reset button should appear after a filter change");

        practiceTestTablePage.resetFilters();

        Assert.assertTrue(practiceTestTablePage.isAnyLanguageSelected(), "Language should reset to Any");
        Assert.assertTrue(practiceTestTablePage.areAllLevelsSelected(), "All level filters should be restored");
        Assert.assertEquals(practiceTestTablePage.getSelectedMinEnrollments(), "any", "Minimum enrollments should reset to Any");
        Assert.assertFalse(practiceTestTablePage.isResetButtonVisible(), "Reset button should be hidden after reset");
        Assert.assertEquals(practiceTestTablePage.getVisibleRowCount(), 9, "All default rows should be visible after reset");
    }

    @Test
    public void testSortByEnrollmentsAscending() {
        practiceTestTablePage.selectSortBy("col_enroll");

        List<Integer> enrollments = practiceTestTablePage.getVisibleEnrollmentValues();
        Assert.assertTrue(isSortedAscending(enrollments), "Sort by enrollments should be ascending");
        Assert.assertEquals(enrollments.get(0).intValue(), 1365, "Lowest enrollment should be first");
    }

    @Test
    public void testSortByCourseNameAlphabetical() {
        practiceTestTablePage.selectSortBy("col_course");

        List<String> courseNames = practiceTestTablePage.getVisibleCourseNames();
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
