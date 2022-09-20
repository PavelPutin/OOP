package ru.vsu.putin_p_a.validators.file_validators;

import java.io.File;

public class FileCanReadValidator extends FileValidator {
    public FileCanReadValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws ValidationException {
        if (!this.getTarget().canRead()) {
            throw new RuntimeException(String.format("%s file isn't readable", this.getTarget()));
        }
    }
}
