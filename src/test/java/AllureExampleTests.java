import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
public class AllureExampleTests {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    void setUp() {
        open("https://slqamsk.github.io/cases/slflights/v01/");

        // Selenide Conditions:
        $x("//h2").shouldHave(text("Аутентификация"), Duration.ofSeconds(10));

        // JUnit Assertions:
        String text = $x("//h2")
                .should(exist, Duration.ofSeconds(10))
                .getText();
        Assertions.assertEquals("Аутентификация", text);
    }
    @Test
    void emptyFields() {
        $("#loginButton").click();
        $("#message").shouldHave(text("Username and Password are required."));
    }
    @Test
    void emptyFieldsRu() {
        $("#loginButton").click();
        $("#message").shouldHave(text("Имя пользователя и пароль обязательны."));
    }
}