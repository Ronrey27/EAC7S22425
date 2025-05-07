/**
 * Represents an imported product, which includes a base product and its country
 * of origin.
 * <p>
 * This class encapsulates a {@link Product} object and adds information about
 * the country
 * where the product was originally produced or manufactured.
 * </p>
 */
public class ImportedProduct {

    /**
     * Constructs an ImportedProduct with the specified product and country of
     * origin.
     *
     * @param product         the base product (cannot be null)
     * @param countryOfOrigin the country of origin (cannot be null or empty)
     * @throws IllegalArgumentException if:
     *                                  <ul>
     *                                  <li>The product is null</li>
     *                                  <li>The country of origin is null or
     *                                  empty</li>
     *                                  </ul>
     */
    String countryOfOrigin;
    Product product;

    public ImportedProduct(Product product, String countryOfOrigin) {
        if (product == null) {
            throw new IllegalArgumentException(Constants.ERROR_PRODUCT_NOT_FOUND);
        }
        if (countryOfOrigin == null || countryOfOrigin.isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_COUNTRY_ORIGIN_NAME_NULL_OR_EMPTY);
        }
        this.product = product;
        this.countryOfOrigin = countryOfOrigin;
    }

    /**
     * Gets the base product associated with this imported product.
     *
     * @return the base product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Gets the country of origin for this imported product.
     *
     * @return the country of origin
     */
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    /**
     * Returns a string representation of the imported product.
     *
     * @return a string in the format "[name]}. [price], [stock], [Brand]"
     */
    @Override
    public String toString() {
        return String.format("%s",product.toString());
        
    }
}