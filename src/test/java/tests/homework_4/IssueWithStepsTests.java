package tests.homework_4;

import Api.ApiSteps;
import Api.BasicSteps;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.LoadCredentials.getCredentialsFromJson;

public class IssueWithStepsTests {
    private static final String BASE_URL = "https://github.com/";
    private static final String REPOSITORY = "belamstel/qa_guruProjects";
    private static final String LOGIN = getCredentialsFromJson("ApiTests.secret", "login");
    private static final String PASSWORD = getCredentialsFromJson("ApiTests.secret", "pass");
    private static final String ISSUE_TITLE ="Homework 4";
    private static final String ISSUE_TEXT = "test";
    private static String number = "";
    private final BasicSteps webSteps = new BasicSteps();
    private final ApiSteps apiSteps = new ApiSteps();

    @BeforeEach
    public void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    @DisplayName("Пользователь не должен найти отсутствующую Issue по номеру")
    public void shouldNotFindIssueByMissingNumber() {
        webSteps.openMainPage(BASE_URL);
        webSteps.signInGithub(LOGIN,PASSWORD);
        webSteps.searchForRepository(BASE_URL, REPOSITORY);
        number = webSteps.createNewIssue(ISSUE_TITLE,ISSUE_TEXT);
        apiSteps.shouldSeeIssueWithNumber(number, ISSUE_TITLE,ISSUE_TEXT);
    }

}