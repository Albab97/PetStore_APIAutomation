package api.test;

import api.endpoints.UserEndpoints;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import api.payload.*;
import org.testng.annotations.Test;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import static api.endpoints.UserEndpoints.*;


public class UserTests {
    Faker faker;
    User userPayload;
    public Logger logger;  // for logs

    @BeforeClass
    public void setup(){
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        //For Logs
        logger = LogManager.getLogManager().getLogger(this.getClass().toString());
    }

    @Test(priority = 1)
    public void testPostUser(){
        logger.info("********* Creating user ************");
        Response response = createUser(userPayload);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********* User created ************");
    }
    @Test(priority = 2)
    public void testGetUserByUsername(){
        logger.info("********* Reading user info ************");
        Response response;
        response = readUser(userPayload.getUsername());
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********* User info is displayed ************");
    }
    @Test(priority = 3)
    public void testUpdateUser(){
        logger.info("********* Updating user info ************");
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

        logger.info("********* User is updated ************");
    }
    @Test(priority = 4)
    public void testDeleteUserByUsername(){
        logger.info("********* Deleting user ************");
        Response response = deleteUser(userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********* User is deleted ************");
    }
}
