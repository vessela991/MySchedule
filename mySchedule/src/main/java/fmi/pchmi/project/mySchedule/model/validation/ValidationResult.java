package fmi.pchmi.project.mySchedule.model.validation;

import lombok.Getter;

@Getter
public class ValidationResult {
    private final boolean isSuccess;
    private final String validationError;

    private ValidationResult(boolean isSuccess, String validationError) {
        this.isSuccess = isSuccess;
        this.validationError = validationError;
    }

    public static ValidationResult success() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult failure(String validationError) {
        return new ValidationResult(false, validationError);
    }
}
