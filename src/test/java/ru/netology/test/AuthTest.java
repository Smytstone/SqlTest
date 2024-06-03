package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.*;


public class AuthTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterEach
    void setDown() {
        cleanAuthCodes();
    }

    @AfterAll
    public static void setDownClass() {
        cleanDatabase();
    }

    @Test
    public void shouldAuth() {
        var authInfo = DataHelper.getUser();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldNoAuthWithOldVerifyCode() {
        var authInfo = DataHelper.getUser();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);

        open("http://localhost:9999/");
        authInfo = DataHelper.getUser();
        verificationPage = loginPage.validLogin(authInfo);
        verificationCode = SQLHelper.getVerificationCode();
        verificationPage.invalidVerify(verificationCode);
        verificationPage.verifyErrorNotification();
    }

    @Test
    public void shouldNotAuthWithRandomVerifyCode() {
        var authInfo = DataHelper.getUser();
        var verificationPage = loginPage.validLogin(authInfo);
        var randomCode = DataHelper.generateRandomVerificationCode();
        verificationPage.invalidVerify(String.valueOf(randomCode));
    }

    @Test
    public void shouldNotAuthRandomUser() {
        var authInfo = DataHelper.generateRandomUser();
        var logPage = loginPage.validLogin(authInfo);
        logPage.errorNotification();
    }

    @Test
    public void shouldBlockUserAfterThreeAttemptsOfInputInvalidPassword() {
        var authInfo = DataHelper.getUser();
        var logPage = loginPage.invalidLogin(authInfo);
        logPage.errorNotification();

        open("http://localhost:9999/");
        authInfo = DataHelper.getUser();
        logPage = loginPage.invalidLogin(authInfo);
        logPage.errorNotification();

        open("http://localhost:9999/");
        authInfo = DataHelper.getUser();
        logPage = loginPage.invalidLogin(authInfo);
        logPage.errorNotification();

        open("http://localhost:9999/");
        loginPage.validLogin(authInfo);
        loginPage.blocked();
    }

    @Test
    public void shouldNotificationWithEmptyVerifyCode() {
        var authInfo = DataHelper.getUser();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verify(null);
        verificationPage.emptyCode();
    }
}
