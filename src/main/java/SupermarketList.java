import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of {@link Supermarket} objects and provides
 * functionality
 * to manage and filter the list of supermarkets.
 * <p>
 * This class allows adding supermarkets, searching for specific supermarkets by
 * name and city,
 * filtering supermarkets by name or city, and generating a string
 * representation of the list.
 * </p>
 */
public class SupermarketList {
    /**
     * The list of supermarkets.
     * <p>
     * This list is publicly accessible but should be modified only through the
     * provided methods
     * to ensure proper validation and consistency.
     * </p>
     */
    private final List<Supermarket> supermarketList = new ArrayList<>();

    /*
     * Gets the size of the supermarket list.
     *
     * @return The number of supermarkets in the list
     */
    public int getSize() {
        return supermarketList.size();
    }

    /*
     * Gets the list of supermarkets.
     * <p>
     * This method returns a copy of the list to prevent direct modification.
     * </p>
     * 
     * @return A new list containing all supermarkets in the list
     */
    public List<Supermarket> getSupermarketList() {
        return new ArrayList<>(supermarketList);
    }

    /**
     * Adds a supermarket to the list.
     *
     * @param supermarket The supermarket to add (cannot be null)
     * @throws IllegalArgumentException If the supermarket is null
     */
    public void addSupermarket(Supermarket supermarket) {
        if (supermarket == null) {
            throw new IllegalArgumentException(Constants.ERROR_SUPERMARKET_NULL);
        }
        supermarketList.add(supermarket);

    }

    /**
     * Searches for a supermarket by its name and city.
     * <p>
     * If the name or city is null or empty, this method returns null.
     * </p>
     *
     * @param name The name of the supermarket to search for (case-sensitive)
     * @param city The city of the supermarket to search for (case-sensitive)
     * @return The matching supermarket, or null if no match is found
     */
    public Supermarket lookForSupermarket(String name, String city) {
        for (Supermarket supermarket : supermarketList) {
            // if the name and city match, return the supermarket
            if (supermarket.getName().equals(name) && supermarket.getCity().equals(city)) {
                return supermarket;
            }
        }
        return null;
    }

    /**
     * Filters the list of supermarkets by name.
     * <p>
     * If the name is null or empty, this method returns an empty
     * {@link SupermarketList}.
     * </p>
     *
     * @param name The name to filter by (case-sensitive)
     * @return A new {@link SupermarketList} containing only supermarkets with the
     *         specified name
     */
    public SupermarketList filterByName(String name) {
        SupermarketList filteredList = new SupermarketList();
        if (name == null || name.isEmpty()) {
            return filteredList;
        }
        for (Supermarket supermarket : supermarketList) {
            if (supermarket.getName().equals(name)) {
                filteredList.addSupermarket(supermarket);
            }
        }
        return filteredList;
    }

    /**
     * Filters the list of supermarkets by city.
     * <p>
     * If the city is null or empty, this method returns an empty
     * {@link SupermarketList}.
     * </p>
     *
     * @param city The city to filter by (case-sensitive)
     * @return A new {@link SupermarketList} containing only supermarkets in the
     *         specified city
     */
    public SupermarketList filterByCity(String city) {
        SupermarketList filteredList = new SupermarketList();
        if (city == null || city.isEmpty()) {
            return filteredList;
        }
        for (Supermarket supermarket : supermarketList) {
            if (supermarket.getCity().equals(city)) {
                filteredList.addSupermarket(supermarket);
            }
        }
        return filteredList;
    }

    /**
     * Generates a string representation of the list of supermarkets.
     * <p>
     * Each supermarket is represented by its
     * {@link Supermarket#supermarketToString()} output,
     * concatenated without separators.
     * </p>
     *
     * @return A string containing the concatenated representations of all
     *         supermarkets in the list
     */
    public String listToString() {

        String result = "";
        for (Supermarket supermarket : supermarketList) {
            result += supermarket.supermarketToString();
        }
        return result;
    }
}