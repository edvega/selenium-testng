# Project context

This repo is a Java + Maven + Selenium + TestNG browser automation project.

## What it is
- `src/main/java/com/practicetestautomation/pageobjects/`: Page Object Model classes.
- `src/test/java/com/practicetestautomation/tests/`: TestNG tests and shared `BaseTest` setup/teardown.
- `src/test/resources/TestSuites/`: TestNG XML suites.

## How the project works
- `BaseTest` creates the browser based on the `browser` TestNG parameter and closes it in `@AfterMethod`.
- Default browser is Chrome; Firefox is also supported.
- Page objects standardize browser interactions and waits via `BasePage`.
- Tests validate real web pages from `practicetestautomation.com` and use assertions in TestNG.

## Typical commands
- Run all tests: `mvn test`
- Run a single test class: `mvn -Dtest=LoginTests test`
- Run a specific method: `mvn -Dtest=LoginTests#testLoginFunctionality test`

## Conventions for changes
- Prefer extending existing page objects and `BaseTest` rather than creating new test frameworks.
- Keep browser waits in page objects; avoid arbitrary sleeps.
- Preserve TestNG naming and package structure (`tests.*`, `pageobjects.*`).
- Use Maven/Java conventions; do not add extra build systems or dependencies unless required.
