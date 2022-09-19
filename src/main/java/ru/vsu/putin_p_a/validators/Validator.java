package ru.vsu.putin_p_a.validators;

import ru.vsu.putin_p_a.validators.file_validators.ValidationException;

import java.io.FileNotFoundException;

public interface Validator {
    void validate() throws ValidationException;
}
