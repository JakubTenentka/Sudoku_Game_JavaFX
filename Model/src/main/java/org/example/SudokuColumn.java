package org.example;

import org.example.exceptions.InvalidSudokuException;

import java.util.ArrayList;
import java.util.List;

public class SudokuColumn  extends SudokuGroup implements Cloneable {
    public SudokuColumn(final SudokuField[] fields) throws InvalidSudokuException {
        super(fields);
    }

    @Override
    public SudokuColumn clone() {
        try {
            SudokuColumn clonedColumn = (SudokuColumn) super.clone();
            List<SudokuField> clonedFields = new ArrayList<>(this.getFields().size());
            for (SudokuField field : this.getFields()) {
                clonedFields.add(field.clone());
            }
            clonedColumn.fields = clonedFields;

            return clonedColumn;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
