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
        loginPage = open("http//localhost:9999", LoginPage.class);
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
}
