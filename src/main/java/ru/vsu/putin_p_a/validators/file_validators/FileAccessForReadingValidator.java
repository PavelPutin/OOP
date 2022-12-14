package ru.vsu.putin_p_a.validators.file_validators;

import java.io.File;

public class FileAccessForReadingValidator extends FileValidator {
    public FileAccessForReadingValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws ValidationException {
        new FileIsFileValidator(getTarget()).validate();
        new FileCanReadValidator(getTarget()).validate();
    }
}
