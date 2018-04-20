package com.dunaj.CSP.Interfaces;

import java.util.List;

/**
 * Interface for variables involved in CSP problems (IProblem)
 */
public interface IVariable {
    /**
     * Returns the domain of the variable
     * @return List of values that can be assigned to this variable
     */
    List<Object> getDomain();
}