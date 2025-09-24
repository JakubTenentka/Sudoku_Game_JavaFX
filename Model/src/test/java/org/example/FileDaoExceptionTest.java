package org.example;

import org.example.exceptions.FIleDaoException;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileDaoExceptionTest {

    @Test
    void testGetLocalizedMessage_DefaultLocale() {
        Locale.setDefault(Locale.ENGLISH);
        FIleDaoException exception = new FIleDaoException(FIleDaoException.ExceptionType.INPUT_PROBLEM);

        String localizedMessage = exception.getLocalizedMessage();

        assertEquals("Error with input", localizedMessage);
    }

    @Test
    void testGetLocalizedMessage_PolishLocale() {
        Locale.setDefault(new Locale("pl", "PL"));
        FIleDaoException exception = new FIleDaoException(FIleDaoException.ExceptionType.INPUT_PROBLEM);

        String localizedMessage = exception.getLocalizedMessage();

        assertEquals("Problem z wejsciem", localizedMessage);
    }

    @Test
    void testGetLocalizedMessage_UKLocale() {
        Locale.setDefault(Locale.UK);
        FIleDaoException exception = new FIleDaoException(FIleDaoException.ExceptionType.INPUT_PROBLEM);

        String localizedMessage = exception.getLocalizedMessage();

        assertEquals("Error with input", localizedMessage);
    }
}
