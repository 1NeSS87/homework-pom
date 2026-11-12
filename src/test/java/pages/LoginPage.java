package pages;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class LoginPage {
    SelenideElement username; // = $("#username");
    SelenideElement password = $("#password");

    SelenideElement
        loginButton = $("#loginButton"),
        errorMessage = $("#message"),
        greeting = $("#greeting"),
        header = $("#loginContainer").find("h2");

    public LoginPage() {
        // Конструктор писать не обязательно
        // Так используют, когда, например, надо из базы данных взять
        // информацию о локаторе
        this.username = $("#username");
    }

    @Step("Вход в систему")
    public void login(String username, String password) {
        //$("#username").setValue("standard_user");
        this.username.setValue(username);
        this.password.setValue(password);
        this.loginButton.click();
    }

    @Step("Успешный выход из системы")
    public void afterLogout() {
        this.header.shouldBe(visible);
        this.header.shouldHave(text("Аутентификация"));
        this.loginButton.shouldBe(visible);
        this.greeting.shouldNot(visible);
    }

    @Step("Неуспешный логин")
    public void isLoginUnsuccessful() {
        this.errorMessage.shouldBe(visible);
        this.errorMessage.shouldHave(Condition.cssClass("error"));
        this.errorMessage.shouldHave(text("Неверное имя пользователя или пароль."));
    }

    @Step("Успешный логин")
    public void isLoginSuccessful(String fio) {
        this.greeting.shouldHave(text("Добро пожаловать, " + fio + "!"));
    }

    @Step("Нажать кнопку \"Войти\"")
    public void clickLogin() {
        this.loginButton.click();
    }

    @Step("Проверка: не заданы логин и пароль")
    public void isLoginRequired() {
        this.errorMessage.shouldHave(text("Username and Password are required."));
    }

}