package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    public ValidatableResponse create(User user) {
        return given().log().all()
                .spec(getSpec())
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }

    public ValidatableResponse login(UserLogin userLogin) {
        return given().log().all()
                .spec(getSpec())
                .body(userLogin)
                .when()
                .post("/api/auth/login ")
                .then();

    }

    public ValidatableResponse delete(String accessToken){
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user")
                .then();
    }

    public ValidatableResponse changeData(UserData userData, String accessToken){
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .patch("/api/auth/user")
                .then();
    }

    public ValidatableResponse getOrderData(String accessToken){
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get("/api/auth/user")
                .then();
    }
}
