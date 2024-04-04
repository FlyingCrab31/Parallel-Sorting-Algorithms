import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RecursiveAction;
class ParallelSort {

    private static final int THRESHOLD = 1000;

    public enum SortAlgorithm {
        MERGE_SORT, QUICK_SORT, RADIX_SORT
    }

    public static <T> void parallelSort(T[] arr, Comparator<T> comparator, SortAlgorithm algorithm) {
        parallelSort(arr, 0, arr.length - 1, comparator, algorithm);
    }

    private static <T> void parallelSort(T[] arr, int low, int high, Comparator<T> comparator, SortAlgorithm algorithm) {
        if (low < high) {
            if (high - low < THRESHOLD) {
                sequentialSort(arr, low, high, comparator, algorithm);
            } else {
                int mid = low + (high - low) / 2;

                ParallelSortTask<T> leftTask = new ParallelSortTask<>(arr, low, mid, comparator, algorithm);
                ParallelSortTask<T> rightTask = new ParallelSortTask<>(arr, mid + 1, high, comparator, algorithm);

                leftTask.fork();
                rightTask.fork();

                leftTask.join();
                rightTask.join();

                merge(arr, low, mid, high, comparator);
            }
        }
    }

    private static class ParallelSortTask<T> extends RecursiveAction {
        private final T[] arr;
        private final int low;
        private final int high;
        private final Comparator<T> comparator;
        private final SortAlgorithm algorithm;

        public ParallelSortTask(T[] arr, int low, int high, Comparator<T> comparator, SortAlgorithm algorithm) {
            this.arr = arr;
            this.low = low;
            this.high = high;
            this.comparator = comparator;
            this.algorithm = algorithm;
        }

        @Override
        protected void compute() {
            if (low < high) {
                if (high - low < THRESHOLD) {
                    sequentialSort(arr, low, high, comparator, algorithm);
                } else {
                    int mid = low + (high - low) / 2;

                    ParallelSortTask<T> leftTask = new ParallelSortTask<>(arr, low, mid, comparator, algorithm);
                    ParallelSortTask<T> rightTask = new ParallelSortTask<>(arr, mid + 1, high, comparator, algorithm);

                    leftTask.fork();
                    rightTask.fork();

                    leftTask.join();
                    rightTask.join();

                    merge(arr, low, mid, high, comparator);
                }
            }
        }
    }

    private static <T> void sequentialSort(T[] arr, int low, int high, Comparator<T> comparator, SortAlgorithm algorithm) {
        switch (algorithm) {
            case MERGE_SORT:
                sequentialMergeSort(arr, low, high, comparator);
                break;
            case QUICK_SORT:
                sequentialQuickSort(arr, low, high, comparator);
                break;
            case RADIX_SORT:
                sequentialRadixSort(arr, low, high);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort algorithm");
        }
    }

    private static <T> void sequentialMergeSort(T[] arr, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            sequentialMergeSort(arr, low, mid, comparator);
            sequentialMergeSort(arr, mid + 1, high, comparator);
            merge(arr, low, mid, high, comparator);
        }
    }

    private static <T> void sequentialQuickSort(T[] arr, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(arr, low, high, comparator);
            sequentialQuickSort(arr, low, pi - 1, comparator);
            sequentialQuickSort(arr, pi + 1, high, comparator);
        }
    }

    private static <T> int partition(T[] arr, int low, int high, Comparator<T> comparator) {
        T pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], pivot) < 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static <T> void sequentialRadixSort(T[] arr, int low, int high) {
        // Implement sequential radix sort
    }

    private static <T> void merge(T[] arr, int low, int mid, int high, Comparator<T> comparator) {
        int n1 = mid - low + 1;
        int n2 = high - mid;

        T[] leftArray = Arrays.copyOfRange(arr, low, mid + 1);
        T[] rightArray = Arrays.copyOfRange(arr, mid + 1, high + 1);

        int i = 0, j = 0, k = low;

        while (i < n1 && j < n2) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

