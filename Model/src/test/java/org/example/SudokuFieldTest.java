package org.example;
import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SudokuFieldTest {
    @Test
    public void toStringTest(){
        SudokuField field = new SudokuField();
        field.setValue(2);
        assertEquals("2",field.toString());


    }

    @Test public void EqualsTest(){
        SudokuField field = new SudokuField();
        field.setValue(2);
        SudokuField field2 = new SudokuField();
        field2.setValue(2);
        assertEquals(field,field2);
        Assertions.assertTrue(field.equals(field));
        Assertions.assertFalse(field.equals(null));
        Assertions.assertFalse(field.equals(new Object()));
    }

    @Test
    public void testCompareTo_SameValue() {
        SudokuField field1 = new SudokuField();
        field1.setValue(5);

        SudokuField field2 = new SudokuField();
        field2.setValue(5);

        assertEquals(0, field1.compareTo(field2), "Fields with the same value should return 0");
    }

    @Test
    public void testCompareTo_LowerValue() {
        SudokuField field1 = new SudokuField();
        field1.setValue(3);

        SudokuField field2 = new SudokuField();
        field2.setValue(5);

        assertTrue(field1.compareTo(field2) < 0, "Field1 with lower value should be negative when compared to field2 with higher value");
    }

    @Test
    public void testCompareTo_HigherValue() {
        SudokuField field1 = new SudokuField();
        field1.setValue(8);

        SudokuField field2 = new SudokuField();
        field2.setValue(5);

        assertTrue(field1.compareTo(field2) > 0, "Field1 with higher value should be positive when compared to field2 with lower value");
    }

    @Test
    public void testCompareTo_NullValue() {
        SudokuField field1 = new SudokuField();
        field1.setValue(5);

        assertThrows(NullPointerException.class, () -> {
            field1.compareTo(null);
        }, "Comparing to null should throw NullPointerException");
    }
}
