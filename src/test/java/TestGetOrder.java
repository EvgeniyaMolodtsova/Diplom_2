import io.qameta.allure.junit4.DisplayName;
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
    public void setUp() throws InterruptedException {
        ThreadSleep.run();
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
    @DisplayName("поллучение заказа")
    public void getOrder(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertEquals(200, getOrder.extract().statusCode());
    }

    @Test
    @DisplayName("проверка, что в заказе те же ингредиенты, которые передали")
    public void checkGetIngredients(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        List<String> ingredients = Convert.arrayToList(Burgers.FIFTH_BURGER);

        Assert.assertEquals(ingredients, getOrder.extract().path("orders.ingredients[0]"));
    }

    @Test
    @DisplayName("проверка, что в структуре есть статус")
    public void checkStatus(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertEquals("done", getOrder.extract().path("orders.status[0]"));
    }

    @Test
    @DisplayName("проверка, что в структуре есть ID")
    public void checkID(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertNotNull(getOrder.extract().path("orders.id"));
    }

    @Test
    @DisplayName("проверка, что в структуре есть номер заказа")
    public void checkNumber(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertNotNull(getOrder.extract().path("orders.number"));
    }

    @Test
    @DisplayName("проверка, что в структуре есть дата заказа")
    public void checkDate(){
        ValidatableResponse getOrder = orderClient.getOrder(token);

        Assert.assertNotNull(getOrder.extract().path("orders.createdAt"));
        Assert.assertNotNull(getOrder.extract().path("orders.updatedAt"));
    }

    @Test
    @DisplayName("проверка, что неавторизованный пользователь не может получить заказ")
    public void getOrderWithOutAuthorization(){
        ValidatableResponse getOrder = orderClient.getOrder("");

        Assert.assertEquals(401, getOrder.extract().statusCode());
    }
}
