package api.steps;

import api.model.СourierModel;
import api.Сourier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Random;

public class CourierSteps {
    private final Сourier сourier = new Сourier();
    private final СourierModel courierModel = new СourierModel();

    @Step("API создания нового курьера")
    public ValidatableResponse newCourier(СourierModel courier) {
        ValidatableResponse validatableResponse = сourier.postCreatingСourier(courier);
        return validatableResponse;
    }

    @Step("Генерация рандомного курьера")
    public СourierModel generatingRandomCourier() {
        courierModel.setLogin("login" + new Random().nextInt(10000000));
        courierModel.setPassword("pas");
        courierModel.setFirstName("name" + new Random().nextInt(10000000));
        System.out.println("Рандомный FirstName: " + courierModel.getFirstName());
        System.out.println("Рандомный Login: " + courierModel.getLogin());
        System.out.println("Рандомный Password: " + courierModel.getPassword());
        return courierModel;
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse courierLoginInSystem(СourierModel courier) {
        return сourier.postCourierLoginInSystem(courier);
    }

    @Step("ID Курьера")
    public String getIdCourier(СourierModel courier) {
        var validatableResponse = courierLoginInSystem(courier);
        System.out.println("ID Курьера: " + validatableResponse.extract().jsonPath().get("id").toString());
        return validatableResponse.extract().jsonPath().get("id").toString();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String id) {
        return сourier.deleteCourier(id);
    }
}
