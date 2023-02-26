package api.client;

import api.user.User;
import api.user.UserData;
import api.user.UserLogin;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    private static final String AUTH_USER = "/api/auth/user";

    @Step("создание юзера через API")
    public ValidatableResponse create(User user) {
        return given().log().all()
                .spec(getSpec())
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("логин юзера API")
    public ValidatableResponse login(UserLogin userLogin) {
        return given().log().all()
                .spec(getSpec())
                .body(userLogin)
                .when()
                .post("/api/auth/login ")
                .then();

    }

    @Step("удаление юзера API")
    public ValidatableResponse delete(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(AUTH_USER)
                .then();
    }

    @Step("изменение данных юзера API")
    public ValidatableResponse changeData(UserData userData, String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .patch(AUTH_USER)
                .then();
    }

    @Step("получение данных юзера API")
    public ValidatableResponse getUserData(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(AUTH_USER)
                .then();
    }
}
