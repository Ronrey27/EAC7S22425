import java.util.Scanner;

/**
 * A utility class for handling input and output operations in the console.
 * <p>
 * This class provides methods to display messages, menus, errors, and information,
 * as well as methods to read user input for strings, integers, and floats.
 * </p>
 */
public class UtilsIO {

    /**
     * Displays a message with a header and main text in a formatted way.
     *
     * @param header   The header text to display (e.g., "INFO" or "ERROR").
     * @param mainText The main text to display.
     */
    public void showAnyMessage(String header, String mainText) {
        if (mainText == null || mainText.isEmpty()
            || header == null || header.isEmpty()) {
            return;
        }
        System.out.println(Constants.EMPTY_SPACE);
        System.out.println(Constants.MESSAGE_SEPARATOR);
        System.out.println(header);
        System.out.println(Constants.MESSAGE_SEPARATOR);
        System.out.println(mainText);
        System.out.println(Constants.EMPTY_SPACE);
    }

    /**
     * Displays a menu with a predefined header.
     *
     * @param menuText The text of the menu to display.
     */
    public void showMenu(String menuText) {
        showAnyMessage(Constants.START_MENU_HEADER, menuText);
    }

    /**
     * Displays an error message with a predefined header.
     *
     * @param errorMessage The error message to display.
     */
    public void showError(String errorMessage) {
        showAnyMessage(Constants.ERROR_HEADER, errorMessage);
    }

    /**
     * Displays an informational message with a predefined header.
     *
     * @param infoMessage The informational message to display.
     */
    public void showInfo(String infoMessage) {
        showAnyMessage(Constants.INFO_HEADER, infoMessage);
    }

    /**
     * Prompts the user to enter a string and validates the input.
     *
     * @param message      The prompt message to display.
     * @param errorMessage The error message to display if the input is invalid.
     * @return The string entered by the user.
     */
    public String askForString(String message, String errorMessage) {
        if (message == null || message.isEmpty()) {
            message = Constants.MESSAGE_DEFAULT_ASK_STRING;
        }
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = Constants.MESSAGE_DEFAULT_ERROR_STRING;
        }
        Scanner scanner = new Scanner(System.in, "Cp850");
        System.out.println(message);
        String inputText = scanner.nextLine();
        while (inputText.isEmpty()) {
            System.out.println(errorMessage);
            System.out.println(message);
            inputText = scanner.nextLine();
        }
        return inputText;
    }

    /**
     * Prompts the user to enter an integer and validates the input.
     *
     * @param message      The prompt message to display.
     * @param errorMessage The error message to display if the input is invalid.
     * @return The integer entered by the user.
     */
    public int askForInteger(String message, String errorMessage) {
        if (message == null || message.isEmpty()) {
            message = Constants.MESSAGE_DEFAULT_ASK_INTEGER;
        }
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = Constants.MESSAGE_DEFAULT_ERROR_INTEGER;
        }
        Scanner scan = new Scanner(System.in);
        int inputInt;
        boolean correct;
        do {
            System.out.print(message + "\n");
            correct = scan.hasNextInt();
            if (!correct) {
                scan.next();
                System.out.println(errorMessage);
            }
        } while (!correct);
        inputInt = scan.nextInt();
        scan.nextLine();
        return inputInt;
    }

    /**
     * Prompts the user to enter a float and validates the input.
     *
     * @param message      The prompt message to display.
     * @param errorMessage The error message to display if the input is invalid.
     * @return The float entered by the user.
     */
    public float askForFloat(String message, String errorMessage) {
        if (message == null || message.isEmpty()) {
            message = Constants.MESSAGE_DEFAULT_ASK_FLOAT;
        }
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = Constants.MESSAGE_DEFAULT_ERROR_FLOAT;
        }
        Scanner scan = new Scanner(System.in);
        float inputFloat;
        boolean correct;
        do {
            System.out.print(message + "\n");
            correct = scan.hasNextFloat();
            if (!correct) {
                scan.next();
                System.out.println(errorMessage);
            }
        } while (!correct);
        inputFloat = scan.nextFloat();
        scan.nextLine();
        return inputFloat;
    }

    /**
     * Displays a list of supermarkets in a formatted table.
     *
     * @param supermarketsList The list of supermarkets to display, formatted as a string.
     */
    public void showSupermarkets(String supermarketsList) {
        if (supermarketsList == null || supermarketsList.isEmpty()) {
            return;
        }
        System.out.println("\n");
        System.out.println(Constants.LIST_HEADER);
        System.out.println(Constants.TEMP_LIN);
        String[] supermarkets = supermarketsList.split("\n");
        for (String supermarket1 : supermarkets) {
            String[] supermarket = supermarket1.split(",");
            if (supermarket.length == 4) {
                try {
                    float longitude = Float.parseFloat(supermarket[2]);
                    float latitude = Float.parseFloat(supermarket[3]);
                    System.out.println(String.format(Constants.SUPERMARKET_BOARD_FORMAT, supermarket[0], supermarket[1], longitude, latitude));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
    }
}