package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGeneration {

    public String getRandom() {
        int length = 7;
        boolean useLetters = true;
        boolean useNumbers = false;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public User getDefault(){
        String email = getRandom() + "@yandex.ru";
        String password = getRandom();
        String name = getRandom();
        return new User(email, password, name);
    }

    public User getDefaultWithOutEmail(){
        String password = getRandom();
        String name = getRandom();
        return new User(null, password, name);
    }

    public User getDefaultWithOutPassword(){
        String email = getRandom() + "yandex.ru";
        String name = getRandom();
        return new User(email, null, name);
    }

    public User getDefaultWithOutName(){
        String email = getRandom() + "yandex.ru";
        String password = getRandom();
        return new User(email, password, null);
    }
}
