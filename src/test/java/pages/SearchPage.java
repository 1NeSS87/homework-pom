package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import utils.DateUtils;


public class SearchPage {
    SelenideElement
            cityFrom = $("#departureCity"),
            cityTo = $("#arrivalCity"),
            departureDate = $("#departureDate"),
            findButton = $x("//button[.='Найти']"),
            message = $("#searchMessage");

    @Step("Поиск рейсов (задаём только дату)")
    public void search(String departureDate) {
        this.departureDate.setValue(
                DateUtils.formatDate(departureDate, Configuration.browser)
        );
        this.findButton.click();
    }

    @Step("Поиск рейсов")
    public void search(String departureDate, String from, String to) {
        this.cityFrom.selectOption(from);
        this.cityTo.selectOption(to);
        this.departureDate.setValue(
                DateUtils.formatDate(departureDate, Configuration.browser)
        );
        this.findButton.click();
    }

    @Step("Проверка, что дата не указана")
    public void isDepartureDateEmpty() {
        this.message.shouldHave(text("Пожалуйста, укажите дату вылета."));
    }

    @Step("Проверка, что дата в прошлом")
    public void isDateInPast(){
        this.message.shouldHave(text("Невозможно осуществить поиск: выбранная дата уже прошла."));
    }

}