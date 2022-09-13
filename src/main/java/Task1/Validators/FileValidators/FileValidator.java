package Task1.Validators.FileValidators;

import Task1.Validators.Validator;

import java.io.File;

abstract public class FileValidator implements Validator {
    private final File target;

    public FileValidator(File target) {
        this.target = target;
    }

    public File getTarget() {
        return target;
    }
}
