package org.example;

import org.example.exceptions.InvalidSudokuException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {

    private SudokuColumn originalColumn;

    @BeforeEach
    public void setUp() throws InvalidSudokuException {
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }
        originalColumn = new SudokuColumn(fields);
    }

    @Test
    public void testClone_createsIndependentCopy() {
        SudokuColumn clonedColumn = originalColumn.clone();
        assertNotSame(originalColumn, clonedColumn, "The cloned column should be a different object.");
        for (int i = 0; i < 9; i++) {
            assertEquals(originalColumn.getFields().get(i).getValue(), clonedColumn.getFields().get(i).getValue(),
                    "The field values should be the same.");
        }
        for (int i = 0; i < 9; i++) {
            assertNotSame(originalColumn.getFields().get(i), clonedColumn.getFields().get(i),
                    "The fields should be different objects.");
        }
    }

    @Test
    public void testClone_doesNotAffectOriginal() {
        SudokuColumn clonedColumn = originalColumn.clone();
        clonedColumn.getFields().get(0).setValue(10);
        assertNotEquals(originalColumn.getFields().get(0).getValue(), clonedColumn.getFields().get(0).getValue(),
                "The original column's field value should not change.");
    }
}
