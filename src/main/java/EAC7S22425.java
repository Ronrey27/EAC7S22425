import java.util.Locale;

public class EAC7S22425 {

    public static void main(String[] args) {
        EAC7S22425 program = new EAC7S22425();
        Locale.setDefault(Locale.US);
        program.start();
    }

    String marketFileName = Constants.SUPERMARKETS_FILE_NAME;
    String productsFileName = Constants.PRODUCTS_FILE_NAME;

    public void start() {
        UtilsIO io = new UtilsIO();
        FileUtils fu = new FileUtils(Constants.DEFAULT_DATA_DIRECTORY);
        SupermarketList supermarketList = new SupermarketList();

        boolean exitLoop = false;
        while (!exitLoop) {
            io.showMenu(Constants.START_MENU);
            int option = io.askForInteger(Constants.MESSAGE_ASK_OPTION_VALUE, Constants.ERROR_NO_INTEGER);
            switch (option) {
                case 1: {
                    addSupermarketToList(io, supermarketList);
                    break;
                }
                case 2: {
                    addProductToSupermarket(io, supermarketList);
                    break;
                }
                case 3: {
                    showSupermarketList(io, supermarketList);
                    break;
                }
                case 4: {
                    exportSupermarkets(io, fu, supermarketList);
                    break;
                }
                case 5: {
                    importSupermarkets(io, fu, supermarketList);
                    break;
                }
                case 0: {
                    // exit the program
                    exitLoop = true;
                    break;
                }
                default: {
                    // show an error message if the option is not valid
                    io.showError(Constants.MESSAGE_NOT_VALID_OPTION);
                    break;
                }
            }
        }
    }

    private void showSupermarketList(UtilsIO io, SupermarketList supermarketList) {
        if (supermarketList.getSupermarketList().isEmpty()) {
            io.showError(Constants.ERROR_SUPERMARKET_LIST_EMPTY);
            return;
        }
        io.showSupermarkets(supermarketList.listToString());
    }

    private void addSupermarketToList(UtilsIO io, SupermarketList supermarketList) {
        String name = io.askForString(Constants.MESSAGE_INSERT_MARKET_NAME, Constants.ERROR_EMPTY_STRING);
        String city = io.askForString(Constants.MESSAGE_INSERT_CITY, Constants.ERROR_EMPTY_STRING);
        float longitude = io.askForFloat(Constants.MESSAGE_INSERT_LONGITUDE, Constants.ERROR_NO_FLOAT);
        float latitude = io.askForFloat(Constants.MESSAGE_INSERT_LATITUDE, Constants.ERROR_NO_FLOAT);
        Supermarket supermarket = new Supermarket(name, city, longitude, latitude);
        // comprobamos si el supermercado existe
        if (supermarketList.lookForSupermarket(name, city) != null) {
            io.showError(Constants.ERROR_SUPERMARKET_ALREADY_ON_LIST);
            return;
        }
        supermarketList.addSupermarket(supermarket);
        io.showInfo(Constants.MESSAGE_MARKET_ADDED_SUCCESSFULLY);

    }

    private void addProductToSupermarket(UtilsIO io, SupermarketList supermarketList) {
        String name = io.askForString(Constants.MESSAGE_INSERT_MARKET_NAME, Constants.ERROR_EMPTY_STRING);
        String city = io.askForString(Constants.MESSAGE_INSERT_CITY, Constants.ERROR_EMPTY_STRING);
        Supermarket supermarket = supermarketList.lookForSupermarket(name, city);
        if (supermarket == null) {
            io.showError(Constants.ERROR_SUPERMARKET_NOT_FOUND);
        } else {
            String productName = io.askForString(Constants.MESSAGE_INSERT_PRODUCT_NAME, Constants.ERROR_EMPTY_STRING);
            float price = io.askForFloat(Constants.MESSAGE_INSERT_PRODUCT_PRICE, Constants.MESSAGE_DEFAULT_ERROR_FLOAT);

            boolean exitLoop = false;

            while (!exitLoop) {
                io.showInfo(Constants.MESSAGE_MENU_PRODUCT_TYPE);
                int option = io.askForInteger(Constants.MESSAGE_ASK_OPTION_TYPE_VALUE, Constants.ERROR_NO_INTEGER);
                if (option == 1) {
                    String expirationDate = io.askForString(Constants.MESSAGE_INSERT_EXPIRATION_DATE,
                            Constants.ERROR_EXPIRATION_DATE_FORMAT);
                    try {// creamos el producto si todo va correcto en caso contrario muestra ERROR
                        FoodProduct foodProduct = new FoodProduct(productName, price, expirationDate);
                        if (supermarket.hasProduct(productName)) {
                            io.showError(Constants.ERROR_PRODUCT_ALREADY_EXISTS);
                            return;
                        }
                        supermarket.addProduct(foodProduct);
                        io.showInfo(Constants.MESSAGE_PRODUCT_ADDED_SUCCESSFULLY);
                    } catch (IllegalArgumentException e) {
                        io.showError(e.getMessage());
                    }
                    exitLoop = true;
                } else if (option == 2) {
                    String brandName = io.askForString(Constants.MESSAGE_INSERT_PRODUCT_BRAND_NAME,
                            Constants.ERROR_EMPTY_STRING);
                    try {
                        CosmeticProduct cosmeticProduct = new CosmeticProduct(productName, price, brandName);
                        // comprobamos si el producto existe
                        if (supermarket.hasProduct(productName)) {
                            io.showError(Constants.ERROR_PRODUCT_ALREADY_EXISTS);
                            return;
                        }
                        // agregamos el producto al supermercado
                        supermarket.addProduct(cosmeticProduct);
                        io.showInfo(Constants.MESSAGE_PRODUCT_ADDED_SUCCESSFULLY);
                    } catch (IllegalArgumentException e) {
                        io.showError(e.getMessage());
                    }

                    exitLoop = true;
                } else {
                    io.showError(Constants.MESSAGE_NOT_VALID_OPTION);
                }

            }
        }

    }

