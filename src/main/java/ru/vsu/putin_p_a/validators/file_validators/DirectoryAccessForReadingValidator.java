package ru.vsu.putin_p_a.validators.file_validators;

import java.io.File;
import java.io.FileNotFoundException;

public class DirectoryAccessForReadingValidator extends FileValidator{
    public DirectoryAccessForReadingValidator(File target) {
        super(target);
    }

    @Override
    public void validate() throws FileNotFoundException {
        new FileIsDirectoryValidator(getTarget()).validate();
        new FileCanReadValidator(getTarget()).validate();
    }
}
