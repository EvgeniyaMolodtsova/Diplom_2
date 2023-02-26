package api.user;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserGeneration {

    public String getRandom() {
        int length = 7;
        boolean useLetters = true;
        boolean useNumbers = false;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    @Step("создание дефолтного юзера")
    public User getDefault() {
        String email = getRandom() + "@yandex.ru";
        String password = getRandom();
        String name = getRandom();
        return new User(email, password, name);
    }

    @Step("создание юзера без email")
    public User getDefaultWithOutEmail() {
        String password = getRandom();
        String name = getRandom();
        return new User(null, password, name);
    }

    @Step("создание юзера без пароля")
    public User getDefaultWithOutPassword() {
        String email = getRandom() + "yandex.ru";
        String name = getRandom();
        return new User(email, null, name);
    }

    @Step("создание юзера без имени")
    public User getDefaultWithOutName() {
        String email = getRandom() + "yandex.ru";
        String password = getRandom();
        return new User(email, password, null);
    }
}
