package org.example;

import org.example.SudokuRow;
import org.example.SudokuField;
import org.example.exceptions.InvalidSudokuException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {

    private SudokuRow originalRow;

    @BeforeEach
    public void setUp() throws InvalidSudokuException {
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }
        originalRow = new SudokuRow(fields);
    }

    @Test
    public void testClone_createsIndependentCopy() {
        SudokuRow clonedRow = originalRow.clone();
        assertNotSame(originalRow, clonedRow, "The cloned row should be a different object.");
        for (int i = 0; i < 9; i++) {
            assertEquals(originalRow.getFields().get(i).getValue(), clonedRow.getFields().get(i).getValue(),
                    "The field values should be the same.");
        }
        for (int i = 0; i < 9; i++) {
            assertNotSame(originalRow.getFields().get(i), clonedRow.getFields().get(i),
                    "The fields should be different objects.");
        }
    }

    @Test
    public void testClone_doesNotAffectOriginal() {
        SudokuRow clonedRow = originalRow.clone();
        clonedRow.getFields().get(0).setValue(10);
        assertNotEquals(originalRow.getFields().get(0).getValue(), clonedRow.getFields().get(0).getValue(),
                "The original row's field value should not change.");
    }
}
