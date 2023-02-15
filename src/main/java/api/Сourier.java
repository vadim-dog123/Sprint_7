package api;

import api.model.СourierModel;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Сourier extends PlaceholderRestClient {
    public ValidatableResponse postCreatingСourier(СourierModel json){

        return given()
                .spec(baseSpec())
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/")
                .then();
    }
        public ValidatableResponse postCourierLoginInSystem(СourierModel json){
            //  File json = new File("src/test/resources/test.json");
            return given()
                    .spec(baseSpec())
                    .header("Content-type", "application/json")
                    .and()
                    .body(json)
                    .when()
                    .post("/api/v1/courier/login")
                    .then();
        }
    public ValidatableResponse deleteCourier(String id){
        return given()
                .spec(baseSpec())
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("/api/v1/courier/"+id)
                .then();
    }



}
