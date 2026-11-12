import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ResultAviasalesPage;
import pages.SearchAviasalesPage;
import utils.DateUtils;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class POMAviasalesTests {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        Configuration.pageLoadStrategy = "eager";
        open("https://www.aviasales.ru/?params=MOW1");
        getWebDriver().manage().window().maximize();
    }

    @DisplayName("Мониторинг цен")
    @Test
    void testPriceMonitoring() {
        SearchAviasalesPage searchAviasalesPage = new SearchAviasalesPage();
        searchAviasalesPage.setInputCityTo("Питер", "Санкт-Петербург");

        searchAviasalesPage.openStartDate();
        searchAviasalesPage.selectionDate(DateUtils.datePlusMonths(1));

        searchAviasalesPage.openEndDate();
        searchAviasalesPage.selectionDate(DateUtils.datePlusWeeks(DateUtils.datePlusMonths(1),2));

        searchAviasalesPage.openPassengers();
        searchAviasalesPage.addAdult();
        searchAviasalesPage.addChildren();

        searchAviasalesPage.submitForm();

        switchTo().window(1);

        ResultAviasalesPage resultAviasalesPage = new ResultAviasalesPage();
        resultAviasalesPage.printPrice("Оптимальный");
        resultAviasalesPage.printPrice("Самый дешёвый");
    }
}
