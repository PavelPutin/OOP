package Task1.Validators.FileValidators;

import java.io.File;
import java.io.FileNotFoundException;

public class FileIsDirectoryValidator extends FileValidator {
    public FileIsDirectoryValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws FileNotFoundException {
        if (!getTarget().isDirectory()) {
            throw new IllegalArgumentException(String.format("%s doesn't lead to a directory", getTarget()));
        }
    }
}
