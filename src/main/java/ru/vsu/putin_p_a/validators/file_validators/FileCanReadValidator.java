package ru.vsu.putin_p_a.validators.file_validators;

import java.io.File;
import java.io.FileNotFoundException;

public class FileCanReadValidator extends FileValidator {
    public FileCanReadValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws FileNotFoundException {
        if (!this.getTarget().canRead()) {
            throw new RuntimeException(String.format("%s file isn't readable", this.getTarget()));
        }
    }
}