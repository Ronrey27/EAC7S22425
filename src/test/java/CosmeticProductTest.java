import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CosmeticProductTest {

    // Test data
    private static final String VALID_NAME = "Lipstick";
    private static final float VALID_PRICE = 15.0f;
    private static final int VALID_STOCK = 10;
    private static final String VALID_BRAND = "Maybelline";
    

    @BeforeEach
    public void setUp() {
      Locale.setDefault(Locale.US);  // Set the default locale to US to ensure consistent behavior across all environments
    }

    /**
     * Test of constructor (with stock), of class CosmeticProduct.
     */
    @Test
    void constructor_WithStock_ValidParameters_SetsFieldsCorrectly() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_BRAND);
        assertAll(
            () -> assertEquals(VALID_NAME, product.getName()),
            () -> assertEquals(VALID_PRICE, product.getPrice()),
            () -> assertEquals(VALID_STOCK, product.getStock()),
            () -> assertEquals(VALID_BRAND, product.getBrand())
        );
    }
    @Test
    void constructor_WithoutStock_ValidParameters_SetsFieldsCorrectly() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        assertAll(
            () -> assertEquals(VALID_NAME, product.getName()),
            () -> assertEquals(VALID_PRICE, product.getPrice()),
            () -> assertEquals(0, product.getStock()), // Default stock
            () -> assertEquals(VALID_BRAND, product.getBrand())
        );
    }
    @Test
    void constructor_InvalidBrand_ThrowsException() {
        assertAll(
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, null)
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, "")
            )
        );
    }

    /**
     * Test of getBrand method, of class CosmeticProduct.
     */
    @Test
    void getBrand_ReturnsCorrectBrand() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        assertEquals(VALID_BRAND, product.getBrand());
    }

    /**
     * Test of applyDiscount method, of class CosmeticProduct.
     */
    @Test
    void applyDiscount_ValidDiscount_CalculatesCorrectPrice() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        float discountedPrice = product.applyDiscount(20.0f); // 20% discount
        assertEquals(12.0f, discountedPrice); // 15.0 * 0.8 = 12.0
    }
    @Test
    void applyDiscount_ZeroDiscount_ReturnsOriginalPrice() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        float discountedPrice = product.applyDiscount(0.0f);
        assertEquals(VALID_PRICE, discountedPrice);
    }
    @Test
    void applyDiscount_FullDiscount_ReturnsZeroPrice() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        float discountedPrice = product.applyDiscount(100.0f);
        assertEquals(0.0f, discountedPrice);
    }
    @Test
    void applyDiscount_NegativeDiscount_ThrowsException() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        assertThrows(
            IllegalArgumentException.class,
            () -> product.applyDiscount(-10.0f)
        );
    }
    @Test
    void applyDiscount_DiscountOver100_ThrowsException() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        assertThrows(
            IllegalArgumentException.class,
            () -> product.applyDiscount(150.0f)
        );
    }
    
    /**
     * Test of toString method, of class CosmeticProduct.
     */
    @Test
    void toString_ValidProduct_ReturnsFormattedString() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_BRAND);
        String expected = String.format("%s,%.2f,%d,%s,%s\n", VALID_NAME, VALID_PRICE, VALID_STOCK, "COSMETIC", VALID_BRAND);
        assertEquals(expected, product.toString());
    }

    /**
     * Test of setPrice method, of class CosmeticProduct (inherited from Product).
     */
    @Test
    void setPrice_ValidPrice_UpdatesPrice() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        product.setPrice(20.0f);
        assertEquals(20.0f, product.getPrice());
    }
    @Test
    void setStock_ValidStock_UpdatesStock() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_BRAND);
        product.setStock(5);
        assertEquals(5, product.getStock());
    }
    @Test
    void decrementStock_ValidStock_DecrementsByOne() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, 3, VALID_BRAND);
        product.decrementStock();
        assertEquals(2, product.getStock());
    }
    @Test
    void thereIsStock_StockPositive_ReturnsTrue() {
        CosmeticProduct product = new CosmeticProduct(VALID_NAME, VALID_PRICE, 1, VALID_BRAND);
        assertTrue(product.thereIsStock());
    }
}