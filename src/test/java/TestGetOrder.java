import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestGetOrder {

    private final UserGeneration userGeneration = new UserGeneration();
    private String token;
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();

    @Before
    public void setUp() {
        User user = userGeneration.getDefault();
        ValidatableResponse createUser = userClient.create(user);
        token = createUser.extract().path("accessToken");
        orderClient.createOrder(new Order(Burgers.FIFTH_BURGER), token);
    }

    @After
    public void cleanUp() {
        if (token != null) {
            userClient.delete(token);
        }
    }

    @Test
    public void getOrder(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertEquals(200, getOrder.extract().statusCode());
    }

    @Test
    public void checkGetIngredients(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        List<String> ingredients = Convert.arrayToList(Burgers.FIFTH_BURGER);

        Assert.assertEquals(ingredients, getOrder.extract().path("orders.ingredients[0]"));
    }

    @Test
    public void checkStatus(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertEquals("done", getOrder.extract().path("orders.status[0]"));
    }

    @Test
    public void checkID(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertNotNull(getOrder.extract().path("orders.id"));
    }

    @Test
    public void checkNumber(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertNotNull(getOrder.extract().path("orders.number"));
    }

    @Test
    public void checkDate(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertNotNull(getOrder.extract().path("orders.createdAt"));
        Assert.assertNotNull(getOrder.extract().path("orders.updatedAt"));
    }

    @Test
    public void getOrderWithOutAuthorization(){
        ValidatableResponse getOrder = orderClient.getOrder("");

        Assert.assertEquals(401, getOrder.extract().statusCode());
    }
}
