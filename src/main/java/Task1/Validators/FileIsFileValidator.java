package Task1.Validators;

import java.io.File;
import java.io.FileNotFoundException;

public class FileIsFileValidator extends FileValidator{
    public FileIsFileValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws FileNotFoundException {
        if (!getTarget().isFile()) {
            throw new IllegalArgumentException(String.format("%s doesn't lead to a file", getTarget()));
        }
    }
}
