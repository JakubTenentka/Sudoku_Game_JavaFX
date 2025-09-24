package org.example.exceptions;

import java.util.ResourceBundle;

public class ModelException extends Exception {
    public ModelException() {
        super("DEFAULT_MODEL_MESSAGE");
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle messageBundle = ResourceBundle.getBundle("ModelExceptionBundle");
        return messageBundle.getString(this.getMessage());
    }
}
