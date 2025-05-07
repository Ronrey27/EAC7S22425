
public class CosmeticProduct extends Product {

    private final String brand;

    /**
     * Constructor for CosmeticProduct.
     *
     * @param name The name of the cosmetic product.
     * @param price The price of the cosmetic product.
     * @param stock The stock of the cosmetic product.
     * @param brand The brand of the cosmetic product (cannot be null or empty).
     * @throws IllegalArgumentException If the brand is null or empty.
     */
    public CosmeticProduct(String name, float price, int stock, String brand) {
        super(name, price, stock);
        if (brand == null || brand.isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_PRODUCT_NAME_NULL_OR_EMPTY);
        } else {
            this.brand = brand;
        }

    }

    /**
     * Constructor for CosmeticProduct.
     *
     * @param name The name of the cosmetic product.
     * @param price The price of the cosmetic product.
     * @param brand The brand of the cosmetic product (cannot be null or empty).
     * @throws IllegalArgumentException If the brand is null or empty.
     */
    public CosmeticProduct(String name, float price, String brand) {
        super(name, price);
        this.brand = brand;
    }

    /**
     * Gets the brand of the cosmetic product.
     *
     * @return The brand.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Applies a discount to the cosmetic product.
     *
     * @param baseDiscount The base discount percentage to apply.
     * @return The discounted price.
     */
    @Override
    public float applyDiscount(float baseDiscount) {
        if (baseDiscount < 0 || baseDiscount > 100) {
            throw new IllegalArgumentException(Constants.ERROR_BASE_DISCOUNT_OUT_OF_RANGE);
        }
        float discountedPrice = getPrice() - (getPrice() * baseDiscount / 100);

        return discountedPrice;
    }

    /**
     * Creates a string representation of the cosmetic product for the toString
     * method.
     *
     * @return A string in the format "[name], [price], [stock], COSMETIC,
     * [brand]".
     */
    @Override
    protected String productToString() {
        return String.format("%s,%.2f,%d,COSMETIC,%s", getName(), getPrice(), getStock(), brand);
    }
}
