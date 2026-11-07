import com.codeborne.selenide.*;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;


@ExtendWith(SoftAssertsExtension.class)
public class DZ4 {

    //для 35 слайда
    public static void clickWithCtrl (SelenideElement element){
        Actions actions = new Actions(getWebDriver());
        try{
            actions.keyDown(Keys.CONTROL).build().perform();
            element.click();
        } finally {
            actions.keyUp(Keys.CONTROL).build().perform();
        }

    }
    //clickWithCtrl($("#someLinkId"));



    @Test
    @DisplayName("Модуль 4-8")
    void test01() {
        open("https://slqamsk.github.io/cases/sltrack/v02");
        $x("//h2[contains(., 'SL-Track — Вход в систему управления задачами')]").shouldBe(visible);
        $x("//label[contains(., 'Email:')]").shouldBe(visible);
        $x("//label[contains(., 'Пароль:')]").shouldBe(visible);
        $("#username").shouldHave(attribute("placeholder", "user@example.com"));
        $("#password").shouldHave(attribute("placeholder", "••••••••"));
        sleep (3_000);
        $("#username").setValue("admin@example.com");
        $("#password").setValue("admin123");
        sleep (3_000);
        $x("//button[contains(., 'Войти')]").click();
        $x("//h2[contains(., 'Добро пожаловать в систему управления задачами SL-Track!')]").shouldBe(visible);
        $("#user-name").shouldHave(text("Пётр Иванов"));
        $("#user-role").shouldHave(text("Администратор"));
        $("#logout-btn").shouldBe(visible);
    }

    @Test
    @DisplayName("Модуль 4-8_*")
    void test02() {
        Configuration.timeout = 6_000;
        open("https://slqamsk.github.io/cases/sltrack/v02");
        $("#username").setValue("admin@example.com");
        $("#password").setValue("admin123");
        sleep (3_000);
        $x("//button[contains(., 'Войти')]").click();
        sleep (3_000);
        $("#my-tasks-btn").click();
        sleep (3_000);
        $x("//*[contains(@class, 'task-assignee')][not(contains(., 'Пётр Иванов'))]").shouldNot(Condition.exist);
    }

    @Test
    @DisplayName("Модуль 4-8_**")
    void test03() {
        Configuration.assertionMode = AssertionMode.SOFT;
        open("https://slqamsk.github.io/cases/sltrack/v03");
        $x("//h2[contains(., 'SL-Track — Вход в систему управления задачами')]").shouldBe(visible);
        $x("//label[contains(., 'Email:')]").shouldBe(visible);
        $x("//label[contains(., 'Пароль:')]").shouldBe(visible);
        $("#username").shouldHave(attribute("placeholder", "user@example.com"));
        $("#password").shouldHave(attribute("placeholder", "••••••••"));
        sleep (3_000);
        $("#username").setValue("admin@example.com");
        $("#password").setValue("admin123");
        sleep (3_000);
        $x("//button[contains(., 'Войти')]").click();
        $x("//h2[contains(., 'Добро пожаловать в систему управления задачами SL-Track!')]").shouldBe(visible);
        $("#user-name").shouldHave(text("Пётр Иванов"));
        $("#user-role").shouldHave(text("Администратор"));
        $("#logout-btn").shouldBe(visible);
        sleep (3_000);
        $("#my-tasks-btn").click();
        sleep (3_000);
        $x("//*[contains(@class, 'task-assignee')][not(contains(., 'Пётр Иванов'))]").shouldNot(Condition.exist);
    }

    @Test
    @DisplayName("Модуль 4-13")
    void test04() {
        open("https://demoqa.com/frames");
        switchTo().frame("frame1");
        $("h1").shouldHave(Condition.text("This is a sample page"));

    }

    @Test
    @DisplayName("Модуль 4-20")
    void test05() {
        Configuration.timeout = 6_000;
        open("https://demoqa.com/alerts");

        $("#alertButton").click();
        Alert alert = switchTo().alert();
        sleep(2_000);
        alert.accept();

        $("#timerAlertButton").click();
        sleep(2_000);
        alert = switchTo().alert();
        alert.accept();

        $("#confirmButton").click();
        sleep(2_000);
        alert = switchTo().alert();
        alert.accept();

        $("#confirmButton").click();
        sleep(2_000);
        alert = switchTo().alert();
        alert.dismiss();

        $("#promtButton").click();
        sleep(2_000);
        alert = switchTo().alert();
        alert.sendKeys("testing");
        sleep(2_000);
        alert.accept();

        $("#promtButton").click();
        sleep(2_000);
        alert = switchTo().alert();
        alert.sendKeys("testing");
        sleep(2_000);
        alert.dismiss();

    }

