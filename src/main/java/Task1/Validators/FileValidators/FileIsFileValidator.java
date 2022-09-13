package Task1.Validators.FileValidators;

import java.io.File;

public class FileIsFileValidator extends FileValidator{
    public FileIsFileValidator(File target) {
        super(target);
    }

    @Override
    public void validate() {
        if (!getTarget().isFile()) {
            throw new IllegalArgumentException(String.format("%s doesn't lead to a file", getTarget()));
        }
    }
}
