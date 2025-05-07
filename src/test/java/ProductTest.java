import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {

    // Concrete class to test the abstract Product class
    private static class TestProduct extends Product {
        public TestProduct(String name, float price) {
            super(name, price);
        }

        public TestProduct(String name, float price, int stock) {
            super(name, price, stock);
        }

        @Override
        public float applyDiscount(float baseDiscount) {
            // Dummy implementation for the Discountable interface
            return this.price * (1 - baseDiscount / 100);
        }
    }

    // Constants for testing
    private static final String VALID_NAME = "Test Product";
    private static final float VALID_PRICE = 10.0f;
    private static final int VALID_STOCK = 5;

    @BeforeEach
    public void setUp() {
      Locale.setDefault(Locale.US);  // Set the default locale to US to ensure consistent behavior across all environments
    }
    
    /**
     * Test of constructor, of class Product.
     */
    @Test
    void constructor_ValidParameters_SetsFieldsCorrectly() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, VALID_STOCK);
        assertEquals(VALID_NAME, product.getName());
        assertEquals(VALID_PRICE, product.getPrice());
        assertEquals(VALID_STOCK, product.getStock());
    }
    @Test
    void constructor_InvalidName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new TestProduct(null, VALID_PRICE));
        assertThrows(IllegalArgumentException.class, () -> new TestProduct("", VALID_PRICE));
    }
    @Test
    void constructor_InvalidPrice_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new TestProduct(VALID_NAME, 0));
        assertThrows(IllegalArgumentException.class, () -> new TestProduct(VALID_NAME, -5.0f));
    }
    @Test
    void constructor_InvalidStock_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new TestProduct(VALID_NAME, VALID_PRICE, -1));
    }

    /**
     * Test of setPrice method, of class Product.
     */
    @Test
    void setPrice_ValidPrice_UpdatesPrice() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE);
        product.setPrice(20.0f);
        assertEquals(20.0f, product.getPrice());
    }
    @Test
    void setPrice_InvalidPrice_ThrowsException() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE);
        assertThrows(IllegalArgumentException.class, () -> product.setPrice(0));
        assertThrows(IllegalArgumentException.class, () -> product.setPrice(-5.0f));
    }
    @Test
    void setStock_ValidStock_UpdatesStock() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE);
        product.setStock(10);
        assertEquals(10, product.getStock());
    }
    @Test
    void setStock_InvalidStock_ThrowsException() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE);
        assertThrows(IllegalArgumentException.class, () -> product.setStock(-1));
    }

    /**
     * Test of decrementStock method, of class Product.
     */
    @Test
    void decrementStock_ValidStock_DecrementsByOne() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, 3);
        product.decrementStock();
        assertEquals(2, product.getStock());
    }
    @Test
    void decrementStock_ZeroStock_ThrowsException() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, 0);
        assertThrows(IllegalArgumentException.class, product::decrementStock);
    }
    @Test
    void decrementStockQuantity_ValidQuantity_DecrementsStock() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, 10);
        product.decrementStock(3);
        assertEquals(7, product.getStock());
    }
    @Test
    void decrementStockQuantity_InvalidQuantity_ThrowsException() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, 5);
        assertThrows(IllegalArgumentException.class, () -> product.decrementStock(0));
        assertThrows(IllegalArgumentException.class, () -> product.decrementStock(-2));
    }
    @Test
    void decrementStockQuantity_InsufficientStock_ThrowsException() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, 2);
        assertThrows(IllegalArgumentException.class, () -> product.decrementStock(3));
    }

    /**
     * Test of thereIsStock method, of class Product.
     */
    @Test
    void thereIsStock_StockPositive_ReturnsTrue() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, 1);
        assertTrue(product.thereIsStock());
    }
    @Test
    void thereIsStock_StockZero_ReturnsFalse() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, 0);
        assertFalse(product.thereIsStock());
    }

    /**
     * Test of toString method, of class Product.
     */
    @Test
    void toString_ValidProduct_ReturnsFormattedString() {
        TestProduct product = new TestProduct(VALID_NAME, VALID_PRICE, 5);
        String expected = String.format("%s,%.2f,%d\n", VALID_NAME, VALID_PRICE, 5);
        assertEquals(expected, product.toString());
    }
}