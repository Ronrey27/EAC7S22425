import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 *
 * @author ioc
 */
public class UtilsIOTest {

    private final static String MESSAGE_ASK_ANYTHING = "Introdueixi alguna cosa";
    private final static String MESSAGE_ERROR = "Error en la entrada";
    private final static String MESSAGE_TEST_INPUT = "This message tests the input";
    private final static float FLOAT_TEST_INPUT = 6.3f;
    private final static int INT_TEST_INPUT = 6;    
    public static final int POINTS_NO_PLAYED = -1;
    public static final int INDEX_NAME = 0;
    public static final int INDEX_LAST_NAME = 1;
    public static final int INDEX_AGE = 2;
    public static final int THROWS_NUMBER = 10;
    public static final int MAX_POINTS = 10;
    public static final int PLAYERS_MATRIX_COLUMNS = 3;

    public UtilsIOTest() {
    } 

    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        Locale.setDefault(Locale.US);  // Set the default locale to US to ensure consistent behavior across all environments
    }
    
    @AfterEach
    public void tearDown() {
    }

    private void provideInput(String data) {
        if (data != null) {
            ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
            System.setIn(testIn);
        }
    }   

    /**
     * Test of showAnyMessage method, of class UtilsIO.
     */
    @Test
    public void testShowAnyMessage_headerNull_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showAnyMessage(null,"content");
        });
    }
    @Test
    public void testShowAnyMessage_headerEmpty_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showAnyMessage("","content");
        });
    }
    @Test
    public void testShowAnyMessage_contentNull_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showAnyMessage("header",null);
        });
    }
    @Test
    public void testShowAnyMessage_contentEmpty_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showAnyMessage("header","");
        });
    }        
    @Test
    public void testShowAnyMessage_HeaderAndContentNull_NoOutput() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    
        UtilsIO io = new UtilsIO();
        io.showAnyMessage(null, null);
    
        assertTrue(outputStreamCaptor.toString().isEmpty());
    }    
    @Test
    public void testShowAnyMessage_HeaderAndContentEmpty_NoOutput() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    
        UtilsIO io = new UtilsIO();
        io.showAnyMessage("", "");
    
        assertTrue(outputStreamCaptor.toString().isEmpty());
    }
    
    /**
     * Test of showMenu method, of class UtilsIO.
     */
    @Test
    public void testShowMenu_contentNull_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showMenu(null);
        });
    }
    @Test
    public void testShowMenu_contentEmpty_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showMenu("");
        });
    }


    @Test
    public void testShowMenu_contentNull_EmptoyOutput() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        UtilsIO io = new UtilsIO();
        io.showMenu(null);
        assertTrue(outputStreamCaptor.toString().isEmpty());
    }
    
    @Test
    public void testShowMenu_contentEmpty_EmptoyOutput() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        UtilsIO io = new UtilsIO();
        io.showMenu("");
        assertTrue(outputStreamCaptor.toString().isEmpty());
    }
    
    /**
     * Test of showError method, of class UtilsIO.
     */
    @Test
    public void testShowError_contentNull_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showError(null);
        });
    }
    @Test
    public void testShowError_contentEmpty_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showError("");
        });
    }

    @Test
    public void testShowError_contentNull_EmptoyOutput() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        UtilsIO io = new UtilsIO();
        io.showError(null);
        assertTrue(outputStreamCaptor.toString().isEmpty());
    }
    
    @Test
    public void testShowError_contentEmpty_EmptoyOutput() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        UtilsIO io = new UtilsIO();
        io.showError("");
        assertTrue(outputStreamCaptor.toString().isEmpty());
    }
    
    /**
     * Test of showInfo method, of class UtilsIO.
     */
    @Test
    public void testShowInfo_contentNull_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showInfo(null);
        });
    }
    @Test
    public void testShowInfo_contentEmpty_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showInfo("");
        });
    }

    @Test
    public void testShowInfo_contentNull_EmptoyOutput() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        UtilsIO io = new UtilsIO();
        io.showInfo(null);
        assertTrue(outputStreamCaptor.toString().isEmpty());
    }
    
    @Test
    public void testShowInfo_contentEmpty_EmptoyOutput() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        UtilsIO io = new UtilsIO();
        io.showInfo("");
        assertTrue(outputStreamCaptor.toString().isEmpty());
    }
    
    /**
     * Test of askForString method, of class UtilsIO.
     */
    @Test
    public void testAskForStringOK() {
        UtilsIO io = new UtilsIO();
        provideInput(MESSAGE_TEST_INPUT);
        String result = io.askForString(MESSAGE_ASK_ANYTHING, MESSAGE_ERROR);
        assertEquals(MESSAGE_TEST_INPUT, result);
    }

    @Test
    public void testAskForStringNotOKIfEmpty() {
        UtilsIO io = new UtilsIO();
        provideInput("\n" + MESSAGE_TEST_INPUT);
        String result = io.askForString(MESSAGE_ASK_ANYTHING, MESSAGE_ERROR);
        assertEquals(MESSAGE_TEST_INPUT, result);
    }

    @Test
    public void testAskForStringOKIfMessageNull() {
        UtilsIO io = new UtilsIO();
        provideInput("\n" + MESSAGE_TEST_INPUT);
        String result = io.askForString(null, MESSAGE_ERROR);
        assertEquals(MESSAGE_TEST_INPUT, result);
    }

    @Test
    public void testAskForStringOKIfMessageEmpty() {
        UtilsIO io = new UtilsIO();
        provideInput("\n" + MESSAGE_TEST_INPUT);
        String result = io.askForString("", MESSAGE_ERROR);
        assertEquals(MESSAGE_TEST_INPUT, result);
    }

    @Test
    public void testAskForStringOKIfErrorMessageNull() {
        UtilsIO io = new UtilsIO();
        provideInput("\n" + MESSAGE_TEST_INPUT);
        String result = io.askForString(MESSAGE_ASK_ANYTHING, null);
        assertEquals(MESSAGE_TEST_INPUT, result);
    }

    @Test
    public void testAskForStringOKIfErrorMessageEmpty() {
        UtilsIO io = new UtilsIO();
        provideInput("\n" + MESSAGE_TEST_INPUT);
        String result = io.askForString(MESSAGE_ASK_ANYTHING, "");
        assertEquals(MESSAGE_TEST_INPUT, result);
    }
    /**
     * Test of askForInteger method, of class UtilsIO.
     */
    @Test
    public void testAskForIntegerOK() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        int result = io.askForInteger(MESSAGE_ASK_ANYTHING, MESSAGE_ERROR);
        assertEquals(INT_TEST_INPUT, result);
    }
    
    @Test
    public void testAskForIntegerNotOKIfString() {
        UtilsIO io = new UtilsIO();
        provideInput(MESSAGE_TEST_INPUT + "\n" + String.valueOf(INT_TEST_INPUT)+"\n");
        int result = io.askForInteger(MESSAGE_ASK_ANYTHING, MESSAGE_ERROR);
        assertEquals(INT_TEST_INPUT, result);
    }
 
    @Test
    public void testAskForIntegerNotOKIfFloat() {
        UtilsIO io = new UtilsIO();
        provideInput(FLOAT_TEST_INPUT + "\n" + String.valueOf(INT_TEST_INPUT)+"\n");
        int result = io.askForInteger(MESSAGE_ASK_ANYTHING, MESSAGE_ERROR);
        assertEquals(INT_TEST_INPUT, result);
    }

    @Test
    public void testAskForIntegerOKIfMessageNull() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        int result = io.askForInteger(null, MESSAGE_ERROR);
        assertEquals(INT_TEST_INPUT, result);
    }

    @Test
    public void testAskForIntegerOKIfMessageEmpty() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        int result = io.askForInteger("", MESSAGE_ERROR);
        assertEquals(INT_TEST_INPUT, result);
    }

    @Test
    public void testAskForIntegerOKIfErrorMessageNull() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        int result = io.askForInteger(MESSAGE_ASK_ANYTHING, null);
        assertEquals(INT_TEST_INPUT, result);
    }

    @Test
    public void testAskForIntegerOKIfErrorMessageEmpty() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        int result = io.askForInteger(MESSAGE_ASK_ANYTHING, "");
        assertEquals(INT_TEST_INPUT, result);
    }
    /**
     * Test of askForFloat method, of class UtilsIO.
     */
    @Test
    public void testAskForFloatOK() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(FLOAT_TEST_INPUT)+"\n");
        float result = io.askForFloat(MESSAGE_ASK_ANYTHING, MESSAGE_ERROR);
        assertEquals(FLOAT_TEST_INPUT, result);
    }

    @Test
    public void testAskForFloatNotOkIfString() {
        UtilsIO io = new UtilsIO();
        provideInput(MESSAGE_TEST_INPUT + "\n" + String.valueOf(FLOAT_TEST_INPUT)+"\n");
        float result = io.askForFloat(MESSAGE_ASK_ANYTHING, MESSAGE_ERROR);
        assertEquals(FLOAT_TEST_INPUT, result);
    }
    @Test
    public void testAskForFloatOKIfMessageNull() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        float result = io.askForFloat(null, MESSAGE_ERROR);
        assertEquals(INT_TEST_INPUT, result);
    }

    @Test
    public void testAskForFloatOKIfMessageEmpty() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        float result = io.askForFloat("", MESSAGE_ERROR);
        assertEquals(INT_TEST_INPUT, result);
    }

    @Test
    public void testAskForFloatOKIfErrorMessageNull() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        float result = io.askForFloat(MESSAGE_ASK_ANYTHING, null);
        assertEquals(INT_TEST_INPUT, result);
    }

    @Test
    public void testAskForFloatOKIfErrorMessageEmpty() {
        UtilsIO io = new UtilsIO();
        provideInput(String.valueOf(INT_TEST_INPUT)+"\n");
        float result = io.askForFloat(MESSAGE_ASK_ANYTHING, "");
        assertEquals(INT_TEST_INPUT, result);
    }
    /**
     * Test of showSupermarkets method, of class UtilsIO.
     */
    @Test
    public void testShowSupermarkets_listNull_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showSupermarkets(null);
        });
    }

    @Test
    public void testShowSupermarkets_listEmpty_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showSupermarkets("");
        });
    }

    @Test
    public void testShowSupermarkets_listWithoutData_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showSupermarkets("This is a random String without data");
        });
    }

    @Test
    public void testShowSupermarkets_listWithoutIncorrectColumns_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showSupermarkets("DateTime1,Supermarket1,City1,1,1\nDateTime2,Supermarket2,City2,1,1\nDateTime3,Supermarket3,City3,1,1\nDateTime4,Supermarket4,1\n");
        });
    }

    @Test
    public void testShowSupermarkets_IncorrectFloatFormat_NoException() {
        UtilsIO io = new UtilsIO();
            assertDoesNotThrow(() -> {
                io.showSupermarkets("DateTime1,Supermarket1,City1,text_instead_of_float,1\nDateTime2,Supermarket2,City2,1,1\nDateTime3,Supermarket3,City3,1,1\nDateTime4,Supermarket4,1,1\n");
        });
    }
}
