package com.dunaj.CSP.Algorithms;

import com.dunaj.CSP.Interfaces.IAlgorithm;
import com.dunaj.CSP.Interfaces.IAssignment;
import com.dunaj.CSP.Interfaces.IProblem;
import com.dunaj.CSP.Interfaces.IVariable;
import com.dunaj.CSP.Main;

import java.util.*;

public class Forwardchecking implements IAlgorithm {
    protected IProblem problem;
    protected List<IAssignment> solutions;

    /**
     * Initialises Backtracking algorithm in context of specified problem
     * @param problem Problem to solve
     */
    public Forwardchecking(IProblem problem) {
        this.problem = problem;
        this.solutions = new LinkedList<>();
    }

    /**
     * Run the algorithm
     */
    @Override
    public void run() {
        //Main.log("Starting forwardchecking", 3);
        recursiveFind(new FCAssignment());
    }

    /**
     * Recursive method for solving the problem
     * @param assignment Value assignment representing current position in search tree
     */
    protected void recursiveFind(FCAssignment assignment) {
        if (problem.isComplete(assignment) && problem.isConsistent(assignment)) {
            //Main.log("Found solution " + assignment.toString(), 3);
            solutions.add(assignment);
            return;
        }

        IVariable variable = getNextVariable(assignment);
        if (variable == null) {
            //Main.log("All variables assigned");
            return;
        }
        //Main.log("Next variable will be: " + variable.toString(), 3);

        if (assignment.getRestrictedDomain(variable).size() == 0)
            Main.violationCount++;

        for (Object value : assignment.getRestrictedDomain(variable)) {
            Main.nodeCount++;
            FCAssignment newAssignment = assignment.assign(variable, value);
            if (!problem.isConsistent(newAssignment)) {
                Main.log("Assignment " + newAssignment.toString() + " violates constraints", 2);
                continue;
            }
            //Main.log("Assignment " + newAssignment.toString() + " satisfies constraints", 2);
            newAssignment.applyDomainRestrictions(problem.getDomainRestrictions(variable, value));
            recursiveFind(newAssignment);
        }
        //Main.log("Domain for variable " + variable.toString() + " is exhausted", 3);
    }

    /**
     * Determines which variable consider next.
     * Can be overridden by subclass.
     * @param assignment Assignment representing current position in search tree
     * @return Next variable to consider
     */
    protected IVariable getNextVariable(FCAssignment assignment) {
        List<? extends IVariable> unassignedVariables = problem.getUnassignedVariables(assignment);
        if (unassignedVariables == null)
            return null;
        return unassignedVariables.get(0);
    }

    /**
     * Retrieves all solutions found so far
     * @return List of value assignments
     */
    @Override
    public List<IAssignment> getSolutions() {
        return solutions;
    }

    /**
     * Class representing a value assignment in Forward checking algorithm
     */
    class FCAssignment implements IAssignment {
        private Map<IVariable, Object> assignments;
        private Map<IVariable, List<Object>> restrictedDomains;

        /**
         * Creates an assignment from a Map and restricted domains of variables
         * @param assignments Map of variables and their values
         * @param restrictedDomains Restricted domains of variables
         */
        FCAssignment(Map<IVariable, Object> assignments,
                     Map<IVariable, List<Object>> restrictedDomains) {
            this.assignments = assignments;
            this.restrictedDomains = restrictedDomains;
        }

        /**
         * Creates an empty assignment
         */
        FCAssignment() {
            this.assignments = new HashMap<>();
            this.restrictedDomains = new HashMap<>();
            for (IVariable variable : problem.getVariables()) {
                restrictedDomains.put(variable, variable.getDomain());
            }
        }

        /**
         * Return new instance of the assignment with added new variable-value pair
         * @param variable Variable
         * @param value Assigned value
         * @return New assignment instance containing new variable-value pair.
         */
        @Override
        public FCAssignment assign(IVariable variable, Object value) {
            Map<IVariable, Object> newAssignments = new HashMap<>(assignments);
            newAssignments.put(variable, value);
            Map<IVariable, List<Object>> newDomains = new HashMap<>();
            for (IVariable var : restrictedDomains.keySet()) {
                newDomains.put(var, new ArrayList<>(restrictedDomains.get(var)));
            }
            return new FCAssignment(newAssignments, newDomains);
        }

        /**
         * Returns a list of restricted domains of the specified variable
         * @param variable Considered variable
         * @return List of restrcicted domains
         */
        public List<Object> getRestrictedDomain(IVariable variable) {
            return restrictedDomains.get(variable);
        }

        /**
         * Removes values from domains of variables
         * @param domainRestrictions Map containing list of values that have to be removed
         */
        public void applyDomainRestrictions(Map<IVariable, List<Object>> domainRestrictions) {
            if (domainRestrictions != null) {
                for (IVariable variable : restrictedDomains.keySet()) {
                    restrictedDomains.get(variable).removeAll(domainRestrictions.get(variable));
                }
            }
        }

        /**
         * Returns map representing the assignment
         * @return Assignment map
         */
        @Override
        public Map<IVariable, Object> getAssignments() {
            return assignments;
        }

        /**
         * Returns a string containing all variable-value pairs
         * @return String representing assignment
         */
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (IVariable variable : problem.getVariables()) {
                stringBuilder.append(" ");
                stringBuilder.append(getValue(variable));
                stringBuilder.append(" ");
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

    }
}
