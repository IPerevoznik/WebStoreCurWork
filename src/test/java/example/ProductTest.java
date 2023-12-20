package example;

import Objects.Product;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
    private Product product;

    @Before
    public void setUp() {
        product = new Product();
        product.setNameProduct("Test Product");
        product.setDescription("This is a test product");
        product.setPhotoProduct("test.jpg");
        product.setPrice("10.00");
        product.getTagProduct().add("testTag1");
        product.getTagProduct().add("testTag2");
    }

    @Test
    public void testGetNameProduct() {
        assertEquals("Test Product", product.getNameProduct());
    }

    @Test
    public void testContainsTag() {
        assertTrue(product.containsTag("testTag1"));
        assertFalse(product.containsTag("unknownTag"));
    }
}
