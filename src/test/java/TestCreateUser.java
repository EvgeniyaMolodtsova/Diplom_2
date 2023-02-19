import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.ThreadSleep;
import org.example.User;
import org.example.UserClient;
import org.example.UserGeneration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCreateUser {
    private final UserGeneration userGeneration = new UserGeneration();
    private String token;
    private final UserClient userClient = new UserClient();

    @Before
    public void waiting() throws InterruptedException {
        ThreadSleep.run();
    }

    @After
    public void cleanUp(){
        if(token != null){
            userClient.delete(token);
        }
    }

    @Test
    @DisplayName("создание пользователя")
    public void userCreate(){
        User user = userGeneration.getDefault();
        ValidatableResponse createUser = userClient.create(user);

        int statusCode = createUser.extract().statusCode();

        if(statusCode == 200){
            token = createUser.extract().path("accessToken");
        }

        Assert.assertEquals(200, statusCode);
    }

    @Test
    @DisplayName("нельзя создать двух одинаковых пользователей")
    public void equalUser(){
        User user = userGeneration.getDefault();
        ValidatableResponse createUser = userClient.create(user);
        ValidatableResponse createEqualUser = userClient.create(user);

        int statusCode = createUser.extract().statusCode();

        if(statusCode == 200){
            token = createUser.extract().path("accessToken");
        }

        Assert.assertEquals(403, createEqualUser.extract().statusCode());
    }

    @Test
    @DisplayName("нельзя создать пользователя биз email")
    public void userCreateWithOutEmail(){
        User user = userGeneration.getDefaultWithOutEmail();
        ValidatableResponse createUser = userClient.create(user);

        int statusCode = createUser.extract().statusCode();

        if(statusCode == 200){
            token = createUser.extract().path("accessToken");
        }

        Assert.assertEquals(403, statusCode);
    }

    @Test
    @DisplayName("нельзя создать пользователя без пароля")
    public void userCreateWithOutPassword(){
        User user = userGeneration.getDefaultWithOutPassword();
        ValidatableResponse createUser = userClient.create(user);

        int statusCode = createUser.extract().statusCode();

        if(statusCode == 200){
            token = createUser.extract().path("accessToken");
        }

        Assert.assertEquals(403, statusCode);
    }

    @Test
    @DisplayName("нельзя создать пользователя без имени")
    public void userCreateWithOutName(){
        User user = userGeneration.getDefaultWithOutName();
        ValidatableResponse createUser = userClient.create(user);

        int statusCode = createUser.extract().statusCode();

        if(statusCode == 200){
            token = createUser.extract().path("accessToken");
        }

        Assert.assertEquals(403, statusCode);
    }
}
