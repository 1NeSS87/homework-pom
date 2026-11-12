package pages;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;


public class ResultAviasalesPage {

    @Step("Получить цену по названию блока")
    public void printPrice(String nameBlock) {
        System.out.println("Цена из блока " + nameBlock + " = " + $x("//span[text()='" + nameBlock + "']/ancestor::*[contains(@data-test-id, 'bdui-ticket-preview-normal-')]/div[2]//span")
                .shouldBe(visible, Duration.ofSeconds(20))
                .text());
    }
}