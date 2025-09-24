package org.example;

import org.example.SudokuBox;
import org.example.SudokuField;
import org.example.exceptions.InvalidSudokuException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {

    private SudokuBox originalBox;

    @BeforeEach
    public void setUp() throws InvalidSudokuException {
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }
        originalBox = new SudokuBox(fields);
    }

    @Test
    public void testClone_createsIndependentCopy() {
        SudokuBox clonedBox = originalBox.clone();
        assertNotSame(originalBox, clonedBox, "The cloned box should be a different object.");

        for (int i = 0; i < 9; i++) {
            assertEquals(originalBox.getFields().get(i).getValue(), clonedBox.getFields().get(i).getValue(),
                    "The field values should be the same.");
        }
        for (int i = 0; i < 9; i++) {
            assertNotSame(originalBox.getFields().get(i), clonedBox.getFields().get(i),
                    "The fields should be different objects.");
        }
    }

    @Test
    public void testClone_doesNotAffectOriginal() {
        SudokuBox clonedBox = originalBox.clone();
        clonedBox.getFields().get(0).setValue(10);
        assertNotEquals(originalBox.getFields().get(0).getValue(), clonedBox.getFields().get(0).getValue(),
                "The original box's field value should not change.");
    }
}
