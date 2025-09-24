package org.example;

import org.example.exceptions.InvalidSudokuException;
import org.junit.jupiter.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuGroupTest {

    @Test
    public void IllegalArgumentEXceptionTest(){
        SudokuField[] fields = new SudokuField[10];
        assertThrows(IllegalArgumentException.class, ()->{new SudokuRow(fields);});

    }

    @Test
    public void FalseVerify() throws InvalidSudokuException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
      SudokuBoard sudokuBoard = new SudokuBoard(solver);
      sudokuBoard.set(0,0,2);
      sudokuBoard.set(1,0,2);
      SudokuColumn column = sudokuBoard.getColumn(0);
      assertFalse(column.verify());

    }

    @Test
    public void hashCodeTest() throws InvalidSudokuException {
        SudokuField[] fields = new SudokuField[9];
        SudokuGroup sudokuGroup = new SudokuGroup(fields);
        SudokuField[] fields1 = new SudokuField[9];
        SudokuGroup sudokuGroup1 = new SudokuGroup(fields1);
        Assertions.assertEquals(sudokuGroup.hashCode(), sudokuGroup1.hashCode());

    }

    @Test
    public void equalsTest() throws InvalidSudokuException {
        SudokuField[] fields = new SudokuField[9];
        SudokuGroup sudokuGroup = new SudokuGroup(fields);
        SudokuField[] fields1 = new SudokuField[9];
        SudokuGroup sudokuGroup1 = new SudokuGroup(fields1);
        Assertions.assertEquals(sudokuGroup, sudokuGroup1);
        Assertions.assertTrue(sudokuGroup.equals(sudokuGroup));
        Assertions.assertFalse(sudokuGroup.equals(null));
        Assertions.assertFalse(sudokuGroup.equals(new Object()));
    }
    @Nested
    class toStringTestLogger {
        private static final Logger logger = LoggerFactory.getLogger(SudokuGroupTest.class.getName());
        @Test
        public void toStringTest() throws InvalidSudokuException {
            SudokuField[] fields = new SudokuField[9];
            for (int i = 1; i < fields.length + 1; i++) {
                fields[i - 1] = new SudokuField();
                fields[i - 1].setValue(i);
            }
            SudokuGroup sudokuGroup = new SudokuGroup(fields);
            logger.info(sudokuGroup.toString());



        }
    }

}
