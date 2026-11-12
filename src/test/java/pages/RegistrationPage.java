package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationPage {
    SelenideElement
        flightInfo = $("#flightRegistrationInfo"),
        buttonFinishRegistration = $x("//button[contains(.,'Завершить регистрацию')]"),
        buttonReturn = $x("//button[contains(.,'Вернуться к найденным рейсам')]"),
        fio = $(By.id("passengerName")),
        passport = $("#passportNumber"),
        email = $("#email"),
        phone = $("#phone"),
        message = $("#registrationMessage");

// ******* PAGE CLASSES ********* //
    public void setFIO(String fio) {
        this.fio.setValue(fio);
    }

    @Step("Ввод номера паспорта")
    public void setPassport(String passport) {
        this.passport.setValue(passport);
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPhone(String email) {
        this.phone.setValue(email);
    }


    // ******** "Бизнес-слой" ******** //

    @Step("Заполнение полей и регистрация")
    public void registration(String fio, String passport, String email, String phone) {

        this.setFIO(fio);
        this.setPassport(passport);
        this.setEmail(email);
        this.setPhone(phone);
        buttonFinishRegistration.click();
    }


    @Step("Успешная регистрация")
    public void successRegistration() {
        buttonFinishRegistration.click();
        Alert alert= switchTo().alert();

        assertTrue(alert.getText().contains("Бронирование завершено"));
        alert.accept();
        this.message.shouldHave(text("Регистрация успешно завершена!"));
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



    @Step("Нажать \"Завершить регистрацию\"")
    public void finishRegistration() {
        buttonFinishRegistration.click();
    }

    @Step("Нажать \"Вернуться к найденным рейсам\"")
    public void returnSearchResult() {
        this.buttonReturn.click();
    }

    @Step("Ошибка: номер паспорта должен содержать только цифры и пробелы")
    public void isPassportInvalid() {
        this.message.shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));
    }


}