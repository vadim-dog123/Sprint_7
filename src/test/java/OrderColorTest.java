import api.model.OrderModel;
import api.steps.OrderSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@DisplayName("Создание заказа")
@RunWith(Parameterized.class)
public class OrderColorTest {
    private final String[] color;
    private final OrderSteps orderSteps = new OrderSteps();
    private final OrderModel orderModel = new OrderModel(
            "firstName",
            "lastName",
            "address",
            "metroStation",
            "phone",
            5,
            "2020-06-06",
            "comment",
            new String[]{"BLACK"});

    public OrderColorTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Mожно указать цвета BLACK или GREY")
    public static Object[][] getTestData() {
        final String[][][] objects = {{{"BLACK", "GREY"}}, {{"BLACK"}}, {{"GREY"}}, {{}},};
        return objects;
    }

    @Test
    public void createTwoIdenticalCouriersTest() {
        orderModel.setColor(color);
        ValidatableResponse validatableResponse = orderSteps.newOrder(orderModel);
        assertEquals("Статус код отличается от ожидаймого", validatableResponse.extract().statusCode(), 201);
        assertFalse("Track заказа отсутствует", orderSteps.getTrackOrder(validatableResponse).isEmpty());
    }
}
