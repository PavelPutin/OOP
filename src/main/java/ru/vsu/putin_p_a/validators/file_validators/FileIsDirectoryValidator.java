package ru.vsu.putin_p_a.validators.file_validators;

import java.io.File;

public class FileIsDirectoryValidator extends FileValidator {
    public FileIsDirectoryValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws ValidationException {
        if (!getTarget().isDirectory()) {
            throw new ValidationException(String.format("%s doesn't lead to a directory", getTarget()));
        }
    }
}
