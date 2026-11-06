import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
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