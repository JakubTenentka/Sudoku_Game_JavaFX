package org.example.exceptions;

import java.util.ResourceBundle;

public class InvalidSudokuException extends ModelException {
    public InvalidSudokuException() {
        super("DEFAULT_SUDOKU_EXCEPTION_MESSAGE");
    }

    public InvalidSudokuException(String message) {
        super(message);
    }

    public InvalidSudokuException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle messageBundle = ResourceBundle.getBundle("SudokuExceptionBundle");
        return messageBundle.getString(this.getMessage());
    }
}
