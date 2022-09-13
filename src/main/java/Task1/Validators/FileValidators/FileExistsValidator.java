package Task1.Validators.FileValidators;

import java.io.File;
import java.io.FileNotFoundException;

public class FileExistsValidator extends FileValidator {
    public FileExistsValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws FileNotFoundException {
        if (!getTarget().exists()) {
            throw new FileNotFoundException(String.format("%s file not found", getTarget()));
        }
    }
}
