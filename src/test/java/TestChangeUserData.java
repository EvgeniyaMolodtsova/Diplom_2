import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserData;
import org.example.UserGeneration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestChangeUserData {

    private final UserGeneration userGeneration = new UserGeneration();
    private String token;
    private final UserClient userClient = new UserClient();
    private UserData userData;
    String expectName = "testName";
    String expectEmail = "nfjeoiw@yandex.ru";
    String expectPassword = "eudjs789";


    @Before
    public void setUp() {
        User user = userGeneration.getDefault();
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
    @DisplayName("изменение email")
    public void changeEmail(){
        userData = UserData.from(expectEmail, null, null);

        ValidatableResponse changeEmail = userClient.changeData(userData, token);
        ValidatableResponse getData = userClient.getOrderData(token);

        String actualEmail = getData.extract().path("user.email");

        Assert.assertEquals(200, changeEmail.extract().statusCode());
        Assert.assertEquals(expectEmail, actualEmail);
    }

    @Test
    @DisplayName("изменение пароля")
    public void changePassword(){
        userData = UserData.from(null, expectPassword, null);

        ValidatableResponse changePassword = userClient.changeData(userData, token);

        Assert.assertEquals(200, changePassword.extract().statusCode());
    }

    @Test
    @DisplayName("изменение имени")
    public void changeName(){
        userData = UserData.from(null, null, expectName);

        ValidatableResponse changeName = userClient.changeData(userData, token);
        ValidatableResponse getData = userClient.getOrderData(token);

        String actualName = getData.extract().path("user.name");

        Assert.assertEquals(200, changeName.extract().statusCode());
        Assert.assertEquals(expectName, actualName);
    }

    @Test
    @DisplayName("изменение всех данных")
    public void changeAllData(){
        userData = UserData.from(expectEmail, expectPassword, expectName);

        ValidatableResponse changeName = userClient.changeData(userData, token);
        ValidatableResponse getData = userClient.getOrderData(token);

        String actualEmail = getData.extract().path("user.email");
        String actualName = getData.extract().path("user.name");

        Assert.assertEquals(200, changeName.extract().statusCode());
        Assert.assertEquals(expectEmail, actualEmail);
        Assert.assertEquals(expectName, actualName);
    }

    @Test
    @DisplayName("нельзя поменять email на такой же, который уже используется другим пользователем")
    public void changeToExistingEmail(){
        ValidatableResponse createSecondUser = userClient.create(userGeneration.getDefault());
        String secondToken = createSecondUser.extract().path("accessToken");
        ValidatableResponse getData = userClient.getOrderData(secondToken);

        String equalEmail = getData.extract().path("user.email");

        userData = UserData.from(equalEmail, null, null);

        ValidatableResponse changeEmail = userClient.changeData(userData, token);

        userClient.delete(secondToken);

        Assert.assertEquals(403, changeEmail.extract().statusCode());
    }

    @Test
    @DisplayName("нельзя изменить данные без авторизации")
    public void changeDataWithOutAuthorization(){
        userData = UserData.from(expectEmail, expectPassword, expectName);
        ValidatableResponse changeData = userClient.changeData(userData, "");

        Assert.assertEquals(401, changeData.extract().statusCode());
    }
}
