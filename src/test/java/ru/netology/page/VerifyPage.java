package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class VerifyPage {
    private final SelenideElement codeInput = $("[data-test-id=code] input");
    private final SelenideElement codeInputEmptyNotification = $("[data-test-id=code] .input__sub");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");

    public void verifyPageVisibility() {
        codeInput.should(visible);
    }

    public void verifyErrorNotification() {
        errorNotification.shouldHave(exactText("Ошибка! \nНеверно указан код! Попробуйте ещё раз.")).shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }

    public void invalidVerify(String verificationCode) {
        verify(verificationCode);
        errorNotification.shouldHave(exactText("Ошибка! \nНеверно указан код! Попробуйте ещё раз.")).shouldBe(visible);
    }

    public void verify(String verificationCode) {
        codeInput.setValue(verificationCode);
        verifyButton.click();
    }

    public void emptyCode() {
        codeInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }

    public void errorNotification() {
        errorNotification.shouldHave(exactText("Ошибка! \nНеверно указан логин или пароль")).shouldBe(visible);
    }
}
