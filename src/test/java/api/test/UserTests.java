package api.test;

import api.endpoints.UserEndpoints;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import api.payload.*;
import org.testng.annotations.Test;

import static api.endpoints.UserEndpoints.*;


public class UserTests {
    Faker faker;
    User userPayload;

    @BeforeClass
    public void setupData(){
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
    }

    @Test(priority = 1)
    public void testPostUser(){
        Response response = createUser(userPayload);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(),200);
    }
    @Test(priority = 2)
    public void testGetUserByUsername(){
        Response response;
        response = readUser(userPayload.getUsername());
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(),200);
    }
    @Test(priority = 3)
    public void testUpdateUser(){
        //update data using same user payload.
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = updateUser(userPayload.getUsername(),userPayload);
        response.then().log().body();

        Assert.assertEquals(response.statusCode(),200);

        //Checking data after update
        Response responseAfterUpdate = readUser(userPayload.getUsername());
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
    }
    @Test(priority = 4)
    public void testDeleteUserByUsername(){
        Response response = deleteUser(userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
