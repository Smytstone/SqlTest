package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginInput = $("[data-test-id=login] input");
    private final SelenideElement passwordInput = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");


    public VerifyPage validLogin(DataHelper.UserData data) {
        loginInput.setValue(data.getLogin());
        passwordInput.setValue(data.getPassword());
        loginButton.click();
        return new VerifyPage();
    }

    public VerifyPage invalidLogin(DataHelper.UserData data) {
        loginInput.setValue(data.getLogin());
        passwordInput.setValue(DataHelper.getRandomPassword());
        loginButton.click();
        return new VerifyPage();
    }

    public void blocked() {
        errorNotification.shouldHave(exactText("Ошибка! \nПользователь заблокирован")).shouldBe(visible);
    }
}