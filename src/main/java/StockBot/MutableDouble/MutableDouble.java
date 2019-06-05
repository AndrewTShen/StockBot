/**
 * Wrapper for standard Java Double class in order to make it mutable.
 * Disclaimer: Code to make Double Mutable from StackOverflow
 */

package MutableDouble;

import java.util.*;

public class MutableDouble {

    private double value;

    /**
     * Constructor for the Mutable Double
     * @param value Value to set the new Mutable Double to.
     */
    public MutableDouble(double value) {
        this.value = value;
    }

    /**
     * Returns the current value of the mutable double.
     * @return Current value of the mutable double.
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Changes the value of the Mutable Double.
     * @param value The new value of the Mutable Double.
     */
    public void setValue(double value) {
        this.value = value;
    }
}
