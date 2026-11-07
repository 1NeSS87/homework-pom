package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FlightsListPage {
    SelenideElement
            flightsTable = $("#flightsTable"),
            registerButton = $x("//button[.='Зарегистрироваться']"),
            newSearchButton = $x("//button[@class='new-search-btn']"),
            sortField = $("#sortField");

    ElementsCollection allFlights = $$x("//table/tbody/tr");

    @Step("Выбираем первый рейс в списке")
    public void registerToFirstFlight() {
        this.registerButton.click();
    }

    @Step("Проверяем, что рейсов не найдено")
    public void isNoFlights() {
        flightsTable.shouldHave(text("Рейсов по вашему запросу не найдено."));
    }

    @Step("Возвращаемся на страницу поиска")
    public void newSearch() {
        this.newSearchButton.click();
    }

    public void sortByPrice() {
        this.sortField.selectOptionByValue("price");
    }

    @Step("Проверка, что рейсы отсортированы по времени")
    public void isTimeSorted() {
        LocalTime prev = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (SelenideElement currentFlight : allFlights) {
            String timeText = currentFlight.$x("./td[5]").text().trim();
            //System.out.println(timeText);
            LocalTime current = LocalTime.parse(timeText, formatter);

            if (prev != null) {
                Assertions.assertTrue(prev.isBefore(current));
            }
            prev = current;
        }
    }
}