package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post("/api/orders")
                .then();
    }

   public ValidatableResponse getOrder(String accessToken){
       return given().log().all()
               .spec(getSpec())
               .header("Authorization", accessToken)
               .when()
               .get("/api/orders")
               .then();
   }
}
