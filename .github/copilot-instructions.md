# Copilot instructions for Selenium TestNG tests

## Project structure
- Use package names under `com.practicetestautomation.tests.*` for test classes.
- Use package names under `com.practicetestautomation.pageobjects.*` for page objects.
- Keep page object actions in `src/main/java/com/practicetestautomation/pageobjects/` and test logic in `src/test/java/com/practicetestautomation/tests/`.

## Test style
- Every test class should extend `BaseTest`.
- Prefer `@Test` methods with descriptive names such as `testLoginFunctionality`, `negativeLoginTest`, or `elementNotInteractableExceptionTest`.
- Use `org.testng.Assert` for assertions.
- Keep tests readable and focused on one behavior per method.
- Add test groups when relevant (`positive`, `negative`, `regression`, `smoke`).

## Page Object Model rules
- Do not write browser interaction code directly in test methods.
- Add or update page object classes for UI actions and locators.
- Reuse `BasePage` helpers (`waitForElement`, `waitForIsDisplayed`, `waitForIsHidden`) instead of arbitrary sleeps.
- Prefer explicit locators and helper methods like `visit()`, `enterUsername()`, `clickSubmitButton()`, etc.

## Browser and setup rules
- Use the shared `BaseTest` browser lifecycle; do not duplicate driver setup/teardown in tests.
- Respect the `browser` TestNG parameter (`chrome`, `firefox`) and default to Chrome when unspecified.
- Ensure cleanup happens in `@AfterMethod` via the shared test base.

## Suite integration
- If a new test should be part of the default regression suite, update `src/test/resources/TestSuites/FullRegressionSuite.xml` accordingly.
- Keep suite naming and XML structure consistent with the existing files.

## Quality bar
- Prefer deterministic waits over timing assumptions.
- Avoid brittle assertions that depend on incidental page text not explicitly verified by the page object.
- Keep naming consistent with the existing project conventions.
