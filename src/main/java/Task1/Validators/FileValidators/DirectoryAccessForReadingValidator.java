package Task1.Validators.FileValidators;

import java.io.File;
import java.io.FileNotFoundException;

public class DirectoryAccessForReadingValidator extends FileValidator{
    public DirectoryAccessForReadingValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws FileNotFoundException {
        new FileIsDirectoryValidator(getTarget()).validate();
        new FileCanReadValidator(getTarget()).validate();
    }
}
