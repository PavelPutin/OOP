package ru.vsu.putin_p_a.validators.file_validators;

import java.io.File;
import java.io.FileNotFoundException;

public class FileExistsValidator extends FileValidator {
    public FileExistsValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws ValidationException {
        if (!getTarget().exists()) {
            throw new ValidationException(String.format("%s file not found", getTarget()));
        }
    }
}
