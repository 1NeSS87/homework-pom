import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.FlightsListPage;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.SearchPage;
import utils.DateUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class POMFlightsTests {

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

    @DisplayName("01. Пустые логин и пароль")
    @Test
    void test01Empty() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickLogin();
        loginPage.isLoginRequired();
    }

    @DisplayName("02. Неверный пароль")
    @Test
    void test02WrongPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "WrongPassword");
        loginPage.isLoginUnsuccessful();
    }

    @DisplayName("03. Успешный вход в систему")
    @Test
    void test03SuccessLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");
    }

    @DisplayName("04. Не указана дата вылета")
    @Test
    void test04NoDate() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.search("");
        searchPage.isDepartureDateEmpty();
    }

    @DisplayName("05. Не найдены рейсы")
    @Test
    void test05FlightsNotFound() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.search(DateUtils.nextDayOfWeekDate(1), "Казань", "Париж");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.isNoFlights();
    }

    @DisplayName("06. Неправильный номер паспорта")
    @Test
    void test06WrongPassportNumber() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.search(DateUtils.nextDayOfWeekDate(1), "Москва", "Нью-Йорк");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.registerToFirstFlight();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.setPassport("паспорт");
        registrationPage.finishRegistration();
        registrationPage.isPassportInvalid();
    }

    @DisplayName("07. Успешная регистрация на рейс")
    @Test
    void test07SuccessRegistration() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.search(DateUtils.nextDayOfWeekDate(1), "Москва", "Нью-Йорк");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.registerToFirstFlight();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.successRegistration();
    }

    @DisplayName("08. Успешный выход из системы")
    @Test
    void test08Logout() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.logoutSearch();

        loginPage.afterLogout();
    }

    @DisplayName("09. Повторный переход и успешная регистрация на рейс")
    @Test
    void test09RetrySearch() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.search(DateUtils.nextDayOfWeekDate(1), "Москва", "Нью-Йорк");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.registerToFirstFlight();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.returnSearchResult();

        flightsList.registerToFirstFlight();

        registrationPage.successRegistration();
    }

    @DisplayName("10. Проверка, что список отсортирован по цене (возрастание)")
    @Test
    void test10CheckSortByPriceASC() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");

        SearchPage searchPage = new SearchPage();
        searchPage.search(DateUtils.nextDayOfWeekDate(1), "Москва", "Нью-Йорк");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.sortByPrice();
        flightsList.isPriceSorted();
    }
}