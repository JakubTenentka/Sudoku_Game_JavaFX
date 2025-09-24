package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private int value;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        int oldValue = this.value;
        this.value = value;
        propertyChangeSupport.firePropertyChange("value", oldValue, value);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,37)
                .append(value)
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
        SudokuField other = (SudokuField) obj;
        return  new EqualsBuilder()
                .append(value, other.value)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("value",value)
                .toString();
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {

        return (SudokuField) super.clone();
    }



    @Override
    public int compareTo(SudokuField otherField) {
        if (otherField == null) {
            throw new NullPointerException("otherField must not be null");
        }
        return Integer.compare(this.value, otherField.value);
        /*
 field < otherField - zwraca wartość ujemną
 field = otherField - zwraca 0
 field > otherField - zwraca wartość dodatnią
 */
    }
}
