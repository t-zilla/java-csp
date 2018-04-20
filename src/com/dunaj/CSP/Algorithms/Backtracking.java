package com.dunaj.CSP.Algorithms;

import com.dunaj.CSP.Interfaces.IAlgorithm;
import com.dunaj.CSP.Interfaces.IAssignment;
import com.dunaj.CSP.Interfaces.IProblem;
import com.dunaj.CSP.Interfaces.IVariable;
import com.dunaj.CSP.Main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Backtracking implements IAlgorithm {
    private IProblem problem;
    private List<IAssignment> solutions;

    /**
     * Initialises Backtracking algorithm in context of specified problem
     * @param problem Problem to solve
     */
    public Backtracking(IProblem problem) {
        this.problem = problem;
        this.solutions = new LinkedList<>();
    }

    /**
     * Run the algorithm
     */
    @Override
    public void run() {
        Main.log("Starting backtracking", 3);
        recursiveFind(new BTAssignment());
    }

    /**
     * Recursive method for solving the problem
     * @param assignment Value assignment representing current position in search tree
     */
    private void recursiveFind(BTAssignment assignment) {
        if (problem.isComplete(assignment) && problem.isConsistent(assignment)) {
            Main.log("Found solution " + assignment.toString(), 3);
            solutions.add(assignment);
            return;
        }

        IVariable variable = getNextVariable(assignment);
        if (variable == null) {
            Main.log("All variables assigned");
            return;
        }
        Main.log("Next variable will be: " + variable.toString(), 3);

        for (Object value : getValues(variable)) {
            Main.nodeCount++;
            BTAssignment newAssignment = assignment.assign(variable, value);
            if (!problem.isConsistent(newAssignment)) {
                Main.log("Assignment " + newAssignment.toString() + " violates constraints", 2);
                Main.violationCount++;
                continue;
            }
            Main.log("Assignment " + newAssignment.toString() + " satisfies constraints", 2);
            recursiveFind(newAssignment);
        }
        Main.log("Domain for variable " + variable.toString() + " is exhausted", 3);
    }

    /**
     * Determines which variable consider next.
     * Can be overridden by subclass.
     * @param assignment Assignment representing current position in search tree
     * @return Next variable to consider
     */
    private IVariable getNextVariable(BTAssignment assignment) {
        List<? extends IVariable> unassignedVariables = problem.getUnassignedVariables(assignment);
        if (unassignedVariables == null)
            return null;
        return unassignedVariables.get(0);
    }

    /**
     * Retrieves a list of values that will be assigned to given variable
     * Can be overridden by subclass.
     * @param variable Considered variable
     * @return Sorted list of values from the domain
     */
    private List<Object> getValues(IVariable variable) {
        return variable.getDomain();
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
     * Class representing a value assignment in Backtracking algorithm
     */
    class BTAssignment implements IAssignment {
        private Map<IVariable, Object> assignments;

        /**
         * Creates an assignment from a Map
         * @param assignments Map of variables and their values
         */
        BTAssignment(Map<IVariable, Object> assignments) {
            this.assignments = assignments;
        }

        /**
         * Creates an empty assignment
         */
        BTAssignment() {
            this.assignments = new HashMap<>();
        }

        /**
         * Return new instance of the assignment with added new variable-value pair
         * @param variable Variable
         * @param value Assigned value
         * @return New assignment instance containing new variable-value pair.
         */
        @Override
        public BTAssignment assign(IVariable variable, Object value) {
            Map<IVariable, Object> newAssignments = new HashMap<>(assignments);
            newAssignments.put(variable, value);
            return new BTAssignment(newAssignments);
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
