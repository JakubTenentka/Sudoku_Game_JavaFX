package org.example;

import org.example.exceptions.InvalidSudokuException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.example.SudokuBoard;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard sudokuBoard = new SudokuBoard(solver);



    @Test
    public void GettersAndSettersTest(){
        assertEquals(sudokuBoard.get(0,0),0);
        sudokuBoard.set(0,0,1);
        assertEquals(sudokuBoard.get(0,0),1);
        sudokuBoard.set(0,0,0);

    }


    @Test
    public void equalsTest(){
        assertTrue(sudokuBoard.equals(sudokuBoard));
        assertFalse(sudokuBoard.equals(null));
    }

    @Test
    public void checkValidBoardTest(){
        sudokuBoard.set(0,0,1);
        assertTrue(sudokuBoard.checkBoard(0,1,2));

    }
    @Test
    public void checkUnvalidBoardTest(){
        sudokuBoard.set(0,0,1);
        assertFalse(sudokuBoard.checkBoard(0,1,1));
        assertFalse(sudokuBoard.checkBoard(1,0,1));
        assertFalse(sudokuBoard.checkBoard(1,1,1));

    }
    @Test
    public void checkGetRow() throws InvalidSudokuException {
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        int rowIndex = 2;

        SudokuRow row = sudokuBoard.getRow(rowIndex);

        assertTrue(row instanceof SudokuRow);

        List<SudokuField> fields = row.getFields();
        assertEquals(9, fields.size());

        for (int col = 0; col < 9; col++) {
            assertEquals(sudokuBoard.get(rowIndex,col), fields.get(col).getValue());
        }

        assertTrue(row.verify());
    }

    @Test
    public void checkGetColumn() throws InvalidSudokuException {
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        int columnIndex = 2;

        SudokuColumn column = sudokuBoard.getColumn(columnIndex);

        assertTrue(column instanceof SudokuColumn);

        List<SudokuField> fields = column.getFields();
        assertEquals(9, fields.size());

        for (int row = 0; row < 9; row++) {
            assertEquals(sudokuBoard.get(row, columnIndex), fields.get(row).getValue());
        }

        assertTrue(column.verify());
    }
    @Test
    public void checkGetBox() throws InvalidSudokuException {
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);
        int counter=1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                sudokuBoard2.set(row,col,counter);
                counter++;
            }
        }
        int rowBoxIndex=0;
        int columnBoxIndex=0;
        SudokuBox box = sudokuBoard2.getBox(rowBoxIndex,columnBoxIndex);
        assertTrue(box instanceof SudokuBox);

        List<SudokuField> fields = box.getFields();
        assertEquals(9, fields.size());

        int[][] expectedValues = {
                {1,2,3},
                {4,5,6},
                {7,8,9},
        };
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int fieldIndex = i*3+j;
                assertEquals(expectedValues[i][j], fields.get(fieldIndex).getValue());
            }
        }
        assertTrue(box.verify());
    }

    @Test
    public void testEqualsWithDifferentClass() {
        SudokuBoard board = new SudokuBoard(solver);
        String otherObject = "not a SudokuBoard";
        assertNotEquals(board, otherObject);
    }
    @Test
    public void hashCodeTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                sudokuBoard2.set(i, j, i * 9 + j);
            }
        }
        SudokuBoard sudokuBoardDifferent = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoardDifferent.set(i, j, (i + j) % 9);
            }
        }
        assertNotEquals(sudokuBoard1.hashCode(), sudokuBoardDifferent.hashCode());

    }

    @Nested
    class toStringTestLogger {

        private static final Logger logger = LoggerFactory.getLogger(SudokuBoardTest.class.getName());

        @Test
        public void toStringTest() {
            SudokuBoard sudokuBoard = new SudokuBoard(solver);
            sudokuBoard.solveGame();

            logger.info(sudokuBoard.toString());
        }
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        SudokuBoard clonedBoard = (SudokuBoard) sudokuBoard.clone();
        assertNotSame(sudokuBoard, clonedBoard, "Klon powinien nie być tą samą instancją co oryginał");
        assertArrayEquals(sudokuBoard.getBoardCopy(), clonedBoard.getBoardCopy());
    }



}
