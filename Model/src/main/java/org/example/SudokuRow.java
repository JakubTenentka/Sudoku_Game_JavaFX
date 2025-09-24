package org.example;

import org.example.exceptions.InvalidSudokuException;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow extends SudokuGroup implements Cloneable {
    public SudokuRow(final SudokuField[] fields) throws InvalidSudokuException {
        super(fields);
    }

    @Override
    public SudokuRow clone() {
        try {
            SudokuRow clonedRow = (SudokuRow) super.clone();
            List<SudokuField> clonedFields = new ArrayList<>(this.getFields().size());
            for (SudokuField field : this.getFields()) {
                clonedFields.add(field.clone());
            }
            clonedRow.fields = clonedFields;

            return clonedRow;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
