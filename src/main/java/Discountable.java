public interface Discountable {
    /**
     * Applies a discount to the product.
     *
     * @param baseDiscount The base discount percentage to apply (must be between 0
     *                     and 100 inclusive).
     * @return The discounted price.
     * @throws IllegalArgumentException If the baseDiscount is outside the valid
     *                                  range (0-100).
     */
    float applyDiscount(float baseDiscount);
    
}