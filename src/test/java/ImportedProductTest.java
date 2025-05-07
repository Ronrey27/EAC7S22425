import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImportedProductTest {
    private static final String VALID_NAME = "Hand's cream";
    private static final float VALID_PRICE = 10.0f;
    private static final int VALID_STOCK = 50;
    private static final String VALID_COUNTRY = "Spain";
    private static final String VALID_BRAND = "Bosque verde";

    @BeforeEach
    public void setUp() {
      Locale.setDefault(Locale.US);  // Set the default locale to US to ensure consistent behavior across all environments
    }
    
    @Test
    void constructor_ValidParameters_SetsFieldsCorrectly() {
        Product product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_BRAND);
        ImportedProduct importedProduct = new ImportedProduct(product, VALID_COUNTRY);

        assertAll(
            () -> assertEquals(product, importedProduct.getProduct()),
            () -> assertEquals(VALID_COUNTRY, importedProduct.getCountryOfOrigin())
        );
    }

    @Test
    void constructor_NullProduct_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new ImportedProduct(null, VALID_COUNTRY)
        );
    }

    @Test
    void constructor_NullCountry_ThrowsException() {
        Product product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_BRAND);
        assertThrows(
            IllegalArgumentException.class,
            () -> new ImportedProduct(product, null)
        );
    }

    @Test
    void constructor_EmptyCountry_ThrowsException() {
        Product product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_BRAND);
        assertThrows(
            IllegalArgumentException.class,
            () -> new ImportedProduct(product, "")
        );
    }

    @Test
    void getProduct_ReturnsCorrectProduct() {
        Product product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_BRAND);
        ImportedProduct importedProduct = new ImportedProduct(product, VALID_COUNTRY);
        assertEquals(product, importedProduct.getProduct());
    }

    @Test
    void getCountryOfOrigin_ReturnsCorrectCountry() {
        Product product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_BRAND);
        ImportedProduct importedProduct = new ImportedProduct(product, VALID_COUNTRY);
        assertEquals(VALID_COUNTRY, importedProduct.getCountryOfOrigin());
    }

    @Test
    void toString_ReturnsCorrectFormat() {
        Product product = new CosmeticProduct(VALID_NAME, VALID_PRICE, VALID_STOCK, VALID_BRAND);
        ImportedProduct importedProduct = new ImportedProduct(product, VALID_COUNTRY);
        String expected = product.toString();
        assertEquals(expected, importedProduct.toString());
    }
}