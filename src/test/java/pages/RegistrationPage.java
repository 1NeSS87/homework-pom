package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationPage {
    SelenideElement
        flightInfo = $("#flightRegistrationInfo"),
        buttonFinishRegistration = $x("//button[contains(.,'Завершить регистрацию')]"),
        fio = $("#passengerName"),
        passport = $("#passportNumber"),
        email = $("#email"),
        phone = $("#phone"),
        message = $("#registrationMessage");


    @Step("Успешная регистрация")
    public void successRegistration() {
        buttonFinishRegistration.click();
        Alert alert= switchTo().alert();
        assertTrue(alert.getText().contains("Бронирование завершено"));
        alert.accept();
        this.message.shouldHave(text("Регистрация успешно завершена!"));
    }

    @Step("Заполнение полей и регистрация")
    public void registration(String fio, String passport, String email, String phone) {
        this.fio.setValue(fio);
        this.passport.setValue(passport);
        this.email.setValue(email);
        this.phone.setValue(phone);
        buttonFinishRegistration.click();
    }

    @Step("Проверка, что данные рейса корректные")
    public void isFlightDataCorrect(String cityFrom, String cityTo) {
        flightInfo
                .shouldBe(visible)
                .shouldHave(text(cityFrom + " → " + cityTo));
    }

    @Step("Появилась ошибка Заполните все поля")
    public void isErrorFillAllFields() {
        this.message.shouldHave(text("Пожалуйста, заполните все поля."));
    }

    @Step("Ввод номера паспорта")
    public void setPassport(String passport) {
        this.passport.setValue(passport);
    }

    @Step("Нажать \"Завершить регистрацию\"")
    public void finishRegistration() {
        buttonFinishRegistration.click();
    }

    @Step("Ошибка: номер паспорта должен содержать только цифры и пробелы")
    public void isPassportInvalid() {
        this.message.shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));
    }


}