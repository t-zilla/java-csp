# java-csp
An extendable application for solving Contstraint Satisfaction Problems such as Sudoku, Crosswords or N-Queens.

## Features
* Backtracking algorithm
* Forward checking algorithm
* Minimum Remaining Values heuristic
* Extendable - add your own problems and algorithms
* Statistics - number of visited nodes, number of violations, etc.
* Optional debug log

## Sample problems

### N-Queens
The N queens puzzle is the problem of placing N chess queens on an N×N chessboard so that no two queens threaten each other. (source: https://en.wikipedia.org/wiki/Eight_queens_puzzle)

### Latin Square
Latin square is an N×N array filled with N different symbols, each occurring exactly once in each row and exactly once in each column. (source: https://en.wikipedia.org/wiki/Latin_square)

## Comparison of implemented algorithms

### Backtracking vs. Forward checking
Latin Square problem, Logarithmic scale
![alt text](https://raw.githubusercontent.com/t-zilla/java-csp/master/graphs/bt-vs-fc.png)

### Forward checking vs. Forward checking with MRV heuristic
N-Queens problem, Logarithmic scale
![alt text](https://raw.githubusercontent.com/t-zilla/java-csp/master/graphs/fc-vs-fcmrv.png)

## Technologies and tools
* Java 8
* IntelliJ IDEA
* YourKit Java Profiler
