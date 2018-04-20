package com.dunaj.CSP.Problems;

import com.dunaj.CSP.Interfaces.IAssignment;
import com.dunaj.CSP.Interfaces.IProblem;
import com.dunaj.CSP.Interfaces.IVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NQueens implements IProblem {
    private int size;
    private List<Row> rows;

    /**
     * Initialise N-Queens problem of given size
     * @param size Chessboard side length (N)
     */
    public NQueens(int size) {
        this.size = size;
        this.rows = new ArrayList<>(size);
        for (int n=1; n<=size; n++) {
            this.rows.add(new Row(n));
        }
    }

    /**
     * Returns list of all variables involved in the problem
     * @return List of all variables
     */
    @Override
    public List<? extends IVariable> getVariables() {
        return rows;
    }

    /**
     * Checks whether specified assignment doesn't violate any constraints.
     * An assignment can be incomplete but still consistent.
     * A solution is complete and consistent.
     * @param assignment Tested assignment
     * @return true if assignment is consistent
     */
    @Override
    public boolean isConsistent(IAssignment assignment) {
        for (IVariable variable : assignment.getAssignedVariables()) {
            for (IVariable otherVariable : assignment.getAssignedVariables()) {
                if (otherVariable != variable) {
                    int deltaCol = getDeltaCol((Row) variable, (Row) otherVariable, assignment);
                    int deltaRow = getDeltaRow((Row) variable, (Row) otherVariable);
                    if (deltaCol == 0 || deltaCol == deltaRow)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines which values have to be removed from all domains
     * after assigning specified value to specified variable.
     * @param variable Variable to assign value to
     * @param value Value to assign
     * @return Map containing list of values that have to be removed from domains
     */
    @Override
    public Map<IVariable, List<Object>> getDomainRestrictions(IVariable variable, Object value) {
        Map<IVariable, List<Object>> domainRestrictions = new HashMap<>();
        for (IVariable var : getVariables()) {
            List<Object> restrictedValues = new ArrayList<Object>();
            restrictedValues.add(value);
            int deltaRow = getDeltaRow((Row)variable, (Row)var);
            if (deltaRow != 0) {
                if ((Integer)value - deltaRow >= 1)
                    restrictedValues.add((Integer)value - deltaRow);
                if ((Integer)value + deltaRow <= size)
                    restrictedValues.add((Integer)value + deltaRow);
            }
            domainRestrictions.put(var, restrictedValues);
        }
        return domainRestrictions;
    }

    /**
     * Get absolute difference between row numbers
     * @param row1 A row
     * @param row2 Another row
     * @return Absolute difference (0 or more)
     */
    private int getDeltaRow(Row row1, Row row2) {
        return Math.abs(row2.getI() - row1.getI());
    }

    /**
     * Get absolute difference between column numbers where Queens are
     * @param row1 A row
     * @param row2 Another row
     * @param assignment Assignment determining where Queens are in these rows
     * @return Absolute difference (0 or more)
     */
    private int getDeltaCol(Row row1, Row row2, IAssignment assignment) {
        return Math.abs(
                (Integer)assignment.getValue(row1) - (Integer)assignment.getValue(row2)
        );
    }

    /**
     * Class representing a chessboard row in NQueens problem
     */
    class Row implements IVariable {
        private List<Object> domain;
        private int i;

        /**
         * Initialises a new row
         * @param i one-indexed row number
         */
        Row(int i) {
            this.i = i;
            this.domain = new ArrayList<>(size);
            for (int n=1; n<=size; n++) {
                this.domain.add(n);
            }
        }

        /**
         * Returns the domain of the variable
         * @return List of values that can be assigned to this variable
         */
        @Override
        public List<Object> getDomain() {
            return domain;
        }

        /**
         * Returns row number
         * @return one-indexed row number
         */
        public int getI() {
            return i;
        }

        /**
         * Returns string representation of the row
         * @return String representing the row
         */
        public String toString() {
            return "Row " + i;
        }
    }
}