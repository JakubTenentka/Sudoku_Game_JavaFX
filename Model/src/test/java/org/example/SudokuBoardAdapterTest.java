package org.example;


import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardAdapterTest {

    private SudokuBoard sudokuBoard;
    private SudokuBoardAdapter sudokuBoardAdapter;

    @BeforeEach
    public void setUp() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoardAdapter = new SudokuBoardAdapter(sudokuBoard);
    }

    @Test
    public void testConstructor() {
        assertNotNull(sudokuBoardAdapter);
    }

    @Test
    public void testGetCellProperty() throws NoSuchMethodException {
        sudokuBoard.set(0, 0, 5);
        JavaBeanIntegerProperty property = sudokuBoardAdapter.getCellProperty(0, 0);
        assertNotNull(property);
        assertEquals(5, property.get());
    }

    @Test
    public void testGetCellPropertyInitialValue() throws NoSuchMethodException {
        JavaBeanIntegerProperty property = sudokuBoardAdapter.getCellProperty(0, 0);
        assertEquals(0, property.get());
    }

    @Test
    public void testGetCellPropertyAfterUpdate() throws NoSuchMethodException {
        sudokuBoard.set(0, 0, 7);
        JavaBeanIntegerProperty property = sudokuBoardAdapter.getCellProperty(0, 0);
        assertEquals(7, property.get());
    }


}
