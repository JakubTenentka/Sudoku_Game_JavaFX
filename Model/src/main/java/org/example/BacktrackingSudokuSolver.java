package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class BacktrackingSudokuSolver implements SudokuSolver, Serializable, Cloneable {
    @Override
    public boolean solve(SudokuBoard board) {
        List<Integer> mixedList = Arrays.asList(1,2,3,4,5,6,7,8,9);
        Collections.shuffle(mixedList);


        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int cell = board.get(row, column);
                if (cell == 0) {
                    for (int i : mixedList) {
                        if (validation(board,row, column, i)) {
                            board.set(row, column, i);
                            if (solve(board)) {
                                return true;
                            }
                            board.set(row, column, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;


    }

    public boolean validation(SudokuBoard board, int row, int column, int value) {

        for (int i = 0; i < 9; i++) {
            if (board.get(row, i) == value) {
                return false;
            }
        }


        for (int i = 0; i < 9; i++) {
            if (board.get(i, column) == value) {
                return false;
            }
        }

        int firstRow = row - (row % 3);
        int firstColumn = column - (column % 3);
        for (int i = firstRow; i < firstRow + 3; i++) {
            for (int j = firstColumn; j < firstColumn + 3; j++) {
                if (board.get(i, j) == value) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
    return  new HashCodeBuilder(17,37)
            .append(getClass().getName())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("class", getClass().getSimpleName())
                .toString();

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return new EqualsBuilder()
                .append(getClass().getName(), obj.getClass().getName())
                .isEquals();
    }

    @Override
    public BacktrackingSudokuSolver clone() {
        try {
            return (BacktrackingSudokuSolver) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

