package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.example.exceptions.InvalidSudokuException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SudokuGroup implements Cloneable {
    List<SudokuField> fields;

    public SudokuGroup(SudokuField[] fields) throws InvalidSudokuException {
        if (fields.length != 9) {
            throw new InvalidSudokuException("SudokuGroup must have exactly 9 fields.");
        }
        this.fields = Arrays.asList(fields);
    }

    public boolean verify() {
        for (int i = 0; i < fields.size(); i++) {
            for (int j = i + 1; j < fields.size(); j++) {
                if (fields.get(i).getValue() == fields.get(j).getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<SudokuField> getFields() {
        return fields;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fields)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SudokuGroup other = (SudokuGroup) obj;

        return new EqualsBuilder()
                .append(fields, other.fields)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("fields", fields)
                .toString();
    }

    @Override
    public SudokuGroup clone() throws CloneNotSupportedException {
        try {
            SudokuGroup clone = (SudokuGroup) super.clone();


            if (this.fields != null) {
                clone.fields = new ArrayList<>();
                for (SudokuField field : this.fields) {
                    clone.fields.add((SudokuField) field.clone());
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
