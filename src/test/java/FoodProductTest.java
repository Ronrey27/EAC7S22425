import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FoodProductTest {
    private static final String VALID_NAME = "Milk";
    private static final float VALID_PRICE = 2.5f;
    private static final int VALID_STOCK = 10;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String VALID_DATE = LocalDate.now().plusDays(1).format(DATE_FORMATTER);
    private static final String INVALID_DATE = "20231301";
    private static final int DAYS_CLOSE_TO_EXPIRATION = 7;
    private static final int FOOD_PRODUCT_EXPIRATION_DISCOUNT = 20;

    // Helper method to create close-to-expiration date
    private String getCloseExpirationDate() {
        return LocalDate.now()
            .plusDays(DAYS_CLOSE_TO_EXPIRATION)
            .format(DATE_FORMATTER);
    }
    
    @BeforeEach
    public void setUp() {
      Locale.setDefault(Locale.US);  // Set the default locale to US to ensure consistent behavior across all environments
    }

    @Test
    void constructor_ValidParameters_SetsFieldsCorrectly() {
        FoodProduct product = new FoodProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_DATE);
        
        assertAll(
            () -> assertEquals(VALID_NAME, product.getName()),
            () -> assertEquals(VALID_PRICE, product.getPrice()),
            () -> assertEquals(VALID_STOCK, product.getStock()),
            () -> assertEquals(VALID_DATE, product.getExpirationDate())
        );
    }

    @Test
    void constructor_DefaultStock_SetsZeroStock() {
        FoodProduct product = new FoodProduct(VALID_NAME, VALID_PRICE, VALID_DATE);
        assertEquals(0, product.getStock());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",                     // Empty string
        "invalid-date",         // Bad format
        "20230230",           // Invalid date (February 30th)
        "20000101"            // Explicitly expired date
    })
    void constructor_InvalidDate_ThrowsException(String date) {
        assertThrows(IllegalArgumentException.class,
            () -> new FoodProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, date));
    }
    
    @Test
    void applyDiscount_NotCloseToExpiration_AppliesBaseDiscount() {
        // Product not close to expiration
        String futureDate = LocalDate.now()
            .plusDays(DAYS_CLOSE_TO_EXPIRATION + 1)
            .format(DATE_FORMATTER);
        FoodProduct product = new FoodProduct(VALID_NAME, 100.0f, futureDate);
        
        float discountedPrice = product.applyDiscount(20.0f);
        assertEquals(80.0f, discountedPrice);
    }

    @Test
    void applyDiscount_CloseToExpiration_AppliesAdditionalDiscount() {
        FoodProduct product = new FoodProduct(VALID_NAME, 100.0f, getCloseExpirationDate());
        
        float expectedDiscount = 20.0f + FOOD_PRODUCT_EXPIRATION_DISCOUNT;
        float expectedPrice = 100.0f * (1 - expectedDiscount / 100);
        
        assertEquals(expectedPrice, product.applyDiscount(20.0f));
    }

    @Test
    void applyDiscount_MaxDiscount_CapsAt100Percent() {
        FoodProduct product = new FoodProduct(VALID_NAME, 100.0f, getCloseExpirationDate());
        assertEquals(0.0f, product.applyDiscount(95.0f)); // 95% + 10% = 105% → cap at 100%
    }

    @ParameterizedTest
    @ValueSource(floats = {-5.0f, 101.0f})
    void applyDiscount_InvalidDiscount_ThrowsException(float discount) {
        FoodProduct product = new FoodProduct(VALID_NAME, VALID_PRICE, VALID_DATE);
        assertThrows(IllegalArgumentException.class,
            () -> product.applyDiscount(discount));
    }

    @Test
    void updateExpirationDate_ValidDate_UpdatesSuccessfully() {
        FoodProduct product = new FoodProduct(VALID_NAME, VALID_PRICE, VALID_DATE);
        String newDate = LocalDate.now().plusMonths(1).format(DATE_FORMATTER);
        
        product.updateExpirationDate(newDate);
        assertEquals(newDate, product.getExpirationDate());
    }

    @Test
    void updateExpirationDate_InvalidDate_ThrowsException() {
        FoodProduct product = new FoodProduct(VALID_NAME, VALID_PRICE, VALID_DATE);
        assertThrows(IllegalArgumentException.class,
            () -> product.updateExpirationDate(INVALID_DATE));
    }

    @Test
    void toString_ReturnsCorrectFormat() {
        FoodProduct product = new FoodProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_DATE);
        String expected = String.format("%s,%.2f,%d,%s,%s\n",
            VALID_NAME, VALID_PRICE, VALID_STOCK, "FOOD", VALID_DATE);
        
        assertEquals(expected, product.toString());
    }

    @Test
    void getExpirationDate_ReturnsFormattedString() {
        FoodProduct product = new FoodProduct(VALID_NAME, VALID_PRICE, getCloseExpirationDate());
        assertEquals(getCloseExpirationDate(), product.getExpirationDate());
    }

    @Test
    void isCloseToExpiration_EdgeCases() {
        // Exactly at threshold
        String thresholdDate = LocalDate.now()
                .plusDays(DAYS_CLOSE_TO_EXPIRATION)
                .format(DATE_FORMATTER);
        FoodProduct thresholdProduct = new FoodProduct(VALID_NAME, VALID_PRICE, thresholdDate);
        assertTrue(thresholdProduct.applyDiscount(0) < VALID_PRICE);

        // One day over threshold
        String overThresholdDate = LocalDate.now()
            .plusDays(DAYS_CLOSE_TO_EXPIRATION + 1)
            .format(DATE_FORMATTER);
        FoodProduct overThresholdProduct = new FoodProduct(VALID_NAME, VALID_PRICE, overThresholdDate);
        assertEquals(VALID_PRICE, overThresholdProduct.applyDiscount(0));
    }
}