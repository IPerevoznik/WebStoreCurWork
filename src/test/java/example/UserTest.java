package example;

import Objects.Product;
import Objects.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;

public class UserTest {
    private User user;
    private Product testProduct;

    @Before
    public void setUp() {
        user = new User("TestUser", "password");
        testProduct = new Product();
        testProduct.setNameProduct("Test Product");
        testProduct.setDescription("This is a test product");
        testProduct.setPrice("10.00");
    }

    @Test
    public void testAddToBasket() {
        user.addToBasket(testProduct);
        assertTrue(user.getBasket().contains(testProduct));
    }

    @Test
    public void testRemoveFromBasket() {
        user.addToBasket(testProduct);
        user.removeFromBasket(testProduct);
        assertFalse(user.getBasket().contains(testProduct));
    }
}
