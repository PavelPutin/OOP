package Task1.Validators;

import java.io.File;
import java.io.FileNotFoundException;

public class FileAccessForReadingValidator extends FileValidator {
    public FileAccessForReadingValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws FileNotFoundException {
        new FileIsFileValidator(getTarget()).validate();
        new FileCanReadValidator(getTarget()).validate();
    }
}
