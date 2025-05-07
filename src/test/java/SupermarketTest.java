import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupermarketTest {
    private Supermarket supermarket;
    private static final String SUPERMARKET_NAME = "FreshMart";
    private static final String SUPERMARKET_CITY = "New York";
    private static final float LONGITUDE = -74.0060f;
    private static final float LATITUDE = 40.7128f;

    private static final String PRODUCT_NAME = "Apple";
    private static final float PRODUCT_PRICE = 1.99f;
    private static final int PRODUCT_STOCK = 100;
    private static final String BRAND = "Apple Inc.";
    private static final String SUPERMARKET_FILE_FORMAT = "%s,%s,%.7f,%.7f\n";
    private Product product;

    @BeforeEach
    void setUp() {
        supermarket = new Supermarket(SUPERMARKET_NAME, SUPERMARKET_CITY, LONGITUDE, LATITUDE);
        product = new CosmeticProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK, BRAND);
        Locale.setDefault(Locale.US);  // Set the default locale to US to ensure consistent behavior across all environments
    }

    @Test
    void constructor_ValidParameters_SetsFieldsCorrectly() {
        assertAll(
            () -> assertEquals(SUPERMARKET_NAME, supermarket.getName()),
            () -> assertEquals(SUPERMARKET_CITY, supermarket.getCity()),
            () -> assertEquals(LONGITUDE, supermarket.getLongitude()),
            () -> assertEquals(LATITUDE, supermarket.getLatitude())
        );
    }

    @Test
    void setName_ValidName_UpdatesName() {
        String newName = "GreenGrocer";
        supermarket.setName(newName);
        assertEquals(newName, supermarket.getName());
    }

    @Test
    void setName_NullName_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.setName(null)
        );
    }

    @Test
    void setName_EmptyName_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.setName("")
        );
    }

    @Test
    void setCity_ValidCity_UpdatesCity() {
        String newCity = "Los Angeles";
        supermarket.setCity(newCity);
        assertEquals(newCity, supermarket.getCity());
    }

    @Test
    void setCity_NullCity_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.setCity(null)
        );
    }

    @Test
    void setCity_EmptyCity_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.setCity("")
        );
    }

    @Test
    void setLongitude_ValidLongitude_UpdatesLongitude() {
        float newLongitude = -122.4194f;
        supermarket.setLongitude(newLongitude);
        assertEquals(newLongitude, supermarket.getLongitude());
    }

    @Test
    void setLatitude_ValidLatitude_UpdatesLatitude() {
        float newLatitude = 37.7749f;
        supermarket.setLatitude(newLatitude);
        assertEquals(newLatitude, supermarket.getLatitude());
    }

    @Test
    void getProducts_InitiallyEmpty_ReturnsEmptyMap() {
        ArrayList<Product> products = supermarket.getProducts();
        assertTrue(products.isEmpty());
    }

    @Test
    void hasProduct_ProductNotAdded_ReturnsFalse() {
        assertFalse(supermarket.hasProduct(PRODUCT_NAME));
    }

    @Test
    void addProduct_ValidProduct_AddsProduct() {
        supermarket.addProduct(product);
        assertTrue(supermarket.hasProduct(PRODUCT_NAME));
        assertEquals(product, supermarket.getProduct(PRODUCT_NAME));
    }

    @Test
    void addProduct_NullProduct_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.addProduct(null)
        );
    }

    @Test
    void addProduct_DuplicateProduct_ThrowsException() {
        supermarket.addProduct(product);
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.addProduct(product)
        );
    }

    @Test
    void getProduct_ProductExists_ReturnsProduct() {
        supermarket.addProduct(product);
        assertEquals(product, supermarket.getProduct(PRODUCT_NAME));
    }

    @Test
    void getProduct_ProductDoesNotExist_ReturnsNull() {
        assertNull(supermarket.getProduct("Nonexistent Product"));
    }

    @Test
    void getProduct_NullProductName_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.getProduct(null)
        );
    }

    @Test
    void getProduct_EmptyProductName_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.getProduct("")
        );
    }

    @Test
    void updateProduct_ValidProduct_UpdatesProduct() {
        supermarket.addProduct(product);
        Product updatedProduct = new CosmeticProduct(PRODUCT_NAME, 2.99f, 200, BRAND);
        supermarket.updateProduct(updatedProduct);
        assertEquals(updatedProduct, supermarket.getProduct(PRODUCT_NAME));
    }

    @Test
    void updateProduct_NullProduct_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.updateProduct(null)
        );
    }

    @Test
    void updateProduct_ProductDoesNotExist_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.updateProduct(product)
        );
    }

    @Test
    void supermarketToString_ReturnsCorrectFormat() {
        String expected = String.format(SUPERMARKET_FILE_FORMAT, SUPERMARKET_NAME, SUPERMARKET_CITY, LONGITUDE, LATITUDE);
        assertEquals(expected, supermarket.supermarketToString());
    }

    @Test
    void productsToString_NoProducts_ReturnsEmptyString() {
        assertEquals("", supermarket.productsToString());
    }

    @Test
    void productsToString_WithProducts_ReturnsFormattedString() {
    supermarket.addProduct(product);
    String expected = String.format("%s,%s,%s",supermarket.getName(),supermarket.getCity(),product.toString());
    assertEquals(expected, supermarket.productsToString());
    }

    @Test
    void applyDiscountToProduct_ValidDiscount_ReturnsDiscountedPrice() {
        supermarket.addProduct(product);
        float discountedPrice = supermarket.applyDiscountToProduct(PRODUCT_NAME, 10.0f);
        assertEquals(1.79f, discountedPrice, 0.01); // 10% discount on 1.99
    }

    @Test
    void applyDiscountToProduct_ProductDoesNotExist_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.applyDiscountToProduct("Nonexistent Product", 10.0f)
        );
    }

    @Test
    void applyDiscountToProduct_InvalidDiscount_ThrowsException() {
        supermarket.addProduct(product);
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarket.applyDiscountToProduct(PRODUCT_NAME, -10.0f)
        );
    }
}