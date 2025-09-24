package org.example;

public class Repository {

    public SudokuBoard sudokuBoard;

    public Object createInstance() throws CloneNotSupportedException {
        return sudokuBoard.clone();
    }
}
