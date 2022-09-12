package WebServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Configuration {
    public final Path CONFIGURATION = Paths.get("C:\\Users\\Pavel\\Desktop\\Reepositories\\OOP\\file_server_configuration.txt");
    private final String PARAMETER = "^\\w+";
    private final String DELIMITER = ":\\s*";
    private final String VALUE = ".+$";
    private final String VALID_LINE = PARAMETER + DELIMITER + VALUE;

    private Integer port;
    private Path root;

    public Configuration() throws FileNotFoundException {
        if (!CONFIGURATION.toFile().exists()) {
            throw new FileNotFoundException("Configuration file not found");
        }

        if (!CONFIGURATION.toFile().isFile()) {
            throw new IllegalArgumentException("Configuration path doesn't lead to a file");
        }

        if (!CONFIGURATION.toFile().canRead()) {
            throw new RuntimeException("Configuration file isn't readable");
        }

        Scanner scanner = new Scanner(new FileInputStream(CONFIGURATION.toFile()));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.matches(VALID_LINE)) {
                throw new RuntimeException("Configuration file has invalid lines: " + line);
            }

            String[] splitLine = line.split(DELIMITER, 2);
            String parameter = splitLine[0];
            String value = splitLine[1];

            if (parameter.equals("port")) {
                port = parsePort(value);
            }
            if (parameter.equals("root")) {
                root = parseRoot(value);
            }
        }
    }

    public Integer getPort() {
        return port;
    }

    public Path getRoot() {
        return root;
    }

    private Path parseRoot(String input) throws FileNotFoundException {
        Path inputRoot = Paths.get(input);

        if (!inputRoot.toFile().exists()) {
            throw new FileNotFoundException("Root directory not found");
        }

        if (!inputRoot.toFile().isDirectory()) {
            throw new IllegalArgumentException("Root path doesn't lead to a directory: " + inputRoot);
        }

        if (!inputRoot.toFile().canRead()) {
            throw new RuntimeException("Root directory isn't readable");
        }

        return inputRoot;
    }

    private Integer parsePort(String input) {
        return Integer.valueOf(input);
    }
}
