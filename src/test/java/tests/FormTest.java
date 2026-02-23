package tests;

import base.AbsBaseTest;
import pages.FormPage;
import pages.ResultPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormTest extends AbsBaseTest {

    @Test
    public void testFormSubmission() {
        String username = System.getProperty("test.username");
        String email = System.getProperty("test.email");
        String password = System.getProperty("test.password");
        String confirmPassword = password;
        String birthDay = System.getProperty("test.birthdate.day");
        String birthMonth = System.getProperty("test.birthdate.month");
        String birthYear = System.getProperty("test.birthdate.year");
        String languageLevel = System.getProperty("test.language.level");

        FormPage formPage = new FormPage(driver);
        formPage.open();

        formPage.fillCompleteForm(
                username, email, password, confirmPassword,
                birthDay, birthMonth, birthYear, languageLevel
        );

        assertTrue(formPage.validatePasswordMatch(password, confirmPassword));

        ResultPage resultPage = formPage.submitForm();
        assertTrue(resultPage.isResultPageDisplayed());

        String expectedBirthDate = birthYear + "-" + birthMonth + "-" + birthDay;
        assertTrue(resultPage.verifyAllData(username, email, expectedBirthDate, languageLevel));
    }
}
