package api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import api.payload.*;

// UserEndpoints.java
// Created to perform Create,Read,Update,Delete request on the user API.
public class UserEndpoints {
    public static Response createUser(User payload){
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.post_url);
        return response;
    }
    public static Response readUser(String username){
        Response response = given()
                .pathParam("username",username)
                .accept(ContentType.JSON)
                .when()
                .get(Routes.get_url);
        return response;
    }
    public static Response updateUser(String username,User payload){
        Response response = given()
                .pathParam("username",username)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .put(Routes.update_url);
        return response;
    }
    public static Response deleteUser(String username){
        Response response = given()
                .pathParam("username",username)
                .accept(ContentType.JSON)
                .when()
                .delete(Routes.delete_url);
        return response;
    }
}
