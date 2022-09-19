package ru.vsu.putin_p_a.validators.file_validators;

import ru.vsu.putin_p_a.validators.Validator;

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
