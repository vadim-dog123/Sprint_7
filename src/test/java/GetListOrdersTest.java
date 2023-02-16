import api.steps.OrderSteps;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

@DisplayName("Список заказов. В теле ответа возвращается список заказов")
public class GetListOrdersTest {
    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("В теле ответа есть список заказов")
    public void createTwoIdenticalCouriersTest() {
        var order = orderSteps.getOllOrders();
        List a = order.extract().jsonPath().get("orders");
        Allure.step("Проверка наличия списка заказов в теле ответа");
        assertFalse("Списка заказов нету", a.isEmpty());
    }
}
