package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;


public class SearchAviasalesPage {
    SelenideElement
            inputCityTo = $("#avia_form_destination-input"),
            buttonStartDate = $("button[data-test-id='start-date-field']"),
            buttonEndDate = $("button[data-test-id='end-date-field']"),
            buttonPassengers = $("button[data-test-id='passengers-field']"),
            buttonAddAdults = $("div[data-test-id='number-of-adults'] [data-test-id='increase-button']"),
            buttonAddChildren = $("div[data-test-id='number-of-children'] [data-test-id='increase-button']"),
            buttonSubmit = $("button[data-test-id='form-submit']");

    @Step("Ввод и ожидание города прибытия")
    public void setInputCityTo(String name, String value) {
        this.inputCityTo.setValue(name);
        sleep(1000);
        $("h1").click();
        $("input#avia_form_destination-input[value='" + value + "']")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Step("Открыть выбор даты отправления")
    public void openStartDate() {
        this.buttonStartDate.click();
    }

    @Step("Открыть выбор даты прибытия")
    public void openEndDate() {
        this.buttonEndDate.click();
    }

    @Step("Выбор даты в календаре")
    public void selectionDate(String date) {
        $("div[data-test-id='date-" + date + "']").closest("button").click();
    }

    @Step("Открыть выбор пассажиров")
    public void openPassengers() {
        this.buttonPassengers.click();
    }

    @Step("Добавить пассажира взрослого")
    public void addAdult() {
        this.buttonAddAdults.click();
    }

    @Step("Добавить пассажира ребенка")
    public void addChildren() {
        this.buttonAddChildren.click();
    }

    @Step("Нажать \"Найти билеты\"")
    public void submitForm() {
        this.buttonSubmit.click();
    }
}