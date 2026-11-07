import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class DZ {
        private void checkCommissionAndTotalFor (double amount) {
            double commission = amount * 0.01; // 1%

            if (commission < 10) {
                commission = 10;
            } else if (commission > 100) {
                commission = 100;
            }

            double total = amount + commission;

            $x("//input[@name='sum']").setValue(String.valueOf((double) amount));
            $x("//input[@name='submit']").click();

            sleep(3_000);

            double actualCommission = Double.parseDouble($(By.name("com")).getText());
            double actualTotal = Double.parseDouble($(By.name("total")).getText());

            Assertions.assertEquals(commission, actualCommission, "Комиссия для суммы" + amount + "посчитана неверно!");
            Assertions.assertEquals(total, actualTotal, "Общая сумма для суммы" + amount + " посчитана неверно!");
        }

        @Test
        @DisplayName("Задание 1 + 10 с использованием вышенаписанной функции")
        void test01() {
            open("https://slqa.ru/cases/fc/v01/index.php");
            checkCommissionAndTotalFor(100.0);
            checkCommissionAndTotalFor(2000.0);
        }

    @Test
    void test02() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").should(exist);
        $("#password").should(exist);
        $("#loginButton").should(exist);
        $("#username").setValue("locked_out_user");
        $("#password").setValue("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Пользователь заблокирован."));

    }

    @Test
    void test03() {
        open("https://slqa.ru/cases/xPathSimpleForm/");
        $x("//*[contains(., 'Воронеж') and contains(., 'нет поступлений')]").shouldBe(visible);

    }

}