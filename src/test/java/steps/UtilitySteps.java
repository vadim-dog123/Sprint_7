package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.assertEquals;

public class UtilitySteps {
    @Step("Проверка статус кода")
    public void testStatusCode(ValidatableResponse validatableResponse, int statusCode) {
        assertEquals("Статус код отличается от ожидаймого", validatableResponse.extract().statusCode(), statusCode);
    }

    @Step("Проверка ответа на запрос")
    public void testResponseRequest(ValidatableResponse validatableResponse, String jsonPath, String expectat) {
        assertEquals("Ответ не соответствует ожидаймому", expectat, validatableResponse.extract().jsonPath().get(jsonPath).toString());
    }

}
