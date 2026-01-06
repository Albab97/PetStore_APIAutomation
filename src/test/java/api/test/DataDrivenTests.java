package api.test;

import api.utilities.DataProviders;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.payload.*;

import static api.endpoints.UserEndpoints.createUser;
import static api.endpoints.UserEndpoints.deleteUser;

public class DataDrivenTests {
    // We are going to first create multiple users by using Data provider(name ="Data") and
    // then we will delete all those users by getting only the usernames from data provider(name="UserNames")
    @Test(priority = 1, dataProvider = "Data",dataProviderClass = DataProviders.class)  // we need to provide the dataProvider class since it is in a different package/class.
    public void testPostUser(String userID, String userName, String fname, String lname, String useremail, String pwd, String ph)
    {
        User userPayload= new User();

        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(fname);
        userPayload.setLastName(lname);
        userPayload.setEmail(useremail);
        userPayload.setPassword(pwd);
        userPayload.setPhone(ph);

        Response response = createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(),200);
    }
    @Test(priority = 2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
    public void testDeleteUsersByUsername(String username){
        Response response = deleteUser(username);
        Assert.assertEquals(response.getStatusCode(),200);
    }

}