    private void exportSupermarkets(UtilsIO io, FileUtils fu, SupermarketList supermarketList) {

        if (supermarketList == null || supermarketList.getSupermarketList().isEmpty()) {
            io.showError(Constants.ERROR_SUPERMARKET_LIST_EMPTY);
            return;
        }
        // for each supermarket in the list, write the supermarket data to the file and
        // the products data to the other file
        String supermarkets = "";
        String products = "";
        for (Supermarket supermarket : supermarketList.getSupermarketList()) {
            supermarkets += supermarket.supermarketToString();
            products += supermarket.productsToString();
        }
        try {
            fu.createFileFromString(marketFileName, supermarkets);
            fu.createFileFromString(productsFileName, products);
            io.showInfo(Constants.MESSAGE_FILES_CREATED);
        } catch (IllegalArgumentException e) {
            io.showError(e.getMessage());
        }
    }

    private void importSupermarkets(UtilsIO io, FileUtils fu, SupermarketList supermarketList) {
        // verify if the files marketFileName and productsFileName exist. If not, show
        // an error message
        if (!fu.fileExists(marketFileName) || !fu.fileExists(productsFileName)) {
            io.showError(Constants.ERROR_FILES_NOT_FOUND);
            return;
        }
        // import the content of the two files into Strings
        String marketFileContent = fu.extractFileIntoString(marketFileName);
        String productsFileContent = fu.extractFileIntoString(productsFileName);
        if (marketFileContent == null || productsFileContent == null) {
            io.showError(Constants.ERROR_FILES_NOT_FOUND);
            return;
        }
        if (marketFileContent.isEmpty() || productsFileContent.isEmpty()) {
            io.showError(Constants.ERROR_FILES_EMPTY);
            return;
        }
        // for each supermarket in the list, write the supermarket data to the file and
        // the products data to the other file
        String[] marketLines = marketFileContent.split("\n");
        String[] productsLines = productsFileContent.split("\n");
        for (String marketLine : marketLines) {
            String[] marketData = marketLine.split(",");

            Supermarket supermarket = null;
            // buscamos el supermercado en busca de duplicados
            if (supermarketList.lookForSupermarket(marketData[0], marketData[1]) == null) {
                supermarket = new Supermarket(marketData[0], marketData[1], Float.parseFloat(marketData[2]),
                        Float.parseFloat(marketData[3]));
                supermarketList.addSupermarket(supermarket);
            } else {// si el supermercado existe lo cargamos
                supermarket = supermarketList.lookForSupermarket(marketData[0], marketData[1]);
            }

            for (String productLine : productsLines) {
                String[] productData = productLine.split(",");

                try {
                    // nombre y ciudad y tipo de producto coinciden con marketdata entonces importa el producto
                    if (productData[0].equals(marketData[0]) && productData[1].equals(marketData[1]) && productData[5].equals(Constants.PRODUCT_IMPORT_TYPE_COSMETIC)) {
                        Product cosmetic = new CosmeticProduct(productData[2], Float.parseFloat(productData[3]),Integer.parseInt(productData[4]), productData[6]);
                        // comprobamos si el producto ya existe y si existe lo actualizamos
                        if (supermarket.hasProduct(productData[2])) {
                            supermarket.updateProduct(cosmetic);
                        } else {
                            supermarket.addProduct(cosmetic);
                        }
                    }
                    if (productData[0].equals(marketData[0]) && productData[1].equals(marketData[1]) && productData[5].equals(Constants.PRODUCT_IMPORT_TYPE_FOOD)) {
                        Product food = new FoodProduct(productData[2], Float.parseFloat(productData[3]), Integer.parseInt(productData[4]), productData[6]);
                        if (supermarket.hasProduct(productData[2])) {
                            supermarket.updateProduct(food);
                        } else {
                            supermarket.addProduct(food);
                        }

                    }

                } catch (IllegalArgumentException e) {
                    io.showError(e.getMessage());
                }

            }

        }
        io.showInfo(Constants.MESSAGE_FILES_IMPORTED);
    }
}