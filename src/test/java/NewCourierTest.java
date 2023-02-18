import api.model.СourierModel;
import api.steps.CourierSteps;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@DisplayName("Создание курьера")
public class NewCourierTest {
    private final CourierSteps courierSteps = new CourierSteps();
    private ValidatableResponse validatableResponse;
    private СourierModel randomCourier;

    @Before
    public void setUp() {
        randomCourier = courierSteps.generatingRandomCourier();
        validatableResponse = courierSteps.newCourier(randomCourier);
    }

    @After
    public void tearDown() {
        courierSteps.deleteCourier(courierSteps.getIdCourier(randomCourier));
    }

    @Test
    @DisplayName("Kурьера можно создать")
    public void createСourierTest() {
        Allure.step("Проверка кода ответа при создании курьера");
        assertEquals("Статус код отличается от ожидаймого", validatableResponse.extract().statusCode(), 201);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void createTwoIdenticalCouriersTest() {
        var creating = courierSteps.newCourier(randomCourier);
        Allure.step("Проверка кода ответа при попытке создать двух одинаковых курьеров");
        assertEquals("Статус код отличается от ожидаймого", creating.extract().statusCode(), 409);
    }

    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    public void successfulRequestReturnsOkTrueTest() {
        Allure.step("Проверка отвта успешного запроса");
        assertEquals("Ответ не соответствует ожидаймому", validatableResponse.extract().jsonPath().get("ok").toString(), "true");
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void repeatedCourierLoginTest() {
        randomCourier.setFirstName("Test8787878");
        validatableResponse = courierSteps.newCourier(randomCourier);
        Allure.step("Проверка кода ответа при создании с существующем логином");
        assertEquals("Статус код отличается от ожидаймого", validatableResponse.extract().statusCode(), 409);
    }

    @Test
    @DisplayName("Пользователь без логина")
    public void userWithoutLoginTest() {
        CourierSteps courierSteps = new CourierSteps();
        randomCourier.setLogin(null);
        validatableResponse = courierSteps.newCourier(randomCourier);
        Allure.step("Проверка кода ответа при создании пользователя без логина");
        assertEquals("Статус код отличается от ожидаймого", validatableResponse.extract().statusCode(), 400);
    }

    @Test
    @DisplayName("Пользователь без пароля")
    public void userWithoutPasswordTest() {
        CourierSteps courierSteps = new CourierSteps();
        randomCourier.setPassword(null);
        validatableResponse = courierSteps.newCourier(randomCourier);
        Allure.step("Проверка кода ответа при создании пользователя без пароля");
        assertEquals("Статус код отличается от ожидаймого", validatableResponse.extract().statusCode(), 400);
    }
}
