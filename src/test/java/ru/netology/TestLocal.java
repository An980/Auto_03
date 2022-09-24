package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class TestLocal {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
//        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
//        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearsDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void validData() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иван Иванов Иванович");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    //=========================================Name tests=========================================//

    @Test
    void dashName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иван Иванов-Петров Иванович");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    @Test
    void shortName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Барталамей Уй");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    @Test
    void longName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Аийильцикликирмицибайрактазийанкаграманоглу Аглы Харитон");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    @Test
    void otherLanguageName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("John Vick");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test
    void symbolsName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("1238*!^ 812hjak");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test
    void emptyName() {
        driver.get("http://localhost:9999/");

        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79211234567");

        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        elements = driver.findElements(By.className("input__sub"));

        String text = elements.get(0).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());

    }

    //=========================================Phone tests=========================================//

    @Test
    void phoneWithOutPlus() {
        driver.get("http://localhost:9999/");

        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иван Иванов Иванович");
        elements.get(1).sendKeys("79211234567");

        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        elements = driver.findElements(By.className("input__sub"));

        String text = elements.get(1).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void phoneWith8() {
        driver.get("http://localhost:9999/");

        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иван Иванов Иванович");
        elements.get(1).sendKeys("89211234567");

        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        elements = driver.findElements(By.className("input__sub"));

        String text = elements.get(1).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void emptyPhone() {
        driver.get("http://localhost:9999/");

        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иван Иванов Иванович");
        elements.get(1).sendKeys("");

        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.className("button")).click();
        elements = driver.findElements(By.className("input__sub"));

        String text = elements.get(1).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());

    }

    //=========================================Check-box tests=========================================//

    @Test
    void checkBox() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иван Иванов-Петров Иванович");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79211234567");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getText();
        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());

    }
}


