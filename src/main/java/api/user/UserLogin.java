package api.user;

import io.qameta.allure.Step;

public class UserLogin {
    private String email;
    private String password;

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Step("получение Email и пароля от созданого юзера")
    public static UserLogin from(User user) {
        return new UserLogin(user.getEmail(), user.getPassword());
    }

    @Step("создание собственного Email и пароля для логина")
    public static UserLogin create(String email, String password) {
        return new UserLogin(email, password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
