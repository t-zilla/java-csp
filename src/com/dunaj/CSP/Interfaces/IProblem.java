package com.dunaj.CSP.Interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface for CSP Problems that consist of variables (IVariable)
 * and are solved by CSP Algorithms (IAlgorithm)
 */
public interface IProblem {
    /**
     * Returns list of all variables involved in the problem
     * @return List of all variables
     */
    List<? extends IVariable> getVariables();

    /**
     * Checks whether specified assignment doesn't violate any constraints.
     * An assignment can be incomplete but still consistent.
     * A solution is complete and consistent.
     * @param assignment Tested assignment
     * @return true if assignment is consistent
     */
    boolean isConsistent(IAssignment assignment);

    /**
     * Checks whether specified assignment is complete.
     * A complete assignment means all variables were assigned.
     * A solution is complete and consistent.
     * @param assignment Tested assignment
     * @return true if assignment is complete
     */
    default boolean isComplete(IAssignment assignment) {
        for (IVariable variable : getVariables()) {
            if (assignment.getValue(variable) == null)
                return false;
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
    Map<IVariable, List<Object>> getDomainRestrictions(IVariable variable, Object value);

    /**
     * Returns list of variables that don't have any value assigned in the specified assignment.
     * @param assignment Tested assignment
     * @return List of unassigned variables
     */
    default List<? extends IVariable> getUnassignedVariables(IAssignment assignment) {
        List<IVariable> unassignedVariables = new ArrayList<>();
        for (IVariable variable : getVariables())
            if (assignment.getValue(variable) == null)
                unassignedVariables.add(variable);
        return unassignedVariables;
    }
}
