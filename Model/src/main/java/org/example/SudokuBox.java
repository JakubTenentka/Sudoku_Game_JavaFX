package org.example;


import org.example.exceptions.InvalidSudokuException;

import java.util.ArrayList;
import java.util.List;

public class SudokuBox extends SudokuGroup implements Cloneable {
    public SudokuBox(SudokuField[] fields) throws InvalidSudokuException {
        super(fields);
    }

    @Override
    public SudokuBox clone() {
        try {
            SudokuBox clonedBox = (SudokuBox) super.clone();
            List<SudokuField> clonedFields = new ArrayList<>(this.getFields().size());
            for (SudokuField field : this.getFields()) {
                clonedFields.add(field.clone());
            }
            clonedBox.fields = clonedFields;

            return clonedBox;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}


