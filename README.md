
ParallelSort Utility Library:

The ParallelSort utility library provides parallel sorting algorithms in Java, offering efficient sorting of large datasets. This library includes implementations of parallel merge sort, quick sort, and radix sort algorithms, allowing users to choose the sorting algorithm that best fits their requirements. It supports custom comparators for defining sorting logic and provides flexibility for various sorting needs.

Features
Parallel Sorting: Utilizes parallelism to efficiently sort large arrays.
Multiple Algorithms: Offers implementations of parallel merge sort, quick sort, and radix sort.
Custom Comparators: Supports custom comparators for defining sorting order.
Ease of Use: Simple and intuitive interface for sorting arrays with different algorithms.
Usage
Sorting an Array
To sort an array using a specific sorting algorithm, use the parallelSort method:
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        // Example usage
        Integer[] arr = {5, 3, 8, 2, 7, 1, 9, 4, 6};

        // Parallel sort with merge sort algorithm
        ParallelSort.parallelSort(arr, Comparator.naturalOrder(), ParallelSort.SortAlgorithm.MERGE_SORT);

        // Print sorted array
        System.out.println(Arrays.toString(arr));
    }
}
Custom Comparator
You can provide a custom comparator to define the sorting order:
import java.util.Comparator;

public class CustomComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer a, Integer b) {
        // Custom comparison logic
        return a.compareTo(b);
    }
}

public class Main {
    public static void main(String[] args) {
        Integer[] arr = {5, 3, 8, 2, 7, 1, 9, 4, 6};

        // Parallel sort with custom comparator and merge sort algorithm
        ParallelSort.parallelSort(arr, new CustomComparator(), ParallelSort.SortAlgorithm.MERGE_SORT);

        // Print sorted array
        System.out.println(Arrays.toString(arr));
    }
}
Supported Algorithms
The library supports the following sorting algorithms:

Merge Sort (Parallel)
Quick Sort (Parallel)
Radix Sort (Parallel)
Contributing
Contributions to the ParallelSort utility library are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request on the GitHub repository.



