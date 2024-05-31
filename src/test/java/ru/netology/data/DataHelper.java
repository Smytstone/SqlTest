package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    @Value
    public static class UserData {
        String login;
        String password;
    }

    public static UserData getUser() {
        return new UserData("vasya", "qwerty123");
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static UserData generateRandomUser() {
        return new UserData(getRandomLogin(), getRandomPassword());
    }

    @Value
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    public static class VerifyCode {
        String verifyCode;
    }

    public static VerifyCode generateRandomVerificationCode() {
        return new VerifyCode(faker.numerify("######"));
    }
}