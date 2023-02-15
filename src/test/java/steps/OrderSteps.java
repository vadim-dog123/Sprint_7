package steps;

import api.Orders;
import api.model.OrderModel;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderSteps {

    @Step("Шаблон заказа заказа")
    public OrderModel orderTemplate() {
        return new OrderModel(
                "firstName",
                "lastName",
                "address",
                "metroStation",
                "phone",
                5,
                "2020-06-06",
                "comment",
                new String[]{"BLACK"});
    }

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
