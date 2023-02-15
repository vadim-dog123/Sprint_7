import api.model.OrderModel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;
import steps.UtilitySteps;

import static org.junit.Assert.assertFalse;

@DisplayName("Создание заказа")
@RunWith(Parameterized.class)
public class OrderColorTest {
    private final String[] color;
    OrderSteps orderSteps = new OrderSteps();
    OrderModel orderModel = orderSteps.orderTemplate();
    UtilitySteps utilitySteps = new UtilitySteps();


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
        utilitySteps.testStatusCode(validatableResponse, 201);
        assertFalse("Track заказа отсутствует", orderSteps.getTrackOrder(validatableResponse).isEmpty());
    }
}
