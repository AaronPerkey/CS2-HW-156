package unl.cse.sorting;

import java.util.List;

public class SortingAlgorithms {

	/**
	 * A basic implementation of
	 * <a href="https://en.wikipedia.org/wiki/Selection_sort">Selection Sort</a>.
	 * 
	 * @param locations
	 */
	public static void selectionSort(List<Location> locations) {
		for (int i = 0; i < locations.size(); i++) {
			int minIndex = i;
			for (int j = i + 1; j < locations.size(); j++) {
				if (locations.get(j).compareTo(locations.get(minIndex)) < 0) {
					minIndex = j;
				}
			}
			// swap
			Location temp = locations.get(i);
			locations.set(i, locations.get(minIndex));
			locations.set(minIndex, temp);
		}
	}

	/**
	 * A basic implementation of
	 * <a href="https://en.wikipedia.org/wiki/Insertion_sort">Insertion Sort</a>.
	 * 
	 * @param locations
	 */
	public static void insertionSort(List<Location> locations) {
		for (int i = 1; i < locations.size(); i++) {
			Location x = locations.get(i);
			int j = i - 1;
			while (j >= 0 && (locations.get(j).compareTo(x)) >= 1) {
				locations.set(j + 1, locations.get(j));
				j--;
			}
			locations.set(j + 1, x);
		}
	}

	/**
	 * A recursive implementation of
	 * <a href="https://en.wikipedia.org/wiki/Quicksort">Quick Sort</a>.
	 * 
	 * @param locations
	 */
	public static void quickSort(List<Location> locations) {
		quickSortRecursive(locations, 0, locations.size() - 1);
		
	}

	/**
	 * Recursive call for the {@link #quickSort(List)} method.
	 * @param locations
	 * @param low
	 * @param high
	 */
	private static void quickSortRecursive(List<Location> locations, int low, int high) {
		if (low < high) {
			int p = partition(locations, low, high);
			quickSortRecursive(locations, low, p - 1);
			quickSortRecursive(locations, p + 1, high);
		}
	}
	
	private static int partition(List<Location> locations, int low, int high) {
	    int pivot = low;
	    int i = low + 1;
	    int j = high;

	    while (i <= j) {
	        while (i <= j && locations.get(i).compareTo(locations.get(pivot)) < 0) {
	            i++;
	        }
	        while (i <= j && locations.get(j).compareTo(locations.get(pivot)) > 0) {
	            j--;
	        }
	        if (i <= j) {
	            swap(locations, i, j);
	            i++;
	            j--;
	        }
	    }

	    // Swap the pivot element with the element at index j
	    swap(locations, pivot, j);

	    return j;
	}

	
	public static void swap(List<Location> arr, int i, int j) {
	    Location temp = arr.get(i);
	    arr.set(i, arr.get(j));
	    arr.set(j, temp);
	}
	
}
