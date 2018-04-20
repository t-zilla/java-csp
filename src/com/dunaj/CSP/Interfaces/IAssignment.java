package com.dunaj.CSP.Interfaces;

import java.util.Map;
import java.util.Set;

/**
 * Interface for value assignment used in CSP Problems (IProblem and CSP Algorithms (IAlgorithm)
 */
public interface IAssignment {
    /**
     * Return new instance of the assignment with added new variable-value pair
     * @param variable Variable
     * @param value Assigned value
     * @return New assignment instance containing new variable-value pair.
     */
    IAssignment assign(IVariable variable, Object value);

    /**
     * Returns map representing the assignment
     * @return Assignment map
     */
    Map<IVariable, Object> getAssignments();

    /**
     * Returns a set of variables that were already assigned a value
     * @return Set of assigned variables
     */
    default Set<IVariable> getAssignedVariables() {
        return getAssignments().keySet();
    }

    /**
     * Returns value assigned to the specified variable
     * @param variable Variable to check
     * @return Assigned value or null
     */
    default Object getValue(IVariable variable) {
        return getAssignments().get(variable);
    }
}