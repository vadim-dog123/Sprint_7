package api.steps;

import api.Orders;
import api.model.OrderModel;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderSteps {

    @Step("API Создания нового заказа")
    public ValidatableResponse newOrder(OrderModel order) {
        return new Orders().postCreatingOrder(order);
    }

    @Step("Определение track")
    public String getTrackOrder(ValidatableResponse validatableResponse) {
        return validatableResponse.extract().jsonPath().get("track").toString();
    }

    @Step("API Получение списка всех заказов")
    public ValidatableResponse getOllOrders() {
        return new Orders().GetListOrders();
    }
}
