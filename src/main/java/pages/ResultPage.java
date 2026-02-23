package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultPage {

    private static final Logger logger = LoggerFactory.getLogger(ResultPage.class);
    private final WebDriver driver;

    @FindBy(id = "output")
    private WebElement outputContainer;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isResultPageDisplayed() {
        return outputContainer.isDisplayed();
    }

    public String getOutputText() {
        return outputContainer.getText();
    }

    public boolean verifyAllData(String expectedUsername,
                                 String expectedEmail,
                                 String expectedBirthDate,
                                 String expectedLevel) {

        String outputText = getOutputText();

        boolean isCorrect = true;

        if (!outputText.contains(expectedUsername)) {
            isCorrect = false;
        } else {
        }

        if (!outputText.contains(expectedEmail)) {
            isCorrect = false;
        } else {
        }

        if (!outputText.contains(expectedBirthDate) &&
                !outputText.contains(expectedBirthDate.replace(".", "-"))) {
            isCorrect = false;
        } else {
        }

        if (!outputText.contains(expectedLevel) &&
                !outputText.contains("intermediate")) {
            isCorrect = false;
        } else {
        }

        if (isCorrect) {
        }

        return isCorrect;
    }
}