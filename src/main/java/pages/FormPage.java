package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.Waiters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormPage {

    private static final Logger logger = LoggerFactory.getLogger(FormPage.class);
    private final WebDriver driver;
    private final Waiters waiters;

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "confirm_password")
    private WebElement confirmPasswordField;

    @FindBy(id = "birthdate")
    private WebElement birthDateField;

    @FindBy(id = "language_level")
    private WebElement languageLevelDropdown;

    @FindBy(css = "input[type='submit']")
    private WebElement submitButton;

    public FormPage(WebDriver driver) {
        this.driver = driver;
        this.waiters = new Waiters(driver);
        PageFactory.initElements(driver, this);
    }

    public FormPage open() {
        String baseUrl = System.getProperty("base.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalStateException("свойство не задано");
        }
        driver.get(baseUrl + "/form.html");
        return this;
    }

    public FormPage fillUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
        return this;
    }

    public FormPage fillEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public FormPage fillPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public FormPage fillConfirmPassword(String confirmPassword) {
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(confirmPassword);
        return this;
    }

    public FormPage fillBirthDate(String day, String month, String year) {
        birthDateField.clear();
        birthDateField.sendKeys(day + "." + month + "." + year);
        return this;
    }

    public FormPage selectLanguageLevel(String level) {
        Select select = new Select(languageLevelDropdown);
        switch (level) {
            case "Начальный" -> select.selectByValue("beginner");
            case "Средний" -> select.selectByValue("intermediate");
            case "Продвинутый" -> select.selectByValue("advanced");
            case "Носитель языка" -> select.selectByValue("native");
            default -> select.selectByVisibleText(level);
        }
        return this;
    }

    public FormPage fillCompleteForm(String username,
                                     String email,
                                     String password,
                                     String confirmPassword,
                                     String birthDay,
                                     String birthMonth,
                                     String birthYear,
                                     String languageLevel) {
        return fillUsername(username)
                .fillEmail(email)
                .fillPassword(password)
                .fillConfirmPassword(confirmPassword)
                .fillBirthDate(birthDay, birthMonth, birthYear)
                .selectLanguageLevel(languageLevel);
    }

    public boolean validatePasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public ResultPage submitForm() {
        submitButton.click();
        return new ResultPage(driver);
    }
}
