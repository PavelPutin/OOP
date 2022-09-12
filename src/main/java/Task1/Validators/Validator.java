package Task1.Validators;

import java.io.File;
import java.io.FileNotFoundException;

public interface Validator {
    void validate() throws FileNotFoundException;
}
