package org.example;

import org.example.exceptions.FIleDaoException;
import org.example.exceptions.ModelException;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelExceptionTest {

    @Test
    void testGetLocalizedMessage_DefaultLocale() {
        Locale.setDefault(Locale.ENGLISH);
        ModelException exception = new ModelException();

        String localizedMessage = exception.getLocalizedMessage();


        assertEquals("Error with Model layer", localizedMessage);
    }

    @Test
    void testGetLocalizedMessage_PolishLocale() {

        Locale.setDefault(new Locale("pl", "PL"));
        ModelException exception = new ModelException();

        String localizedMessage = exception.getLocalizedMessage();

        assertEquals("Problem z warstwa danych", localizedMessage);
    }

    @Test
    void testGetLocalizedMessage_UKLocale() {
        Locale.setDefault(Locale.UK);
        ModelException exception = new ModelException();

        String localizedMessage = exception.getLocalizedMessage();

        assertEquals("Error with Model layer", localizedMessage);
    }
}
