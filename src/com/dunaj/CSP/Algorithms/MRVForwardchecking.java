package com.dunaj.CSP.Algorithms;

import com.dunaj.CSP.Interfaces.IProblem;
import com.dunaj.CSP.Interfaces.IVariable;

import java.util.Comparator;
import java.util.List;

/**
 * Forward checking with Minimum Remaining Values heurstic
 */
public class MRVForwardchecking extends Forwardchecking {

    /**
     * Initialises Forward checking algorithm with MRV heuristic
     * @param problem Problem to solve
     */
    public MRVForwardchecking(IProblem problem) {
        super(problem);
    }

    /**
     * Determines which variable consider next.
     * Varaibles with minimum remaining values in domain are prioritised
     * @param assignment Assignment representing current position in search tree
     * @return Next variable to consider
     */
    @Override
    protected IVariable getNextVariable(FCAssignment assignment) {
        List<? extends IVariable> unassignedVariables = problem.getUnassignedVariables(assignment);
        if (unassignedVariables == null)
            return null;
        unassignedVariables.sort(Comparator.comparing(var -> assignment.getRestrictedDomain(var).size()));
        return unassignedVariables.get(0);
    }
}
