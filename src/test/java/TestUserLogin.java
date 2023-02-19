import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUserLogin {
    private final UserGeneration userGeneration = new UserGeneration();
    private String token;
    private final UserClient userClient = new UserClient();
    private User user;

    @Before
    public void setUp() throws InterruptedException {
        ThreadSleep.run();
        user = userGeneration.getDefault();
        ValidatableResponse createUser = userClient.create(user);
        token = createUser.extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        if (token != null) {
            userClient.delete(token);
        }
    }

    @Test
    @DisplayName("логин с валидными логином и паролем")
    public void userLogin(){
        ValidatableResponse login = userClient.login(UserLogin.from(user));

        Assert.assertEquals(200, login.extract().statusCode());
    }

    @Test
    @DisplayName("невозможен логин несуществующего пользователя")
    public void notExistingUser(){
        User notExisting = userGeneration.getDefault();

        ValidatableResponse login = userClient.login(UserLogin.from(notExisting));

        Assert.assertEquals(401, login.extract().statusCode());
    }

    @Test
    @DisplayName("невозможен логин с валидным email и неверным паролем")
    public void userLoginWithIncorrectPassword(){
        String email = user.getEmail();

        ValidatableResponse login = userClient.login(UserLogin.create(email, "eiu8whjk"));

        Assert.assertEquals(401, login.extract().statusCode());
    }
}
