package org.example;

import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    public void checkDifferentBoards() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        sudokuBoard1.solveGame();
        sudokuBoard2.solveGame();

        assertNotEquals(sudokuBoard1, sudokuBoard2);
    }

    @Test
    public void checkSudoku() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        SudokuField[][] copiedBoard = sudokuBoard.getBoardCopy();


        for (int i = 0; i < 9; i++) {
            SudokuField[] row = copiedBoard[i];
            assertFalse(areThereDuplicates(row));
        }


        for (int i = 0; i < 9; i++) {
            SudokuField[] column = new SudokuField[9];
            for (int j = 0; j < 9; j++) {
                column[j] = copiedBoard[j][i];
            }
            assertFalse(areThereDuplicates(column));
        }


        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                SudokuField[] block = new SudokuField[9];
                int index = 0;
                for (int i = row; i < row + 3; i++) {
                    for (int j = col; j < col + 3; j++) {
                        block[index++] = copiedBoard[i][j];
                    }
                }
                assertFalse(areThereDuplicates(block));
            }
        }
    }


    boolean areThereDuplicates(SudokuField[] fields) {
        Set<Integer> uniqueValues = new HashSet<>();
        for (SudokuField field : fields) {
            int value = field.getValue();
            if (value != 0) {
                if (uniqueValues.contains(value)) {
                    return true;
                }
                uniqueValues.add(value);
            }
        }
        return false;
    }

    @Test
    public void EqualsTest(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuSolver solver2 = new BacktrackingSudokuSolver();
        Assertions.assertEquals(solver, solver2);
        Assertions.assertTrue(solver.equals(solver));
        Assertions.assertFalse(solver.equals(null));
        Assertions.assertFalse(solver.equals(new Object()));



    }
}
