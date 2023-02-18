package api;

import api.model.OrderModel;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Orders extends PlaceholderRestClient {
    public ValidatableResponse postCreatingOrder(OrderModel json) {

        return given()
                .spec(baseSpec())
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    public ValidatableResponse GetListOrders() {

        return given()
                .spec(baseSpec())
                .get("/api/v1/orders")
                .then();
    }
}
