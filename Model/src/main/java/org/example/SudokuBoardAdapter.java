package org.example;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;

public class SudokuBoardAdapter {
    private final SudokuBoard sudokuBoard;


    public SudokuBoardAdapter(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }

    public JavaBeanIntegerProperty getCellProperty(int row, int col) throws NoSuchMethodException {
        SudokuField sudokuField = sudokuBoard.getField(row, col);
        return JavaBeanIntegerPropertyBuilder.create()
                .bean(sudokuField)
                .name("value")
                .build();

    }
}
