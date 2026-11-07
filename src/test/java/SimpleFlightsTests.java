import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class SimpleFlightsTests {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        //Configuration.browser = "firefox";
    }

    @BeforeEach
    void setUp() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        getWebDriver().manage().window().maximize();
    }

    @DisplayName("01. Пустые логин и пароль.")
    @Test
    void test01Empty() {
        $("#loginButton").click();
        $("#message").shouldHave(text("Username and Password are required."));
    }

    @DisplayName("02. Неверный пароль.")
    @Test
    void test02WrongPassword() {
        step("Задать имя пользователя и пароль", () ->
            {
                $("#username").setValue("standard_user");
                $("#password").setValue("WrongPassword");
            }
        );

        step("Нажать кнопку \"Войти\" и проверить сообщение" , () ->
                {
                    $("#loginButton").click();
                    $("#message").shouldHave(text("Неверное имя пользователя или пароль."));
                }
        );
    }

    @DisplayName("03. Успешный вход в систему.")
    @Test
    void test03SuccessLogin() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"), Duration.ofSeconds(10));
    }

    @DisplayName("04. Не указана дата вылета.")
    @Test
    void test04NoDate() {
        step("Страница логина", () ->
                {
                    $("#username").setValue("standard_user");
                    $("#password").setValue("stand_pass1");
                    $("#loginButton").click();
                }
        );

        step("Страница поиска - не указана дата вылета", () ->
                {
                    $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));
                    $("#departureDate").setValue("");
                    $x("//button[.='Найти']").click();
                    $("#searchMessage").shouldHave(text("Пожалуйста, укажите дату вылета."));
                }
        );
    }

    @DisplayName("05. Не найдены рейсы.")
    @Test
    void test05FlightsNotFound() {
        step("Страница логина", () ->
                {
                    $("#username").setValue("standard_user");
                    $("#password").setValue("stand_pass1");
                    $("#loginButton").click();
                }
        );

        step("Страница поиска", () ->
                {
                    $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));
                    $("#departureCity").selectOption("Казань");
                    $("#arrivalCity").selectOption("Париж");
                    $("#departureDate").setValue(formatDate(nextDayOfWeekDate(1), Configuration.browser));
                    sleep(5_000);
                    $x("//button[.='Найти']").click();
                }
        );

        step("Страница списка рейсов - нет рейса", () ->
                {
                    $("#flightsTable").shouldHave(text("Рейсов по вашему запросу не найдено."));
                }
        );
    }

    @DisplayName("06. Неправильный номер паспорта.")
    @Test
    void test06WrongPassportNumber() {
        step("Страница логина", () ->
                {
                    $("#username").setValue("standard_user");
                    $("#password").setValue("stand_pass1");
                    $("#loginButton").click();
                }
        );

        step("Страница поиска рейсов", () ->
                {
                    $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));
                    $("#departureCity").selectOption("Москва");
                    $("#arrivalCity").selectOption("Нью-Йорк");
                    $("#departureDate").setValue(formatDate(nextDayOfWeekDate(1), Configuration.browser));
                    $x("//button[.='Найти']").click();
                }
        );

        step("Страница списка рейсов", () ->
                {
                    $x("//button[.='Зарегистрироваться']").click();
                }
        );

        step("Страница регистрации на рейс - некорректный номер паспорта", () ->
                {
                    $("#passportNumber").setValue("паспорт");
                    $x("//button[.='Завершить регистрацию']").click();
                    $("#registrationMessage").shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));
                }
        );
    }

    @DisplayName("07. Успешная регистрация на рейс.")
    @Test
    void test07SuccessRegistration() {
        step("Страница логина", () ->
                {
                    $("#username").setValue("standard_user");
                    $("#password").setValue("stand_pass1");
                    $("#loginButton").click();
                }
        );

        step("Страница поиска рейсов", () ->
                {
                    $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));
                    $("#departureCity").selectOption("Москва");
                    $("#arrivalCity").selectOption("Нью-Йорк");
                    $("#departureDate").setValue(formatDate(nextDayOfWeekDate(1), Configuration.browser));
                    $x("//button[.='Найти']").click();
                }
        );

        step("Страница списка рейсов", () ->
                {
                    $x("//button[.='Зарегистрироваться']").click();
                }
        );

        step("Страница регистрации на рейс", () ->
                {
                    $x("//button[.='Завершить регистрацию']").click();
                    Alert alert= switchTo().alert();
                    assertTrue(alert.getText().contains("Бронирование завершено"));
                    alert.accept();
                    $("#registrationMessage").shouldHave(text("Регистрация успешно завершена!"));
                }
        );
    }

    private static String nextDayOfWeekDate(int dayOfWeek) {
        LocalDate today = LocalDate.now();
        DayOfWeek target = DayOfWeek.of(dayOfWeek);
        LocalDate next = today.with(TemporalAdjusters.next(target));
        return next.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private static String formatDate(String date, String browser) {
        LocalDate d = LocalDate.parse(date.trim(),
                DateTimeFormatter.ISO_LOCAL_DATE);
        if (browser == null || browser.isBlank()) {
            return d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        return switch (browser.trim().toLowerCase()) {
            case "chromium", "chrome" -> d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            case "firefox"            -> d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            default                   -> d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        };
    }
}