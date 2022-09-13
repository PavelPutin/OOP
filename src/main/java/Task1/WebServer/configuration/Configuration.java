package Task1.WebServer.configuration;

import Task1.Validators.FileValidators.DirectoryAccessForReadingValidator;
import Task1.Validators.FileValidators.FileAccessForReadingValidator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Configuration {
    public final Path CONFIGURATION = Paths.get("C:\\Users\\Pavel\\Desktop\\Reepositories\\OOP\\file_server_configuration.txt");
    private Integer port;
    private Path root;
    private String appName;

    public Configuration() throws FileNotFoundException {
        new FileAccessForReadingValidator(CONFIGURATION.toFile()).validate();

        Scanner scanner = new Scanner(new FileInputStream(CONFIGURATION.toFile()));
        while (scanner.hasNextLine()) {
            String rawInput = scanner.nextLine();
            Parameter parameter = new Parameter(rawInput);

            if (parameter.getName().equals("port")) {
                port = parsePort(parameter.getValue());
            }
            if (parameter.getName().equals("root")) {
                root = parseRoot(parameter.getValue());
            }
            if (parameter.getName().equals("appname")) {
                appName = parseAppName(parameter.getValue());
            }
        }
    }

    public Integer getPort() {
        return port;
    }

    public Path getRoot() {
        return root;
    }

    public String getAppName() {
        return appName;
    }

    private Path parseRoot(String input) throws FileNotFoundException {
        Path inputRoot = Paths.get(input);
        new DirectoryAccessForReadingValidator(inputRoot.toFile()).validate();
        return inputRoot;
    }

    private Integer parsePort(String input) {
        return Integer.valueOf(input);
    }

    private String parseAppName(String value) {
        return value;
    }
}
