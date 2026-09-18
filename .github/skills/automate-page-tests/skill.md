---
name: automate-page-tests
description: Automate every test case listed on a web page by navigating it with Chrome DevTools MCP, creating page objects, implementing TestNG tests, and validating the suite.
---

# Automate page test cases for this Selenium TestNG repo

Use this skill when the user provides a page URL and wants the agent to automate all listed test cases from that page.

## Objective
Create a complete, production-quality Selenium TestNG automation flow for the target page, following the conventions already used in this repository.

## Required workflow
1. Accept a single input: the URL of the page under test.
2. Use the Chrome DevTools MCP server (`chrome-devtools`) to open the URL and inspect the page.
3. Read the page content, identify all test cases listed or implied by the page, and infer user workflows.
4. Manually validate each case in the browser to confirm expected behavior before writing automation.
5. Map each case to page objects under `src/main/java/com/practicetestautomation/pageobjects/`.
6. Add or update test classes under `src/test/java/com/practicetestautomation/tests/`.
7. Keep test logic in TestNG test classes and UI actions in page objects; never perform raw driver actions directly in test methods.
8. Use `BaseTest` for setup and teardown.
9. Add relevant entries to `src/test/resources/TestSuites/FullRegressionSuite.xml` if the new tests belong in the default suite.
10. Run the relevant Maven tests and confirm they pass.
11. Provide a brief final report with what was added and the verification result.

## Project rules that must be followed
- Use package names consistent with the repo: `com.practicetestautomation.pageobjects.*` and `com.practicetestautomation.tests.*`.
- Every test class should extend `BaseTest`.
- Use `org.testng.Assert` for assertions.
- Prefer descriptive method names like `testLoginFunctionality`, `negativeLoginTest`, `elementNotInteractableExceptionTest`.
- Reuse `BasePage` helpers instead of writing ad hoc waits or sleep calls.
- Keep browser interactions inside page objects; page objects should expose clean, behavior-oriented methods.
- Prefer deterministic waits and explicit element checks.
- Keep tests focused on one behavior per method.
- If a new page requires locators, add them in the corresponding page object class rather than embedding selectors in tests.
- Preserve existing naming patterns and folder structure.

## Execution checklist
- Check whether the target page is already represented by a page object class.
- If not, create one with a `visit()` method and the necessary locator helpers.
- Create a test class that exercises the user-visible behavior and asserts the expected results.
- If the user says the page contains multiple cases, automate each one coherently rather than creating a single broad test.
- Run `mvn test` or a narrower target when appropriate.
- If a test fails, fix either the page object interaction or the assertion, not the test framework.

## Output expectations
At the end, return:
- the page(s) automated
- the page objects created or updated
- the test classes added or modified
- the exact verification command used
- the final pass/fail status with evidence

## Important notes
- Do not add unrelated frameworks or tools.
- Keep the solution aligned with the existing repo and `.github/copilot-instructions.md` guidance.
- Use Chrome DevTools MCP to inspect the actual page before writing automation, then validate the flow manually in the browser.
- If the page requires dynamic waits, use existing `BasePage` helpers rather than arbitrary timing.
