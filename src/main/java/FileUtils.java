import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A utility class for handling file operations such as creating, deleting, reading, and writing files.
 * <p>
 * This class provides methods to manage files within a specified working directory. It ensures that
 * the directory exists and handles common file-related tasks with proper error checking and validation.
 * </p>
 */
public class FileUtils {

    private final String dataDirectory;

    /**
     * Constructs a new {@code FileUtils} instance with the specified directory name.
     * <p>
     * The working directory is created if it does not already exist.
     * </p>
     *
     * @param directoryName The name of the directory to use for file operations.
     */
    public FileUtils(String directoryName) {
        dataDirectory = System.getProperty("user.dir") + File.separator + directoryName;
        inicialitzeWorkDirectory();
    }

    /**
     * Gets the absolute path of the working directory.
     *
     * @return The absolute path of the working directory.
     */
    public String getDataDirectory() {
        return dataDirectory;
    }

    /**
     * Gets the full file path for a given file name within the working directory.
     *
     * @param fileName The name of the file.
     * @return The full file path as a string.
     */
    public String getFilePath(String fileName) {
        return dataDirectory.concat(File.separator).concat(fileName);
    }

    /**
     * Initializes the working directory by creating it if it does not exist.
     */
    private void inicialitzeWorkDirectory() {
        File fDir = new File(dataDirectory);
        if (!fDir.exists()) {
            fDir.mkdir();
        }
    }

    /**
     * Creates a new file with the specified name in the working directory.
     *
     * @param fileName The name of the file to create.
     * @throws IllegalArgumentException If:
     *         <ul>
     *           <li>The file name is null or empty.</li>
     *           <li>A file with the same name already exists.</li>
     *           <li>An I/O error occurs while creating the file.</li>
     *         </ul>
     */
    public void createFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_FILE_NAME_NULL);
        }
        if (fileExists(fileName)) {
            throw new IllegalArgumentException(Constants.ERROR_FILE_EXISTS);
        }
        File fFile = new File(getFilePath(fileName));
        try {
            fFile.createNewFile();
        } catch (IOException e) {
            throw new IllegalArgumentException(Constants.ERROR_CREATING_FILE);
        }
    }

    /**
     * Deletes the specified file from the working directory.
     *
     * @param fileName The name of the file to delete.
     * @throws IllegalArgumentException If:
     *         <ul>
     *           <li>The file name is null or empty.</li>
     *           <li>The file does not exist.</li>
     *         </ul>
     */
    public void deleteFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_FILE_NAME_NULL);
        }
        if (!fileExists(fileName)) {
            throw new IllegalArgumentException(Constants.ERROR_FILE_NOT_EXIST);
        }
        new File(getFilePath(fileName)).delete();
    }

    /**
     * Checks if a file with the specified name exists in the working directory.
     *
     * @param fileName The name of the file to check.
     * @return {@code true} if the file exists, {@code false} otherwise.
     */
    public boolean fileExists(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        return new File(getFilePath(fileName)).exists();
    }

    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param fileName The name of the file to read.
     * @return The content of the file as a string, or {@code null} if:
     *         <ul>
     *           <li>The file name is null or empty.</li>
     *           <li>The file does not exist.</li>
     *           <li>An I/O error occurs while reading the file.</li>
     *         </ul>
     */
    public String extractFileIntoString(String fileName) {
        if (fileName == null || fileName.isEmpty() || !fileExists(fileName)) {
            return null;
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            return null;
        }
        return content.toString();
    }

    /**
     * Creates or overwrites a file with the specified content.
     *
     * @param fileName The name of the file to create or overwrite.
     * @param content  The content to write to the file.
     * @throws IllegalArgumentException If:
     *         <ul>
     *           <li>The file name is null or empty.</li>
     *           <li>The content is null or empty.</li>
     *           <li>An I/O error occurs while writing the file.</li>
     *         </ul>
     */
    public void createFileFromString(String fileName, String content) {
        if (fileName == null || fileName.isEmpty() || content == null ) {
            throw new IllegalArgumentException(Constants.ERROR_CONTENT_NULL);
        }
        if (fileExists(fileName)) {
            deleteFile(fileName);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(fileName)))) {
            writer.write(content);
        } catch (IOException e) {
            throw new IllegalArgumentException(Constants.ERROR_WRITING_FILE);
        }
    }
}