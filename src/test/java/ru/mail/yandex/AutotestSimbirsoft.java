package ru.mail.yandex;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AutotestSimbirsoft {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static String path;

    @Before
    public void setUp() throws IOException {
        // Инициализация Webdriver, необходимо выбрать свою платформу, браузер
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(Platform.LINUX);
        capabilities.setBrowserName("firefox");
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        wait =new WebDriverWait(driver,10);
    }
    @Test
    public void sendMail() {
        // Открываем страницу почты
        driver.get("https://mail.yandex.ru");
        // Нажимаем на кнопку "Войти", переход к форме авторизации
        WebElement enter =  driver.findElement(By.cssSelector(".button2_theme_mail-white"));
        enter.click();
        // Заполняем логин
        WebElement loginfield=  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#passp-field-login")));
        loginfield.sendKeys("Yourmail@yandex.ru"+ Keys.ENTER);
        // Заполняем пароль
        WebElement passfield = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#passp-field-passwd")));
        passfield.sendKeys("Yourpassword"+ Keys.ENTER);
        // Дожидаемся появления кнопки "Отправить"
        WebElement sendbutton=   wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".mail-ComposeButton-Text")));
        // Считываем количество писем с заданным текстом
        List<WebElement> mails=  driver.findElements(By.partialLinkText("Simbirsoft Тестовое задание"));
        // Нажимаем конопку "Отправить"
        sendbutton.click();
        // Вводим адрес получателя
        WebElement adres=  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ComposeRecipients-ToField > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)")));
        adres.sendKeys("Yourmail@yandex.ru");
        // Вводим тему письма
        WebElement nameofmessage=  driver.findElement(By.cssSelector(".composeTextField"));
        nameofmessage.click();
        nameofmessage.sendKeys("«Simbirsoft Тестовое задание.<YourFirstТame>»");
        // Вводим текст письма с количеством писем
        String text=String.format("Количество писем %d",mails.size());
        WebElement bodyofmessage=  driver.findElement(By.cssSelector(".cke_wysiwyg_div"));
        bodyofmessage.sendKeys(text);
        // Нажимаем на кнопку "Отправить" в форме заполнения письма
        WebElement sendmtfck=  driver.findElement(By.cssSelector(".ComposeControlPanelButton-Button_action"));
        sendmtfck.click();

    }
}
