package ru.vsu.putin_p_a.validators.port_validator;

import ru.vsu.putin_p_a.validators.Validator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;

public class PortValidator implements Validator {
    private String target;

    public PortValidator(String target) {
        this.target = target;
    }

    @Override
    public void validate() throws ValidationException {
        try {
            Integer.parseInt(target);
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("Port %s isn't valid", target));
        }
    }
}
