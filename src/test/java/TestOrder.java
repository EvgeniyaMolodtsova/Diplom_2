import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestOrder {
    private final UserGeneration userGeneration = new UserGeneration();
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private String token;
    private final String[] ingredients;
    private final boolean needAccessToken;
    private final int expectedStatusCode;
    private final String expectedStatus;


    public TestOrder(String[] ingredients, boolean needAccessToken, int expectedStatusCode, String expectedStatus) {
        this.ingredients = ingredients;
        this.needAccessToken = needAccessToken;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedStatus = expectedStatus;
    }

    @Parameterized.Parameters
    public static Object[] getIngredientData() {
        return new Object[][] {
                // burger, needAccessToken, statusCode, status
                { Burgers.FIRST_BURGER, true, 200, "done" },
                { Burgers.SECOND_BURGER, true, 200, "done" },
                { Burgers.THIRTH_BURGER, true, 200, "done" },
                { Burgers.FOURTH_BURGER, true, 200, "done" },
                { Burgers.FIFTH_BURGER, true, 200, "done" },
                { Burgers.FIFTH_BURGER, false, 200, null },
                { Burgers.EMPTY_BURGER, true, 400, null },
                { Burgers.INCORRECT_BURGER, true, 500, null }
        };
    }

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
    @DisplayName("создание заказа")
    public void checkOrderCreation() {
        User user = userGeneration.getDefault();
        ValidatableResponse userData = userClient.create(user);
        token = needAccessToken ? userData.extract().path("accessToken") : "";

        ValidatableResponse orderData = orderClient.createOrder(new Order(ingredients), token);
        int statusCode = orderData.extract().statusCode();

        if (expectedStatusCode < 500) {
            Assert.assertEquals(expectedStatus, orderData.extract().path("order.status"));
        }

        Assert.assertEquals(expectedStatusCode, statusCode);
    }
}
