/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 *
 * @author mlvil
 */
public class FileUtilsTest {

    public static final String DEFAULT_DATA_DIRECTORY = "data";
    private final String TEST_DIR_PATH = "src" + File.separator + "test" + File.separator + "java" + File.separator + "temp_dir";
    private final String ERROR_FILE_EXISTS = "The file already exists.";
    private final String ERROR_FILE_NOT_EXIST = "The file does not exist.";
    private final String ERROR_FILE_NAME_NULL = "The file name cannot be null or empty.";
    private final String ERROR_CONTENT_NULL = "The content cannot be null or empty.";

    private Path testDir;

    public FileUtilsTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
      testDir = Paths.get(TEST_DIR_PATH); // Specify your path
      Files.createDirectories(testDir); // Ensure the directory exists
      Locale.setDefault(Locale.US);  // Set the default locale to US to ensure consistent behavior across all environments
    }
    
    @AfterEach
    public void tearDown() throws IOException {
      Files.walk(testDir)
              .map(Path::toFile)
              .forEach(File::delete); // Clean up the directory
    }
    /**
     * Test of constructor, of class FileUtils.
     */
    @Test
    void testConstructorWithUnexistingDirectoryName() {
        String customDir = "my_test_dir";
        String expectedPath = System.getProperty("user.dir") + File.separator + customDir; 
        assertFalse(Files.exists(Path.of(expectedPath))); // directory doesn't exist
        FileUtils customFileUtils = new FileUtils(customDir);
        assertEquals(expectedPath, customFileUtils.getDataDirectory());
        assertTrue(Files.exists(Path.of(expectedPath))); // Check if directory is created
        //Clean up the directory created
        File customDirFile = new File(expectedPath);
        customDirFile.delete();
    }

    @Test
    void testConstructorWithExistingDirectoryName() {
        String customDir = "existing_test_dir";
        String expectedPath = System.getProperty("user.dir") + File.separator + customDir;
        File existingDir = new File(expectedPath);
        existingDir.mkdirs(); // Ensure directory exists
        assertTrue(Files.exists(Path.of(expectedPath))); // Directory should already exist
        FileUtils customFileUtils = new FileUtils(customDir);
        assertEquals(expectedPath, customFileUtils.getDataDirectory());
        assertTrue(Files.exists(Path.of(expectedPath))); // Ensure directory still exists
        // Clean up
        existingDir.delete();
    }
    
    /**
     * Test of getDataDirectory, of class FileUtils.
     */
    @Test
    void testGetDataDirectory_DefaultPath() {
        FileUtils fu = new FileUtils(TEST_DIR_PATH);
        String expectedPath = System.getProperty("user.dir") + File.separator + TEST_DIR_PATH;
        assertEquals(expectedPath, fu.getDataDirectory());
    }

    @Test
    void testGetDataDirectory_CustomPath() {
        String customPath = "custom_data";
        FileUtils fu = new FileUtils(customPath);
        String expectedPath = System.getProperty("user.dir") + File.separator + customPath;
        assertEquals(expectedPath, fu.getDataDirectory());
        // delete folder after test
        File customDir = new File(expectedPath);
        customDir.delete();
    }

    /**
     * Test of getFilePath, of class FileUtils.
     */
    @Test
    void testGetFilePath_ValidFileName() {
        FileUtils fu = new FileUtils(TEST_DIR_PATH);
        String fileName = "example.txt";
        String expectedPath = System.getProperty("user.dir") + File.separator + TEST_DIR_PATH + File.separator + fileName;
        assertEquals(expectedPath, fu.getFilePath(fileName));
    }

    @Test
    void testGetFilePath_EmptyFileName() {
        FileUtils fu = new FileUtils(TEST_DIR_PATH);
        String expectedPath = System.getProperty("user.dir") + File.separator + TEST_DIR_PATH + File.separator;
        assertEquals(expectedPath, fu.getFilePath(""));
    }

    @Test
    void testGetFilePath_NullFileName() {
        FileUtils fu = new FileUtils(TEST_DIR_PATH);
        assertThrows(NullPointerException.class, () -> fu.getFilePath(null));
    }

    
    /**
     * Test of inicialitzeWorkDirectory, of class FileUtils.
     */
    @Test
    void testInicialitzeWorkDirectory_DirectoryCreatedIfNotExists() {
        String customDir = "new_test_dir";
        String expectedPath = System.getProperty("user.dir") + File.separator + customDir;
        File dir = new File(expectedPath);

        // Asegurar que el directorio no existe antes de la prueba
        if (dir.exists()) {
            dir.delete();
        }
        assertFalse(dir.exists());

        // Crear la instancia, lo que debe inicializar el directorio
        new FileUtils(customDir);

        // Verificar que el directorio fue creado
        assertTrue(dir.exists() && dir.isDirectory());

        // Limpiar después del test
        dir.delete();
    }

    @Test
    void testInicialitzeWorkDirectory_DirectoryAlreadyExists() {
        String customDir = "existing_test_dir";
        String expectedPath = System.getProperty("user.dir") + File.separator + customDir;
        File dir = new File(expectedPath);

        // Crear el directorio antes de la prueba
        dir.mkdirs();
        assertTrue(dir.exists() && dir.isDirectory());

        // Crear la instancia, lo que no debería afectar un directorio ya existente
        new FileUtils(customDir);

        // Confirmar que el directorio aún existe
        assertTrue(dir.exists() && dir.isDirectory());

        // Limpiar después del test
        dir.delete();
}

    /**
     * Test of createFile, of class FileUtils.
     */
    @Test
    void testCreateFile_Success() throws IOException {
        String filename = "test_file.txt";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        fu.createFile(filename);
        assertTrue(Files.exists(testDir.resolve(filename)));
        assertEquals(0,new File(testDir.resolve(filename).toString()).length()); //Check if the file is empty
    }
    @Test
    void testCreateFile_AlreadyExists_ThrowsException() throws IOException {
        String filename = "existing_file.txt";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        Files.createFile(testDir.resolve(filename)); // Create the file beforehand
        assertThrows(IllegalArgumentException.class, () -> fu.createFile(filename), ERROR_FILE_EXISTS);
    }
    @Test
    void testCreateFile_NullParameter_ThrowsException() {
      String filename = null;
      FileUtils fu = new FileUtils(TEST_DIR_PATH);
      assertThrows(IllegalArgumentException.class, () -> fu.createFile(filename), ERROR_FILE_NAME_NULL);
    }
    @Test
    void testCreateFile_EmptyParameter_ThrowsException() {
      String filename = "";
      FileUtils fu = new FileUtils(TEST_DIR_PATH);
      assertThrows(IllegalArgumentException.class, () -> fu.createFile(filename), ERROR_FILE_NAME_NULL);
    }

    /**
     * Test of deleteFile, of class FileUtils.
     */
    @Test
    void testDeleteFile_Success() throws IOException {
        String filename = "to_delete.txt";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        Files.createFile(testDir.resolve(filename));
        fu.deleteFile(filename);
        assertFalse(Files.exists(testDir.resolve(filename)));
    }
    @Test
    void testDeleteFile_NotFound_ThrowsException() {
        String filename = "non_existent.txt";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertThrows(IllegalArgumentException.class, () -> fu.deleteFile(filename), ERROR_FILE_NOT_EXIST);
    }
    @Test
    void testDeleteFile_NullParameter_ThrowsException() {
        String filename = null;
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertThrows(IllegalArgumentException.class, () -> fu.deleteFile(filename), ERROR_FILE_NAME_NULL);
    }
    @Test
    void testDeleteFile_EmptyParameter_ThrowsException() {
        String filename = "";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertThrows(IllegalArgumentException.class, () -> fu.deleteFile(filename), ERROR_FILE_NAME_NULL);
    }

    /*
     * Test of fileExists, of class FileUtils.
     */
    @Test
    void testFileExists_ExistsAndNotEmpty_ReturnsTrue() throws IOException{
        String filename = "existing_and_not_empty.txt";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        Files.writeString(testDir.resolve(filename), "some content");
        assertTrue(fu.fileExists(filename));
    }
    @Test
    void testFileExists_ExistsAndEmpty_ReturnsTrue() throws IOException{
        String filename = "existing_and_empty.txt";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        Files.createFile(testDir.resolve(filename));
        assertTrue(fu.fileExists(filename));
    }
    @Test
    void testFileExists_NotExists_ReturnsFalse() {
        String filename = "not_existing.txt";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertFalse(fu.fileExists(filename));
    }    
    @Test
    void testFileExists_EmptyFilename_ReturnsFalse() {
        String filename = "";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertFalse(fu.fileExists(filename));
    }
    @Test
    void testFileExists_NullFilename_ReturnsFalse() {
        String filename = null;
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertFalse(fu.fileExists(filename));
    }
    
  /**
  * Test of extractSupermarketList, of class FileUtils.
  */
  @Test
  public void testExtractFileIntoString_NullFileName() throws IOException {
    FileUtils fu = new FileUtils(TEST_DIR_PATH);
    assertNull(fu.extractFileIntoString(null));
    }

  @Test
  public void testExtractFileIntoString_EmptyFileName() throws IOException {
    FileUtils fu = new FileUtils(TEST_DIR_PATH);
    assertNull(fu.extractFileIntoString(""));
    }

  @Test
  public void testExtractFileIntoString_FileNotExists() throws IOException {
    String filename = "non_existent_file.txt";
    FileUtils fu = new FileUtils(TEST_DIR_PATH);
    assertNull(fu.extractFileIntoString(filename));
    }

  @Test
  public void testExtractFileIntoString_EmptyFile() throws IOException {
    String filename = "empty_file.txt";
    FileUtils fu = new FileUtils(TEST_DIR_PATH);
    fu.createFile(filename);
    String content = fu.extractFileIntoString(filename);
    assertEquals("", content); // Should return an empty string, not null
    }

  @Test
  public void testExtractFileIntoString_WithContent() throws IOException {
      String filename = "with_content.txt";
      String expectedContent = "Line 1\nLine 2\nLine 3\n";
      FileUtils fu = new FileUtils(TEST_DIR_PATH);
      fu.createFile(filename);
      Path filePath = Path.of(fu.getFilePath(filename));
      Files.writeString(filePath, expectedContent);

      String actualContent = fu.extractFileIntoString(filename);
      assertEquals(expectedContent, actualContent);
    }

    /**
     * Test of createFileFromStsring, of class FileUtils.
     */
    @Test
    void testCreateFileFromString_Success() {
        String filename = "test_content.txt";
        String content = "This is a test content.";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        fu.createFileFromString(filename, content);

        Path filePath = Path.of(fu.getFilePath(filename));
        assertTrue(Files.exists(filePath));

        try {
            String fileContent = Files.readString(filePath);
            assertEquals(content, fileContent);
        } catch (IOException e) {
            fail("Error reading the created file.");
        }
    }

    @Test
    void testCreateFileFromString_FileAlreadyExists() {
        String filename = "existing_file.txt";
        String initialContent = "Initial content.";
        String newContent = "New content.";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        Path filePath = Path.of(fu.getFilePath(filename));
        try {
            Files.writeString(filePath, initialContent);
        } catch (IOException e) {
            fail("Error setting up the test file.");
        }

        fu.createFileFromString(filename, newContent);

        try {
            String fileContent = Files.readString(filePath);
            assertEquals(newContent, fileContent);
        } catch (IOException e) {
            fail("Error reading the overwritten file.");
        }
    }

    @Test
    void testCreateFileFromString_NullFileName_ThrowsException() {
        String content = "Some content.";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertThrows(IllegalArgumentException.class, () -> fu.createFileFromString(null, content), ERROR_CONTENT_NULL);
    }

    @Test
    void testCreateFileFromString_EmptyFileName_ThrowsException() {
        String content = "Some content.";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertThrows(IllegalArgumentException.class, () -> fu.createFileFromString("", content), ERROR_CONTENT_NULL);
    }

    @Test
    void testCreateFileFromString_NullContent_ThrowsException() {
        String filename = "test_file.txt";
        FileUtils fu = new FileUtils(TEST_DIR_PATH);

        assertThrows(IllegalArgumentException.class, () -> fu.createFileFromString(filename, null), ERROR_CONTENT_NULL);
    }

  }