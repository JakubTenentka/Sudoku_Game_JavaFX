package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.example.exceptions.InvalidSudokuException;

import java.io.Serializable;
import java.util.Objects;





public class SudokuBoard implements Serializable, Cloneable {
    public SudokuBoard(SudokuSolver solver) {
        Objects.requireNonNull(solver);
        this.solver = solver;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }

    }

    public SudokuField[][] getBoardCopy() {
        SudokuField[][] boardCopy = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardCopy[i][j] = new SudokuField();
                boardCopy[i][j].setValue(board[i][j].getValue());
            }
        }
        return boardCopy;
    }


    private SudokuField [][] board = new SudokuField[9][9];
    private SudokuSolver solver;

    public int get(int row, int column) {
        return board[row][column].getValue();
    }

    public void set(int row, int column, int i) {
        board[row][column].setValue(i);
    }

    public SudokuField getField(int row, int col) {
        return board[row][col];
    }


    public SudokuRow getRow(int row) throws InvalidSudokuException {
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = board[row][i];
        }

        return new SudokuRow(fields);
    }

    public SudokuColumn getColumn(int column) throws InvalidSudokuException {
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = board[i][column];
        }
        return new SudokuColumn(fields);
    }

    public SudokuBox getBox(int row, int column) throws InvalidSudokuException {
        SudokuField[] fields = new SudokuField[9];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[index++] = board[row * 3 + i][column * 3 + j];
            }
        }

        return new SudokuBox(fields);
    }


   boolean checkBoard(int row, int column, int i) {

        for (int w = 0; w < board.length; w++) {
            if (board[w][column].getValue() == i) {
                return false;
            }
        }


        for (int k = 0;k < board.length; k++) {
            if (board[row][k].getValue() == i) {
                return false;
            }
        }


        int firstRow = row - (row % 3);
        int firstColumn = column - (column % 3);
        for (int x = firstRow; x < firstRow + 3; x++) {
            for (int y = firstColumn; y < firstColumn + 3;y++) {
                if (board[x][y].getValue() == i) {
                    return false;
                }
            }
        }
       return true;
    }

    public void solveGame() {
        solver.solve(this);


    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuBoard other = (SudokuBoard) obj;
        return new EqualsBuilder()
                .append(board,other.board)
                .append(solver, other.solver)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(board)
                .append(solver)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("board", board)
                .append("solver", solver)
                .toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SudokuBoard clonedBoard = (SudokuBoard) super.clone();

        clonedBoard.board = new SudokuField[9][9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                clonedBoard.board[i][j] = (SudokuField) board[i][j].clone();
            }
        }

        if (solver != null) {
            clonedBoard.solver = (SudokuSolver) solver.clone();
        }

        return clonedBoard;
    }
}

