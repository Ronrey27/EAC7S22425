import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a supermarket with geographic coordinates and product inventory
 * management.
 * <p>
 * This class manages a collection of products and provides functionality to
 * add, update,
 * and apply discounts to products. It also tracks the supermarket's location
 * using geographic coordinates.
 * </p>
 */
public class Supermarket {

    private String name;
    private String city;
    private float longitude;
    private float latitude;
    public final Map<String, Product> products = new HashMap<>();

    /**
     * Constructs a Supermarket with basic details.
     * <p>
     * Note: Does not validate name/city. Use {@link #setName(String)} and
     * {@link #setCity(String)}
     * to enforce validation rules after construction.
     * </p>
     *
     * @param name      The name of the supermarket (no validation performed)
     * @param city      The city where the supermarket is located (no validation
     *                  performed)
     * @param longitude The geographic longitude in decimal degrees (e.g., -74.0060)
     * @param latitude  The geographic latitude in decimal degrees (e.g., 40.7128)
     */
    public Supermarket(String name, String city, float longitude, float latitude) {
        this.name = name;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Gets the name of the supermarket.
     *
     * @return The name of the supermarket
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supermarket.
     *
     * @param name The new name of the supermarket
     * @throws IllegalArgumentException If the name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_SUPERMARKET_NAME_NULL_OR_EMPTY);
        }
        this.name = name;

    }

    /**
     * Gets the city where the supermarket is located.
     *
     * @return The city of the supermarket
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the supermarket is located.
     *
     * @param city The new city of the supermarket
     * @throws IllegalArgumentException If the city is null or empty
     */
    public void setCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_SUPERMARKET_CITY_NULL_OR_EMPTY);
        }
        this.city = city;

    }

    /**
     * Gets the longitude coordinate of the supermarket.
     *
     * @return The longitude in decimal degrees
     */
    public float getLongitude() {
        return longitude;

    }

    /**
     * Sets the longitude coordinate of the supermarket.
     *
     * @param longitude The new longitude in decimal degrees
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;

    }

    /**
     * Gets the latitude coordinate of the supermarket.
     *
     * @return The latitude in decimal degrees
     */
    public float getLatitude() {
        return latitude;

    }

    /**
     * Sets the latitude coordinate of the supermarket.
     *
     * @param latitude The new latitude in decimal degrees
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;

    }

    /**
     * Gets the product inventory.
     *
     * @return An unmodifiable view of the product map where keys are product names
     *         and values are {@link Product} objects
     */
    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products.values());

    }

    /**
     * Checks if a product exists in the supermarket.
     *
     * @param productName The name of the product to check (case-sensitive)
     * @return True if the product exists, false otherwise
     */
    public boolean hasProduct(String productName) {
        return products.containsKey(productName);
    }

    /**
     * Adds a product to the supermarket.
     *
     * @param product The product to add
     * @throws IllegalArgumentException If:
     *                                  <ul>
     *                                  <li>The product is null</li>
     *                                  <li>A product with the same name already
     *                                  exists</li>
     *                                  </ul>
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException(Constants.ERROR_PRODUCT_NOT_FOUND);
        }
        if (products.containsKey(product.getName())) {
            throw new IllegalArgumentException(Constants.ERROR_PRODUCT_ALREADY_EXISTS);
        }
        products.put(product.getName(), product);

    }

    /**
     * Gets a product by its name.
     *
     * @param productName The name of the product to retrieve (case-sensitive)
     * @return The product, or null if it does not exist
     * @throws IllegalArgumentException If the product name is null or empty
     */
    public Product getProduct(String productName) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_PRODUCT_NAME_NULL_OR_EMPTY);
        }
        return products.get(productName);

    }

    /**
     * Updates a product in the supermarket.
     *
     * @param product The product to update
     * @throws IllegalArgumentException If:
     *                                  <ul>
     *                                  <li>The product is null</li>
     *                                  <li>The product does not exist in the
     *                                  supermarket</li>
     *                                  </ul>
     */
    public void updateProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException(Constants.ERROR_PRODUCT_NOT_FOUND);
        }
        if (!hasProduct(product.getName())) {
            throw new IllegalArgumentException(Constants.ERROR_PRODUCT_ALREADY_EXISTS);
        }
        if (products.containsKey(product.getName())) {
            products.put(product.getName(), product);
        }

    }

    /**
     * Returns a string representation of the supermarket.
     *
     * @return A string in the format "Name: [name], City: [city], Longitude:
     *         [longitude], Latitude: [latitude]"
     */
    public String supermarketToString() {
        return String.format(Constants.SUPERMARKET_FILE_FORMAT, name, city, longitude, latitude);

    }

    /**
     * Returns a string representation of all products in the supermarket.
     *
     * @return A string containing all products and their details, each on a new
     *         line
     */
    public String productsToString() {
        StringBuilder productsString = new StringBuilder();
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            productsString.append(String.format("%s,%s,%s", name, city, entry.getValue()));
        }
        return productsString.toString();

    }

    /**
     * Applies a discount to a specific product.
     *
     * @param productName  The name of the product to discount (case-sensitive)
     * @param baseDiscount The base discount percentage (0-100 inclusive)
     * @return The discounted price
     * @throws IllegalArgumentException If:
     *                                  <ul>
     *                                  <li>productName is null/empty</li>
     *                                  <li>product does not exist</li>
     *                                  <li>baseDiscount is invalid (see
     *                                  {@link Product#applyDiscount(float)})</li>
     *                                  </ul>
     */

    public float applyDiscountToProduct(String productName, float baseDiscount) {
        if (!hasProduct(productName)) {
            throw new IllegalArgumentException(Constants.ERROR_PRODUCT_NOT_FOUND);
        }
        float discountedPrice = 0.0f;
        // buscamos el producto por el nombre
        Product product = products.get(productName);
        // filtamos el tipo de producto con instanceof
        if (product instanceof CosmeticProduct) {
            // le indicamos que los productos filtados son de tipo cosmeticos
            CosmeticProduct cosmetic = (CosmeticProduct) product;
            discountedPrice = cosmetic.applyDiscount(baseDiscount); // aplicamos el metodo de cosmetico al producto
        }
        if (product instanceof FoodProduct) {
            FoodProduct food = (FoodProduct) product;
            discountedPrice = food.applyDiscount(baseDiscount);
        }

        return discountedPrice;

    }

}