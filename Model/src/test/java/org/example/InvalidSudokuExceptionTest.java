package org.example;

import org.example.exceptions.FIleDaoException;
import org.example.exceptions.InvalidSudokuException;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidSudokuExceptionTest {
    @Test
    void testGetLocalizedMessage_DefaultLocale() {
        Locale.setDefault(Locale.ENGLISH);
        InvalidSudokuException exception = new InvalidSudokuException();

        String localizedMessage = exception.getLocalizedMessage();

        assertEquals("Error with sudoku", localizedMessage);
    }

    @Test
    void testGetLocalizedMessage_PolishLocale() {
        Locale.setDefault(new Locale("pl", "PL"));
        InvalidSudokuException exception = new InvalidSudokuException();

        String localizedMessage = exception.getLocalizedMessage();

        assertEquals("Blad w sudoku", localizedMessage);
    }

    @Test
    void testGetLocalizedMessage_UKLocale() {
        Locale.setDefault(Locale.UK);
        InvalidSudokuException exception = new InvalidSudokuException();

        String localizedMessage = exception.getLocalizedMessage();

        assertEquals("Error with sudoku", localizedMessage);
    }
}
