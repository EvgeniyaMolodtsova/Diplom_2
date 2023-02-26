package api.client;

import api.order.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    private static final String URI = "/api/orders";

    @Step("создание заказа POST /api/orders")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(URI)
                .then();
    }

    @Step("получение данных заказа GET /api/orders")
    public ValidatableResponse getOrder(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(URI)
                .then();
    }
}
