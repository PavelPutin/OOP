package ru.vsu.putin_p_a.validators.file_validators;

import java.io.File;

public class FileIsFileValidator extends FileValidator{
    public FileIsFileValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws ValidationException {
        if (!getTarget().isFile()) {
            throw new ValidationException(String.format("%s doesn't lead to a file", getTarget()));
        }
    }
}
