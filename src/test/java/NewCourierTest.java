import api.model.СourierModel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;
import steps.UtilitySteps;

@DisplayName("Создание курьера")
public class NewCourierTest {
    UtilitySteps utilitySteps = new UtilitySteps();
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
    @DisplayName("Kурьера можно создать")
    public void createСourierTest() {
        utilitySteps.testStatusCode(validatableResponse, 201);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void createTwoIdenticalCouriersTest() {
        var creating = courierSteps.newCourier(randomCourier);
        utilitySteps.testStatusCode(creating, 409);
    }

    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    public void successfulRequestReturnsOkTrueTest() {
        utilitySteps.testResponseRequest(validatableResponse, "ok", "true");
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void repeatedCourierLoginTest() {
        randomCourier.setFirstName("Test8787878");
        validatableResponse = courierSteps.newCourier(randomCourier);
        utilitySteps.testStatusCode(validatableResponse, 409);
    }

    @Test
    @DisplayName("Пользователь без логина")
    public void userWithoutLoginTest() {
        CourierSteps courierSteps = new CourierSteps();
        randomCourier.setLogin(null);
        validatableResponse = courierSteps.newCourier(randomCourier);
        utilitySteps.testStatusCode(validatableResponse, 400);
    }

    @Test
    @DisplayName("Пользователь без пароля")
    public void userWithoutPasswordTest() {
        CourierSteps courierSteps = new CourierSteps();
        randomCourier.setPassword(null);
        validatableResponse = courierSteps.newCourier(randomCourier);
        utilitySteps.testStatusCode(validatableResponse, 400);
    }

}
