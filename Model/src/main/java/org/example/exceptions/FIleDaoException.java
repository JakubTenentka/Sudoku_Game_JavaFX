package org.example.exceptions;


import java.util.ResourceBundle;

public class FIleDaoException extends ModelException {

    public enum ExceptionType {
        INPUT_PROBLEM,
        CLASS_PROBLEM,
        FILE_PROBLEM
    }

    public FIleDaoException() {

    }

    public FIleDaoException(ExceptionType type) {
        super(type.name());
    }

    public FIleDaoException(ExceptionType type, Throwable cause) {
        super(type.name(), cause);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle messageBundle = ResourceBundle.getBundle("FileDaoExceptionBundle");
        return messageBundle.getString(this.getMessage());
    }

}