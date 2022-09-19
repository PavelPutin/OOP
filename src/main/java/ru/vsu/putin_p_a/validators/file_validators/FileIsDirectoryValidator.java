package ru.vsu.putin_p_a.validators.file_validators;

import java.io.File;
import java.io.FileNotFoundException;

public class FileIsDirectoryValidator extends FileValidator {
    public FileIsDirectoryValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws FileNotFoundException {
        if (!getTarget().isDirectory()) {
            throw new IllegalArgumentException(String.format("%s doesn't lead to a directory", getTarget()));
        }
    }
}
