package org.project.speakeval.exception.utils;

public enum ExceptionCodes implements ExceptionKeyAndMessage {

    ENTITY_NOT_FOUND_EXCEPTION("1000", "The requested entity could not be found using the provided parameters"),
    CONSTRAINT_VIOLATION_EXCEPTION("1001", "Request contains invalid values that violate one or more constraints"),
    ARGUMENT_VALIDATION_EXCEPTION("1002", "One or more request parameters failed validation"),
    ILLEGAL_ARGUMENT_EXCEPTION("1003", "An invalid argument was provided to the operation"),
    RECORD_ALREADY_EXISTS("1004", "A record with the provided details already exists"),
    ACCESS_DENIED_EXCEPTION("1005", "You do not have permission to perform this operation"),
    UNKNOWN_EXCEPTION("1006", "An unexpected error occurred. Please try again later");

    private final String message;
    private String exceptionKey;

    ExceptionCodes(String exceptionKey, String message) {
        this.exceptionKey = exceptionKey;
        this.message = message;
    }

    @Override
    public String getExceptionKey() {
        return exceptionKey;
    }

    @Override
    public String getExceptionMessage() {
        return message;
    }

    public ExceptionCodes exceptionKey(String exceptionKey) {
        this.exceptionKey = exceptionKey;
        return this;
    }
}
