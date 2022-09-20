package ru.vsu.putin_p_a.validators;

import ru.vsu.putin_p_a.validators.file_validators.ValidationException;

public interface Validator {
    void validate() throws ValidationException;
}
