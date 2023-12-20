## DAA - Programming Assignment

## Program 1 : 
![image](https://github.com/kaushaln1/CS-DAA/assets/54100570/3f69708a-2123-4e51-a6c8-37ee1a43afd1)
![image](https://github.com/kaushaln1/CS-DAA/assets/54100570/93f99979-a034-4ca4-bcfe-b62e1e813c5e)
![image](https://github.com/kaushaln1/CS-DAA/assets/54100570/c9c697df-32ac-438b-92fe-3cf90c9de628)


## Program 2 : 
Implement Strassen’s matrix multiplication algorithm. Your program should take
an input variable n (=2k where k is a positive integer, 1 ≤ n ≤ 1,024) in the Linux
command line and generate two n*n random integer matrices, A and B. To avoid the
integer overflow, please generate the maximum random integer as floor(sqrt(maximum_Integer / n) ) for each input variable n. Compute A*B using Strassen’s
algorithm and compare the result to the result produced by the standard matrix
multiplication algorithm with O(n^3) time complexity. Print the results, if correct (If
incorrect results are produced, no credit will be given.

## Program 3 : 
Implement Large Integer Multiplication algorithm. Modify your algorithm so that it divides each n-digit integer into
three smaller integers of n/3 digits. Your programs should take an input variable n (=6k
where k is a positive integer) in the Linux command line and generate two n-digit random
integers (the most significant digit is between 1 and 9, not 0), A and B. Compute A*B
using original algorithm discussed in class and the algorithm you modified. Please make
sure that you get the same results for the two algorithms. Print the results, if correct. No
credit will be given if the algorithm is incorrectly implemented, the time complexity of
your program is higher than O(n^2), or your program only works for specific k values.

## Program 4 :
Implement the longest common subsequence (LCS) algorithm using the dynamic
programming method that was discussed in class. (No credit will be given if you implement
a brute force algorithm, which does exhaustive comparisons between two input strings.)

## Program 5 : 
Write a program floyd.cpp or floyd.java to find all pairs shortest paths using Floyd’s
algorithm for several undirected complete graphs, which are saved in a file called
output.txt. Print all pairs shortest paths and their lengths.
given input file (example) : 
```
Problem 1: n = 7
0 6 5 4 6 3 6
6 0 6 4 5 5 3
5 6 0 3 1 4 6
4 4 3 0 4 1 4
6 5 1 4 0 5 5
3 5 4 1 5 0 3
6 3 6 4 5 3 0
```

## Program 6 : (knapsack) 
1. create knapsack
   * Create n items where n is an integer randomly selected between 5 and 10. Display the selected n value.
   * Create a list of n items where each item has profit p(i)and weight w(i) where
     * pi is a positive integer randomly selected between 10 and 30; and
     * wi is a positive integer randomly selected between 5 and 20.
     * Set the capacity of the knapsack W = floor(0.6 * Σw(i)).
2. Brute forceknap sack
3. Refined Dynamic Programming (fill only required entries in DP table)
4. Improved Greedy 4 knapsack
5. backtracking (DFS)

