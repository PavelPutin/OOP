package Task1.WebServer.configuration;

class Parameter {
    private final String DELIMITER = ":\\s*";
    private final String VALID_LINE = "^\\w+" + DELIMITER + ".+$";

    private final String name;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    private final String value;

    Parameter(String input) {
        isValid(input);

        String[] splitLine = input.split(DELIMITER, 2);
        this.name = splitLine[0];
        this.value = splitLine[1];
    }

    private void isValid(String line) {
        if (!line.matches(VALID_LINE)) {
            throw new RuntimeException("Configuration file has invalid lines: " + line);
        }
    }
}
