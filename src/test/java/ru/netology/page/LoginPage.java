package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginInput = $("[data-test-id=login] input");
    private final SelenideElement loginInputEmptyNotification = $("[data-test-id=login] .input__sub");
    private final SelenideElement passwordInput = $("[data-test-id=password] input");
    private final SelenideElement passwordInputEmptyNotification = $("[data-test-id=password] input__sub");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");

    public VerifyPage validLogin(DataHelper.UserData data) {
        loginInput.setValue(data.getLogin());
        passwordInput.setValue(data.getPassword());
        loginButton.click();
        return new VerifyPage();
    }

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public void emptyLogin() {
        loginInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }

    public void emptyPassword() {
        passwordInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }
}