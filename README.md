# DAA Assignment 1 - Algorithm Analysis Project

This project implements and analyzes several fundamental algorithms with performance metrics collection. The goal is to compare different sorting and selection algorithms and understand their behavior in practice.

## Implemented Algorithms

The project includes four main algorithms with various optimizations:

### algorithms.QuickSort
- Median-of-3 pivot selection for better partitioning
- Smaller-first recursion to minimize stack depth
- Insertion sort cutoff for small arrays (≤10 elements)
- Input randomization to avoid worst-case performance

### algorithms.MergeSort  
- Buffer reuse to reduce memory allocations
- Insertion sort cutoff for small arrays
- Guaranteed O(n log n) time complexity
- Stable sorting algorithm

### algorithms.DeterministicSelect
- Median-of-medians algorithm implementation
- Groups elements by 5, finds medians recursively
- Guaranteed O(n) time complexity
- Avoids worst-case O(n²) performance of quickselect

### algorithms.ClosestPair
- Divide-and-conquer algorithm for 2D closest pair problem
- Strip optimization for efficient cross-boundary checking
- O(n log n) time complexity
- Handles duplicate points and edge cases

## Performance algorithms.Metrics System

The metrics system tracks:
- Comparison counts for each algorithm
- Number of swaps, partitions, and merges
- Maximum recursion depth
- Recursive call counts
- Strip checks (for algorithms.ClosestPair)

All metrics are exported to CSV files for detailed performance analysis.

## Usage

### Basic Demo
```bash
mvn exec:java
```
Runs all algorithms on sample data and displays performance metrics.

### Command Line Interface
Run specific algorithms with different input sizes:

```bash
# Just algorithms.QuickSort on 1000 elements
mvn exec:java -Dexec.args="quicksort 1000"

# All algorithms on 500 elements  
mvn exec:java -Dexec.args="all 500"

# Save results to a custom file
mvn exec:java -Dexec.args="mergesort 100 myresults.csv"
```

Available algorithms:
- `quicksort` or `qs`
- `mergesort` or `ms` 
- `select` or `ds` (deterministic select)
- `closest` or `cp` (closest pair)
- `all` (runs everything)

### Running Tests
```bash
mvn test
```
Runs all unit tests and collects performance metrics. Results are saved to `metrics/test-results.csv`.

## Performance Analysis

### Algorithm Characteristics
- **algorithms.QuickSort**: Generally fastest for random data, but performance varies with pivot selection
- **algorithms.MergeSort**: More predictable performance, stable sorting, guaranteed O(n log n)
- **algorithms.DeterministicSelect**: Guaranteed O(n) performance, useful for median finding
- **algorithms.ClosestPair**: Efficient O(n log n) solution for 2D closest pair problem

### Optimization Impact
- Median-of-3 pivot selection significantly improves algorithms.QuickSort performance
- Buffer reuse in algorithms.MergeSort reduces memory allocations
- Insertion sort cutoff provides measurable speedup for small arrays
- Smaller-first recursion helps maintain reasonable stack depth

## Project Structure

```
src/main/java/
├── Main.java                           # CLI interface
├── algorithms/
│   ├── QuickSort.java                  # Optimized QuickSort
│   ├── MergeSort.java                  # Optimized MergeSort  
│   ├── DeterministicSelect.java        # MoM5 algorithm
│   ├── ClosestPair.java                # Divide-and-conquer
│   └── Metrics.java                    # Performance tracking
└── utils/
    ├── CsvMetricsWriter.java           # CSV export
    └── Util.java                       # Common utilities

src/test/java/
├── QuickSortTest.java                  # Tests + metrics
├── MergeSortTest.java                  # Tests + metrics
├── DeterministicSelectTest.java        # Tests + metrics
└── ClosestPairTest.java                # Tests + metrics
```

## Requirements
- Java 17+
- Maven 3.6+
- JUnit 5 for testing

## Build Commands
```bash
# Compile project
mvn compile

# Run tests
mvn test

# Run demo
mvn exec:java

# Clean build artifacts
mvn clean
```

## Sample Output

Example output from running the demo:
```
algorithms.QuickSort: [64, 34, 25, 12, 22, 11, 90, 5]
Sorted: [5, 11, 12, 22, 25, 34, 64, 90]
algorithms.Metrics: comparisons=22, swaps=14, maxDepth=6

algorithms.MergeSort: [38, 27, 43, 3, 9, 82, 10]
Sorted: [3, 9, 10, 27, 38, 43, 82]
algorithms.Metrics: comparisons=14, merges=6, maxDepth=4
```

Detailed metrics are exported to CSV files for further analysis.

## Future Enhancements

Potential improvements include:
- JMH microbenchmarking for precise performance measurements
- Performance visualization plots
- Detailed worst-case/best-case analysis
- Additional algorithm implementations

This project provides a solid foundation for algorithm analysis with comprehensive testing and performance metrics collection. 
