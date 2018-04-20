package com.dunaj.CSP;

import com.dunaj.CSP.Algorithms.Backtracking;
import com.dunaj.CSP.Algorithms.Forwardchecking;
import com.dunaj.CSP.Algorithms.MRVForwardchecking;
import com.dunaj.CSP.Interfaces.IAlgorithm;
import com.dunaj.CSP.Interfaces.IAssignment;
import com.dunaj.CSP.Interfaces.IProblem;
import com.dunaj.CSP.Problems.LatinSquare;
import com.dunaj.CSP.Problems.NQueens;

public class Main {
    private static IProblem problem = null;
    private static IAlgorithm algorithm = null;
    private static int loggingLevel = 1;
    public static int violationCount = 0;
    public static int nodeCount = 0;

    /**
     * Main method of the application
     * @param args
     * 0: problem name
     * 1: problem size
     * 2: algorithm name
     * 3: (optional) debug level
     */
    public static void main(String[] args) {
        if (args.length >= 4)
            loggingLevel = Integer.parseInt(args[3]);
        if (args.length < 3) {
            args = new String[]{"nqueens", "8", "forwardchecking"};
            log("Using default parameters", 3);
        }
        init(args[0], args[1], args[2]);

        if (problem != null && algorithm != null) {
            log("Solving " + args[0].toUpperCase() + "(" +
                    args[1] + ") with " + args[2].toUpperCase());
            long startTime = System.currentTimeMillis();
            algorithm.run();
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            int solutionCount = 0;
            if (algorithm.getSolutions() != null)
                solutionCount = algorithm.getSolutions().size();
            int i=0;
            log("Found " + solutionCount + " solutions in " + elapsedTime + " ms");
            if (solutionCount > 0) {
                log("Examples: ");
                for (IAssignment solution : algorithm.getSolutions()) {
                    if (solutionCount < 5 || i % (solutionCount / 5) == 0) {
                        log(solution.toString());
                    }
                    i++;
                }
            }
            log("Returns: " + violationCount);
            log("Visited nodes: " + nodeCount);
        }
    }

    /**
     * Initialise the app using supplied parameters
     * @param problemName Name of the problem to solve
     * @param problemSize Size of the problem (if applicable)
     * @param algorithmName Name of the algorithm used to solve the problem
     */
    private static void init(String problemName, String problemSize, String algorithmName) {
        int size = Integer.parseInt(problemSize);
        switch(problemName.toLowerCase()) {
            case "nqueens":
                problem = new NQueens(size);
                break;
            case "latinsquare":
                problem = new LatinSquare(size);
                break;
        }
        if (problem == null)
            log("Invalid problem!", 0);

        switch(algorithmName.toLowerCase()) {
            case "backtracking":
                algorithm = new Backtracking(problem);
                break;
            case "forwardchecking":
                algorithm = new Forwardchecking(problem);
                break;
            case "mrvforwardchecking":
                algorithm = new MRVForwardchecking(problem);
                break;
        }
        if (algorithm == null)
            log("Invalid algorithm!", 0);
    }

    /**
     * Log to standard output
     * @param str Log string
     */
    public static void log(String str) {
        log(str, 1);
    }

    /**
     * Log to standard output providing debug level is high enough
     * @param str Log string
     * @param level Level of the log message. Lower -> More important
     */
    public static void log(String str, int level) {
        if (level <= loggingLevel)
            System.out.println(str);
    }
}
