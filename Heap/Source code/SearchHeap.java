package test4;

/**
 * this is just a heap but for searches instead of Urls. This is used to keep
 * track of the top searches. copied code from UrlHeap since UrlHeap came first.
 */
public class SearchHeap {

	SearchFrequency[] arr;
	int size = 0;
	int originSize = 0;
	SearchFrequency heap;

	/**
	 * same function as UrlHeap, but with SearchFrequencies
	 * 
	 * @param array
	 *            the array you want to manage
	 */
	public SearchHeap(SearchFrequency[] array) {
		this.size = array.length;
		this.originSize = size;
		arr = array;
	}
	
	/**
	 * if node i has a node out of place, but its left and right subtrees are max
	 * heaps, call this on i to maintain heap integrity.
	 * 
	 * @param i
	 *            the node in the heap that is "out of place"
	 */
	public void Max_Heapify(int i) {
		int largest;
		int l = left(i);
		int r = right(i);
		if (l < size && arr[l].getFrequency() > arr[i].getFrequency()) {
			largest = l;
		} else {
			largest = i;
		}

		if (r < size && arr[r].getFrequency() > arr[largest].getFrequency()) {
			largest = r;
		}
		if (largest != i) {
			swap(i, largest);
			Max_Heapify(largest);
		}

	}
	
	/**
	 * In any arrangement, takes the array that is being managed and turns it into 
	 * an array that has heap integrity
	 */
	public void Build_Max_Heap() {
		for (int i = size / 2; i >= 0; i--) {
			Max_Heapify(i);
		}
	}

	public int left(int i) {
		return 2 * i + 1;
	}

	public int right(int i) {
		return 2 * i + 2;
	}

	public int parent(int i) {
		return i / 2;
	}

	/**
	 * if array is a max heap, will sort the array
	 */
	public void HeapSort() {
		Build_Max_Heap();
		for (int i = size - 1; i > 0; i--) {
			swap(0, i);
			size--;
			Max_Heapify(0);
		}
		size = originSize;
	}

	//returns value at root
	public int Heap_Maximum() {
		return arr[0].getFrequency();
	}

	// increases the thing at int i to the value of key and rearranges array
	// I think it needs to be a heap?
	public void Heap_Increase_Key(int i, int key) {
		if (key < arr[i].getFrequency()) {
			System.out.println("new key is smaller than current key @ Heap increase key");
		}
		// arr[i] = key;
		arr[i].setScore(key);

		while (i > 0 && arr[parent(i)].getFrequency() < arr[i].getFrequency()) {
			swap(i, parent(i));
			i = parent(i);
		}
	}

	/**
	 * takes out the highest valued node and restructures heap to maintain
	 * integrity.
	 * 
	 * @return the value that you've extracted.
	 */
	public int Heap_Extract_Max() {
		if (size < 1) {
			System.out.println("heap underflow @ Heap_Extract_Max");
		}
		int max = arr[0].getFrequency();
		arr[0] = arr[size - 1];
		size--;
		// make new array for new size
		SearchFrequency[] newArr = new SearchFrequency[size];
		for (int i = 0; i < size; i++) {
			newArr[i] = arr[i];
		}
		this.arr = newArr;

		Max_Heapify(0);
		return max;
	}

	 /** inserts a value into the heap (Array) and rebuilds heap
	 * 
	 * @param name
	 *            name of the searchFrequency being inserted
	 * @param score
	 *            key of the searchFrequency, AKA the # of searches tied to that search
	 */
	public void Max_Heap_Insert(String name, int key) {
		size++;
		// this means make a new array with +1 size... :')

		// newArr[size-1] = Integer.MIN_VALUE;
		SearchFrequency[] newArr = new SearchFrequency[size];
		SearchFrequency dummy = new SearchFrequency(name, Integer.MIN_VALUE);
		newArr[size - 1] = dummy;

		for (int i = 0; i < size - 1; i++) {
			newArr[i] = arr[i];
		}
		this.arr = newArr;

		Heap_Increase_Key(size - 1, key);

	}

	public void swap(int i, int j) {
		SearchFrequency temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	/**
	 * print all values with their ID for selection 
	 */
	public void printValuesWithId() {
		for (int x = 0; x < arr.length; x++) {
			System.out.println("ID: " + x);
			System.out.println(arr[x].getSearch());
			System.out.println("score: " + arr[x].getFrequency());
			System.out.println();
		}
	}
	
	/**
	 * prints the important information of all objects handled by the array
	 */
	public void printValues() {
		for (SearchFrequency x : arr) {
			System.out.println(x.getSearch());
			System.out.println("Recommendation score: " + x.getFrequency());
			System.out.println();
		}
	}

	public void printScores() {
		for (SearchFrequency x : arr) {
			System.out.println(x.getFrequency());
		}
	}

	public SearchFrequency[] getArray() {
		return arr;
	}

	public int getScore(int ID) {
		return arr[ID].getFrequency();
	}

	public String getOneName(int ID) {
		return arr[ID].getSearch();
	}

	public void printTop() {

	}
	//prints the important values of one searchfreqency
	public void printValue(int ID) {
		System.out.println(arr[ID].getSearch());
		System.out.println("Score: " + arr[ID].getFrequency());
	}

}
