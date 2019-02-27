package QuickSortTree;

//regular quick sort but for searchFrequency objects
public class SearchFrequencyQuickSort {
	// p = LEFT
	// q = MID
	// r = RIGHT
	public void quickSort(SearchFrequency[] arr, int left, int right) {
		if (left < right) {
			int mid = partition(arr, left, right);
			quickSort(arr, left, mid - 1);
			quickSort(arr, mid + 1, right);
		}
	}

	// puts the rightmost element in the subarray to its rightful place within that
	// subarray.
	public int partition(SearchFrequency[] arr, int left, int right) {
		int x = arr[right].getFrequency();
		int i = left - 1;
		for (int j = left; j < right; j++) {
			if (arr[j].getFrequency() <= x) {
				i = i + 1;
				swap(arr, i, j);
			}
		}
		swap(arr, i + 1, right);
		return i + 1;
	}

	public void swap(SearchFrequency[] arr, int i, int j) {
		SearchFrequency temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
