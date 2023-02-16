import api.model.СourierModel;
import api.steps.CourierSteps;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@DisplayName("Логин курьера")
public class CourierLoginTest {
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
    @DisplayName("Kурьер может авторизоваться")
    public void createСourierTest() {
        Allure.step("Проверка, курьер создан");
        assertEquals("Статус код отличается от ожидаймого", courierSteps.courierLoginInSystem(randomCourier).extract().statusCode(), 200);
    }

    @Test
    @DisplayName("Ошибка при неправильном логине")
    public void errorWithIncorrectLoginTest() {
        String login = randomCourier.getPassword();
        randomCourier.setPassword("errorLogin");
        Allure.step("Проверка кода ответа при неправильном логине");
        assertEquals("Статус код отличается от ожидаймого", courierSteps.courierLoginInSystem(randomCourier).extract().statusCode(), 404);
        randomCourier.setPassword(login);
    }

    @Test
    @DisplayName("Ошибка при неправильном пароле")
    public void errorWithIncorrectPasswordTest() {
        String password = randomCourier.getPassword();
        randomCourier.setPassword("errorPassword");
        Allure.step("Проверка кода ответа при неправильном пароле");
        assertEquals("Статус код отличается от ожидаймого", courierSteps.courierLoginInSystem(randomCourier).extract().statusCode(), 404);
        randomCourier.setPassword(password);
    }

    @Test
    @DisplayName("Если нет пароля, запрос возвращает ошибку")
    public void NoPasswordTest() {
        String password = randomCourier.getPassword();
        randomCourier.setPassword(null);
        Allure.step("Проверка кода ответа при отсутствии пароля");
        assertEquals("Статус код отличается от ожидаймого", courierSteps.courierLoginInSystem(randomCourier).extract().statusCode(), 400);
        randomCourier.setPassword(password);
    }

    @Test
    @DisplayName("Если нет логина, запрос возвращает ошибку")
    public void NoLoginTest() {
        String login = randomCourier.getLogin();
        randomCourier.setLogin(null);
        Allure.step("Проверка кода ответа при отсутствии логина");
        assertEquals("Статус код отличается от ожидаймого", courierSteps.courierLoginInSystem(randomCourier).extract().statusCode(), 400);
        randomCourier.setLogin(login);
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void defunctUserTest() {
        String login = randomCourier.getLogin();
        randomCourier.setLogin(null);
        Allure.step("Проверка кода ответа при несуществующем логине");
        assertEquals("Статус код отличается от ожидаймого", courierSteps.courierLoginInSystem(randomCourier).extract().statusCode(), 400);
        randomCourier.setLogin(login);
    }

    @Test
    @DisplayName("Успешный запрос возвращает id")
    public void successfulRequestReturnsIdTest() {
        Allure.step("Проверка наличия ID в ответе сервера");
        assertFalse("ID курьера отсутствует", courierSteps.getIdCourier(randomCourier).isEmpty());
    }
}
