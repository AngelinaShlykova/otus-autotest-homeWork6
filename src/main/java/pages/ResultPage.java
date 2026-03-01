package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, String> expectedData = new HashMap<>();
        expectedData.put("Имя пользователя", expectedUsername);
        expectedData.put("Электронная почта", expectedEmail);

        String dateDDMMYYYY = expectedBirthDate;
        String dateYYYYMMDD = expectedBirthDate.contains(".")
                ? expectedBirthDate.replace(".", "-")
                : expectedBirthDate;
        expectedData.put("Дата рождения", dateDDMMYYYY + "|" + dateYYYYMMDD);

        String levelValue = mapLanguageLevel(expectedLevel);
        expectedData.put("Уровень языка", expectedLevel + "|" + levelValue);

        boolean isCorrect = true;

        for (Map.Entry<String, String> entry : expectedData.entrySet()) {
            String fieldName = entry.getKey();
            String expectedValue = entry.getValue();

            boolean found;
            if (expectedValue.contains("|")) {
                String[] variants = expectedValue.split("\\|");
                found = outputText.contains(variants[0]) ||
                        (variants.length > 1 && outputText.contains(variants[1]));
            } else {
                found = outputText.contains(expectedValue);
            }

            if (!found) {
                logger.error("Поле '{}' не найдено в выводе. Ожидалось: '{}'", fieldName, expectedValue);
                isCorrect = false;
            } else {
                logger.info("Поле '{}' проверено", fieldName);
            }
        }

        if (isCorrect) {
            logger.info("Данные проверены");
        }

        return isCorrect;
    }

    private String mapLanguageLevel(String level) {
        switch (level.trim()) {
            case "Начальный":
                return "beginner";
            case "Средний":
                return "intermediate";
            case "Продвинутый":
                return "advanced";
            case "Носитель языка":
                return "native";
            default:
                return level;
        }
    }
}