    @Test
    @DisplayName("Модуль 4-25")
    void test06() {
        Configuration.timeout = 6_000;
        open("https://demoqa.com/modal-dialogs");
        $(".modal").should(disappear);
        sleep(2_000);
        $("#showSmallModal").click();
        sleep(2_000);
        $(".modal").shouldBe(visible);
        $("#closeSmallModal").shouldBe(visible).click();
        $(".modal").should(disappear);

    }

    @Test
    @DisplayName("Модуль 4-25_*")
    void test07() {
        open("https://demoqa.com/modal-dialogs");
        $(".modal").should(disappear);
        sleep(2_000);
        $("#showSmallModal").click();
        sleep(2_000);
        $x("//a[contains(@href, 'https://demoqa.com')]").shouldBe(visible).click();
    }



    @Test
    @DisplayName("Модуль 4-39")

    void test08() {
        open("https://ru.wikipedia.org/wiki/Selenium");
        int i = 0;
        for (SelenideElement se : $$("a").filter(clickable).filter(visible).filter(enabled)) {
            i++;
            System.out.println(i + ". " + se.getText() + ", " + se.getDomAttribute("href") + ", размер: " + se.getSize());
            //Задача конечно сложновата - вот этот момент нужно на опыте было решать явно.
            if ((se.getSize().height <=1) || (se.getSize().width <= 1)) {
                System.out.println("Элемент слишком маленький, по нему невозможно кликнуть.");
                continue;
            }
            clickWithCtrl(se);
            switchTo().window(1);
            //Вот тут не совсем понятно зачем проверка нужна была на наличе тела вновь открытой страницы
            $("body").shouldBe(exist);
            // а вот этот момент мы вообще не разбирали, как я понял
            String str_url = WebDriverRunner.driver().url();
            try {
                URL url = new URL(str_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int code = connection.getResponseCode();
                System.out.println(str_url + " - " + code);
            } catch (Exception e) {
                System.out.println(str_url + " - некорректная ссылка");
            }
            closeWindow();
            switchTo().window(0);
        }
        }

    @Test
    @DisplayName("Модуль 4-43")
    void test09() {
        open("https://www.specialist.ru/");
        sleep(2_000);
        $("#cookieConsent__ok").click();
        sleep(2_000);
        $x("//a[contains(@href, '/learning-formats')]").shouldBe(visible).click();
        sleep(2_000);
        $x("//a[contains(@href, '/free-learning-new')]").shouldBe(visible).click();
        sleep(2_000);
        $x("//a[contains(@href, '#selectCourse')]").shouldBe(visible).click();
        $("#Filter_CategoriesDirectionFilter").selectOption("Программирование");
        $("#sendBtn").click();
        $x("//a[contains(., 'Тестирование ПО') and contains(., 'Тестировщик программного обеспечения')]").scrollTo();

    }

    @Test
    @DisplayName("Модуль 4-43_*")
    void test10() {
        open("https://www.specialist.ru/");
        sleep(180_000);
        // не дождался появления онлайн-чата - "На сайте https://www.specialist.ru/ дождитесь появления онлайн-чата и нажмите на крестик, чтобы его свернуть."
    }

    @Test
    @DisplayName("Модуль 4-43_*_*")
    void test11() {
        Configuration.pageLoadStrategy = "eager";
        LocalDate today = LocalDate.now();
        LocalDate departureDate = today.plusMonths(1);
        LocalDate returnDate = departureDate.plusWeeks(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String departureIso = departureDate.format(formatter);
        String returnIso = returnDate.format(formatter);
        open("https://www.aviasales.ru/");
//        sleep(2_000);
        $x(".//input[@data-test-id='origin-input']").shouldBe(enabled).clear();
        $x(".//input[@data-test-id='origin-input']").setValue("Москва");
        $x(".//input[@data-test-id='destination-input']").setValue("Питер");
        $x(".//section[@data-test-id='autocomplete-menu']//span[contains(., 'Санкт-Петербург')]").shouldBe(Condition.visible).click();
        $x(".//input[@data-test-id='destination-input']").shouldHave(Condition.value("Санкт-Петербург"));
        $x(".//button[@data-test-id='start-date-field']").shouldBe(Condition.visible).click();
        $x(".//button[.//div[@data-test-id='date-" + departureIso + "']]").shouldBe(Condition.visible).click();
        $x(".//button[@data-test-id='end-date-field']").shouldBe(Condition.visible).click();
        $x(".//button[.//div[@data-test-id='date-" + returnIso + "']]").shouldBe(Condition.visible).click();
//        sleep(2_000);
        $x(".//button[@data-test-id='passengers-field']").shouldBe(Condition.visible).click();
        $x(".//div[@data-test-id='number-of-adults']//button[@data-test-id='increase-button']").click();
        $x(".//div[@data-test-id='number-of-children']//button[@data-test-id='increase-button']").click();
        $x(".//button[@data-test-id='form-submit']").click();
        sleep(2_000);
//        Selenide.webdriver().shouldHave(WebDriverConditions.numberOfWindows(2));
        switchTo().window(1);
        String str_url = WebDriverRunner.driver().url();
        System.out.println("Мы сейчас на странице: " + str_url);

        $x("//div[contains(@data-test-id, 'ticket-preview')]").shouldBe(Condition.exist, Duration.ofSeconds(15));
        String cheapestPrice = "";
        String recommendedPrice = "";


        SelenideElement recommendedBadge = $x("//*[@data-test-id='bdui-ticket-preview-badge-recommended']");
        if (recommendedBadge.exists()){
            SelenideElement recommendedCard = recommendedBadge.$x("./ancestor::div[contains(@data-test-id, 'ticket-preview')]");
            recommendedCard.scrollTo();

            recommendedPrice = recommendedCard.$x(".//*[contains(text(), '₽')]").shouldBe(Condition.visible)
                    .getText();
            System.out.println("Цена рекомендуемого рейса: " + recommendedPrice);
        } else {
            System.out.println("Плашка 'Рекомендуемый' отсутствует в текущей выдаче для бота.");
        }
        // bdui-ticket-preview-normal-fe7b726c1b880664111c27abf6a2692e
        // //span[contains(text(),'Самый дешёвый')]
        // //div[@data-test-id='bdui-ticket-preview-normal-fe7b726c1b880664111c27abf6a2692e']//div[@data-test-id='bdui-ticket-preview-prices']//span
        // //span[text()='Самый дешёвый']/ancestor::div[@data-test-id='bdui-ticket-preview-normal-5920dff7017fc69333a79d32b263f13f']//div[@data-test-id='bdui-ticket-preview-prices']//span
        //sleep(9999_000);
        SelenideElement se1 = $x("//span[text()='Самый дешёвый']");
        se1.shouldBe(exist);

        SelenideElement se2 = se1.$x("./ancestor::div[contains(@data-test-id,'bdui-ticket-preview-normal')]");
        se2.shouldBe(exist);


//        SelenideElement se2 = se1.$x("./ancestor::div[@data-test-id='bdui-ticket-preview-normal-5920dff7017fc69333a79d32b263f13f']");
//        se2.shouldBe(exist);
        SelenideElement se3 = se2.$x(".//div[@data-test-id='bdui-ticket-preview-prices']");
        se3.shouldBe(exist);
        SelenideElement se4 = se3.$x(".//span");
        se4.shouldBe(exist);
        System.out.println("Цена самого дешёвого рейса: " + se4.getText());

        SelenideElement cheapestPriceNew = $x("//span[text()='Самый дешёвый']/ancestor::div[contains(@data-test-id,'bdui-ticket-preview-normal')]//div[@data-test-id='bdui-ticket-preview-prices']//span");
        cheapestPriceNew.shouldBe(exist).shouldBe(visible);
        System.out.println("Цена самого дешёвого рейса: " + cheapestPriceNew.getText());

        SelenideElement cheapestBadge = $x("//*[@data-test-id='bdui-ticket-preview-badge-cheapest']");
        if (cheapestBadge.exists()){
            SelenideElement cheapestCard = cheapestBadge.$x("./ancestor::div[contains(@data-test-id,'ticket-preview')]");
            cheapestCard.scrollTo();
            sleep(9999_000);
            cheapestPrice = cheapestCard.$x(".//div[@data-test-id='bdui-ticket-preview-prices']//span[@data-test-id='text']").shouldBe(Condition.visible)
                    .getText();

            System.out.println("Цена самого дешёвого рейса: " + cheapestPrice);
        } else {
            System.out.println("Плашка 'Дешёвый' отсутствует в текущей выдаче для бота.");

            SelenideElement firstCard = $x("(//div[contains(@data-test-id, 'ticket-preview')])");
            firstCard.scrollTo();

            cheapestPrice = firstCard.$x(".//*[contains(text(), '₽')]")
                    .shouldBe(Condition.visible)
                    .getText();

            System.out.println("Цена самого дешёвого рейса (взят первый в списке): " + cheapestPrice);

        }

        sleep(40_000);

    }

    }
