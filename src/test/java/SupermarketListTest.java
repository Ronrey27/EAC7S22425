import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupermarketListTest {
    private SupermarketList supermarketList;
    private Supermarket supermarket1;
    private Supermarket supermarket2;
    private Supermarket supermarket3;

    @BeforeEach
    void setUp() {
        supermarketList = new SupermarketList();
        supermarket1 = new Supermarket("FreshMart", "New York", -74.0060f, 40.7128f);
        supermarket2 = new Supermarket("GreenGrocer", "Los Angeles", -118.2437f, 34.0522f);
        supermarket3 = new Supermarket("FreshMart", "Chicago", -87.6298f, 41.8781f);
        Locale.setDefault(Locale.US);  // Set the default locale to US to ensure consistent behavior across all environments
    }

    @Test
    void getSize_EmptyList_ReturnsZero() {
        SupermarketList supermarketList = new SupermarketList();
        assertEquals(0, supermarketList.getSize());
    }

    @Test
    void getSize_NonEmptyList_ReturnsCorrectSize() {
        SupermarketList supermarketList = new SupermarketList();
        supermarketList.addSupermarket(new Supermarket("FreshMart", "New York", -74.0060f, 40.7128f));
        supermarketList.addSupermarket(new Supermarket("GreenGrocer", "Los Angeles", -118.2437f, 34.0522f));
        assertEquals(2, supermarketList.getSize());
    }

    @Test
    void getSupermarketList_EmptyList_ReturnsEmptyList() {
        SupermarketList supermarketList = new SupermarketList();
        List<Supermarket> list = supermarketList.getSupermarketList();
        assertTrue(list.isEmpty());
    }

    @Test
    void getSupermarketList_NonEmptyList_ReturnsCopyOfList() {
        SupermarketList supermarketList = new SupermarketList();
        Supermarket supermarket1 = new Supermarket("FreshMart", "New York", -74.0060f, 40.7128f);
        Supermarket supermarket2 = new Supermarket("GreenGrocer", "Los Angeles", -118.2437f, 34.0522f);
        supermarketList.addSupermarket(supermarket1);
        supermarketList.addSupermarket(supermarket2);

        List<Supermarket> list = supermarketList.getSupermarketList();
        assertEquals(2, list.size());
        assertTrue(list.contains(supermarket1));
        assertTrue(list.contains(supermarket2));

        // Ensure the returned list is a copy and not the original list
        list.clear();
        assertFalse(list.contains(supermarket1));
        assertEquals(2, supermarketList.getSize()); // Original list remains unchanged
    }
    @Test
    void addSupermarket_ValidSupermarket_AddsToTheList() {
        supermarketList.addSupermarket(supermarket1);
        assertEquals(1, supermarketList.getSize());
        assertEquals(supermarket1, supermarketList.getSupermarketList().get(0));
    }

    @Test
    void addSupermarket_NullSupermarket_ThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> supermarketList.addSupermarket(null)
        );
    }

    @Test
    void lookForSupermarket_ValidNameAndCity_ReturnsSupermarket() {
        supermarketList.addSupermarket(supermarket1);
        supermarketList.addSupermarket(supermarket2);

        Supermarket foundSupermarket = supermarketList.lookForSupermarket("FreshMart", "New York");
        assertEquals(supermarket1, foundSupermarket);
    }

    @Test
    void lookForSupermarket_NonExistentSupermarket_ReturnsNull() {
        supermarketList.addSupermarket(supermarket1);
        supermarketList.addSupermarket(supermarket2);

        Supermarket foundSupermarket = supermarketList.lookForSupermarket("Nonexistent", "Nowhere");
        assertNull(foundSupermarket);
    }

    @Test
    void lookForSupermarket_NullName_ReturnsNull() {
        supermarketList.addSupermarket(supermarket1);
        Supermarket foundSupermarket = supermarketList.lookForSupermarket(null, "New York");
        assertNull(foundSupermarket);
    }

    @Test
    void lookForSupermarket_EmptyName_ReturnsNull() {
        supermarketList.addSupermarket(supermarket1);
        Supermarket foundSupermarket = supermarketList.lookForSupermarket("", "New York");
        assertNull(foundSupermarket);
    }

    @Test
    void lookForSupermarket_NullCity_ReturnsNull() {
        supermarketList.addSupermarket(supermarket1);
        Supermarket foundSupermarket = supermarketList.lookForSupermarket("FreshMart", null);
        assertNull(foundSupermarket);
    }

    @Test
    void lookForSupermarket_EmptyCity_ReturnsNull() {
        supermarketList.addSupermarket(supermarket1);
        Supermarket foundSupermarket = supermarketList.lookForSupermarket("FreshMart", "");
        assertNull(foundSupermarket);
    }

    @Test
    void filterByName_ValidName_ReturnsFilteredList() {
        supermarketList.addSupermarket(supermarket1);
        supermarketList.addSupermarket(supermarket2);
        supermarketList.addSupermarket(supermarket3);

        SupermarketList filteredList = supermarketList.filterByName("FreshMart");
        assertEquals(2, filteredList.getSize());
        assertTrue(filteredList.getSupermarketList().contains(supermarket1));
        assertTrue(filteredList.getSupermarketList().contains(supermarket3));
    }

    @Test
    void filterByName_NonExistentName_ReturnsEmptyList() {
        supermarketList.addSupermarket(supermarket1);
        supermarketList.addSupermarket(supermarket2);

        SupermarketList filteredList = supermarketList.filterByName("Nonexistent");
        assertTrue(filteredList.getSize() == 0);
    }

    @Test
    void filterByName_NullName_ReturnsEmptyList() {
        supermarketList.addSupermarket(supermarket1);
        SupermarketList filteredList = supermarketList.filterByName(null);
        assertTrue(filteredList.getSize() == 0);
    }

    @Test
    void filterByName_EmptyName_ReturnsEmptyList() {
        supermarketList.addSupermarket(supermarket1);
        SupermarketList filteredList = supermarketList.filterByName("");
        assertTrue(filteredList.getSize() == 0);
    }

    @Test
    void filterByCity_ValidCity_ReturnsFilteredList() {
        supermarketList.addSupermarket(supermarket1);
        supermarketList.addSupermarket(supermarket2);
        supermarketList.addSupermarket(supermarket3);

        SupermarketList filteredList = supermarketList.filterByCity("New York");
        assertEquals(1, filteredList.getSize());
        assertEquals(supermarket1, filteredList.getSupermarketList().get(0));
    }

    @Test
    void filterByCity_NonExistentCity_ReturnsEmptyList() {
        supermarketList.addSupermarket(supermarket1);
        supermarketList.addSupermarket(supermarket2);

        SupermarketList filteredList = supermarketList.filterByCity("Nonexistent");
        assertTrue(filteredList.getSize() == 0);
    }

    @Test
    void filterByCity_NullCity_ReturnsEmptyList() {
        supermarketList.addSupermarket(supermarket1);
        SupermarketList filteredList = supermarketList.filterByCity(null);
        assertTrue(filteredList.getSize() == 0);
    }

    @Test
    void filterByCity_EmptyCity_ReturnsEmptyList() {
        supermarketList.addSupermarket(supermarket1);
        SupermarketList filteredList = supermarketList.filterByCity("");
        assertTrue(filteredList.getSize() == 0);
    }

    @Test
    void listToString_EmptyList_ReturnsEmptyString() {
        assertEquals("", supermarketList.listToString());
    }

    @Test
    void listToString_NonEmptyList_ReturnsConcatenatedString() {
        supermarketList.addSupermarket(supermarket1);
        supermarketList.addSupermarket(supermarket2);

        String expected = supermarket1.supermarketToString() + supermarket2.supermarketToString();
        assertEquals(expected, supermarketList.listToString());
    }
}