import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.FlightsListPage;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.SearchPage;
import utils.DateUtils;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMAdditionalTests {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        //Configuration.browser = "firefox";
    }

    @BeforeEach
    void setUp() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        getWebDriver().manage().window().maximize();
        //sleep(10_000);
    }

    @DisplayName("01. Пустые поля при регистрации")
    @Test
    void test01EmptyField() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.search(DateUtils.nextDayOfWeekDate(1), "Москва", "Нью-Йорк");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.registerToFirstFlight();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage.registration("", "", "", "");
        registrationPage.isErrorFillAllFields();
    }

    @DisplayName("02. Мульти-логин под разными пользователями")
    @ParameterizedTest
    @CsvFileSource(resources = "logins.csv", numLinesToSkip = 1)
    void test02MultiLogin(String userName, String passWord, String fio) {
        LoginPage loginPage = new LoginPage();
        loginPage.login(userName, passWord);
        loginPage.isLoginSuccessful(fio);
    }

    @DisplayName("03. Поиск с датой в прошлом")
    @Test
    void test03DateInPast() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");

        SearchPage searchPage = new SearchPage();
        searchPage.search("2024-01-01");
        searchPage.isDateInPast();
    }

    @DisplayName("04. Длинный тест")
    @Test
    void test04LongTest() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");

        SearchPage searchPage = new SearchPage();
        searchPage.search(DateUtils.nextDayOfWeekDate(1), "Казань", "Париж");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.isNoFlights();

        flightsList.newSearch();
        searchPage.search(DateUtils.nextDayOfWeekDate(1), "Москва", "Нью-Йорк");

        flightsList.registerToFirstFlight();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage.registration("Иван Петров", "", "test@mail.ru", "+79999999999");
        registrationPage.isErrorFillAllFields();

        registrationPage.registration("Иван Петров", "1234 123456", "test@mail.ru", "+79999999999");
        registrationPage.successRegistration();
    }

    @DisplayName("05. Сортировка по цене (демо)")
    @Test
    void test05DemoCollection() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");

        SearchPage searchPage = new SearchPage();
        searchPage.search("2026-03-16", "Москва", "Нью-Йорк");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.sortByPrice();
        flightsList.isTimeSorted();
    }
}