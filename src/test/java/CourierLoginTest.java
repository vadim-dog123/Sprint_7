import api.model.СourierModel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;
import steps.UtilitySteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@DisplayName("Логин курьера")
public class CourierLoginTest {
    /*
если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
успешный запрос возвращает id.
    */ UtilitySteps utilitySteps = new UtilitySteps();
    CourierSteps courierSteps = new CourierSteps();
    ValidatableResponse validatableResponse;
    СourierModel randomCourier;

    @Before
    public void setUp() {
        randomCourier = courierSteps.generatingRandomCourier();
        validatableResponse = courierSteps.newCourier(randomCourier);
    }

    @After
    public void tearDown() {
        utilitySteps.testStatusCode(courierSteps.deleteCourier(courierSteps.getIdCourier(randomCourier)), 200);
    }

    @Test
    @DisplayName("Kурьер может авторизоваться")
    public void createСourierTest() {
        utilitySteps.testStatusCode(courierSteps.courierLoginInSystem(randomCourier), 200);
    }

    @Test
    @DisplayName("Ошибка при неправильном логине")
    public void errorWithIncorrectLoginTest() {
        String login = randomCourier.getPassword();
        randomCourier.setPassword("errorLogin");
        utilitySteps.testStatusCode(courierSteps.courierLoginInSystem(randomCourier), 404);
        randomCourier.setPassword(login);
    }

    @Test
    @DisplayName("Ошибка при неправильном пароле")
    public void errorWithIncorrectPasswordTest() {
        String password = randomCourier.getPassword();
        randomCourier.setPassword("errorPassword");
        utilitySteps.testStatusCode(courierSteps.courierLoginInSystem(randomCourier), 404);
        randomCourier.setPassword(password);
    }

    @Test
    @DisplayName("Если нет пароля, запрос возвращает ошибку")
    public void NoPasswordTest() {
        String password = randomCourier.getPassword();
        randomCourier.setPassword(null);
        utilitySteps.testStatusCode(courierSteps.courierLoginInSystem(randomCourier), 400);
        randomCourier.setPassword(password);
    }

    @Test
    @DisplayName("Если нет логина, запрос возвращает ошибку")
    public void NoLoginTest() {
        String login = randomCourier.getLogin();
        randomCourier.setLogin(null);
        utilitySteps.testStatusCode(courierSteps.courierLoginInSystem(randomCourier), 400);
        randomCourier.setLogin(login);
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void defunctUserTest() {
        String login = randomCourier.getLogin();
        randomCourier.setLogin(null);
        utilitySteps.testStatusCode(courierSteps.courierLoginInSystem(randomCourier), 400);
        randomCourier.setLogin(login);
    }

    @Test
    @DisplayName("Успешный запрос возвращает id")
    public void successfulRequestReturnsIdTest() {
        assertFalse("ID курьера отсутствует", courierSteps.getIdCourier(randomCourier).isEmpty());
    }

}
