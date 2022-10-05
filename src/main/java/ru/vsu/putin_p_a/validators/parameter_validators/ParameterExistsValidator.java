package ru.vsu.putin_p_a.validators.parameter_validators;

import ru.vsu.putin_p_a.validators.Validator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;

public class ParameterExistsValidator implements Validator {
    private final String name, value;

    public ParameterExistsValidator(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void validate() throws ValidationException {
        if (value == null) {
            throw new ValidationException(String.format("Parameter %s doesn't exist in configuration file!", name));
        }
    }
}
