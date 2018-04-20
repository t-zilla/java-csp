package com.dunaj.CSP.Interfaces;

import java.util.List;
import java.util.Map;

/**
 * Interface for algorithms that solve CSP problems (IProblem)
 */
public interface IAlgorithm {
    /**
     * Attempts to solve the problem
     */
    void run();

    /**
     * Returns list of assignments that satisfy considered problem
     * @return List of value assignments
     */
    List<IAssignment> getSolutions();
}
