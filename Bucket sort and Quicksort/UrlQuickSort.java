package QuickSortTree;

//regular quick sort but for website scores
public class UrlQuickSort {
	// p = LEFT
	// q = MID
	// r = RIGHT
	public void quickSort(Url[] arr, int left, int right) {
		if (left < right) {
			int mid = partition(arr, left, right);
			quickSort(arr, left, mid - 1);
			quickSort(arr, mid + 1, right);
		}
	}

	//puts the rightmost element in the subarray to its rightful place within that subarray.
	public int partition(Url[] arr, int left, int right) {
		int x = arr[right].getScore();
		int i = left - 1;
		for (int j = left; j < right; j++) {
			if (arr[j].getScore() <= x) {
				i = i + 1;
				swap(arr, i, j);
			}
		}
		swap(arr, i + 1, right);
		return i + 1;
	}

	public void swap(Url[] arr, int i, int j) {
		Url temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
