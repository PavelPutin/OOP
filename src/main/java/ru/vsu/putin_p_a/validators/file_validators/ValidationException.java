package ru.vsu.putin_p_a.validators.file_validators;

import java.io.IOException;

public class ValidationException extends IOException {
    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }
}
