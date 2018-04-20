package com.dunaj.CSP.Problems;

import com.dunaj.CSP.Interfaces.IAssignment;
import com.dunaj.CSP.Interfaces.IProblem;
import com.dunaj.CSP.Interfaces.IVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatinSquare implements IProblem {
    private int size;
    private List<Cell> cells;

    /**
     * Initialise a latin square problem of given size
     * @param size Square size (side length)
     */
    public LatinSquare(int size) {
        this.size = size;
        this.cells = new ArrayList<>(size);
        for (int row=1; row<=size; row++) {
            for (int col=1; col<=size; col++) {
                this.cells.add(new Cell(row, col));
            }
        }
    }

    /**
     * Returns list of all variables involved in the problem
     * @return List of all variables
     */
    @Override
    public List<? extends IVariable> getVariables() {
        return cells;
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
                    Cell cell = (Cell)variable;
                    Cell otherCell = (Cell)otherVariable;
                    if (isSameRowOrColumn(cell, otherCell) &&
                            assignment.getValue(cell).equals(assignment.getValue(otherCell)))
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
            if (isSameRowOrColumn((Cell)variable, (Cell)var))
                restrictedValues.add(value);
            domainRestrictions.put(var, restrictedValues);
        }
        return domainRestrictions;
    }

    /**
     * Checks whether two cells are in the same row or column
     * @param cell1 A cell
     * @param cell2 Another cell
     * @return true if they are in the same row or column
     */
    private boolean isSameRowOrColumn(Cell cell1, Cell cell2) {
        return cell1.getRow() == cell2.getRow() || cell1.getCol() == cell2.getCol();
    }

    /**
     * Class representing a single cell in Latin Square
     */
    class Cell implements IVariable {
        private int row, col;
        private List<Object> domain;

        /**
         * Initialise a cell
         * @param row one-indexed row number
         * @param col one-indexed column number
         */
        Cell(int row, int col) {
            this.row = row;
            this.col = col;
            this.domain = new ArrayList<Object>(size);
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
         * Returns row number of the cell
         * @return one-indexed row number
         */
        public int getRow() {
            return row;
        }

        /**
         * Returns column number of the cell
         * @return one-indexed column number
         */
        public int getCol() {
            return col;
        }

        /**
         * Returns string representation of the cell
         * @return String representing the cell
         */
        public String toString() {
            return "Cell (" + row + "," + col + ")";
        }
    }
}
