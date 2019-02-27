package test4;

/**
 * Manages an array of Urls.
 */
public class UrlHeap {

	Url[] arr; // ARRAY THAT HEAP MANAGESs
	int size = 0;
	int originSize = 0; // may not be needed in new implementation
	UrlHeap heap; // ?

	/**
	 * Makes an un-heapified heap. Call Build_Max_Heap() to turn it into a heapified
	 * heap.
	 * 
	 * @param array
	 *            array you want to apply the heap functions to.
	 */
	public UrlHeap(Url[] array) {
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
		if (l < size && arr[l].getScore() > arr[i].getScore()) {
			largest = l;
		} else {
			largest = i;
		}

		if (r < size && arr[r].getScore() > arr[largest].getScore()) {
			largest = r;
		}
		if (largest != i) {
			swap(i, largest);
			Max_Heapify(largest);
		}

	}

	/**
	 * Turns all the elements in the array into a structured heap.
	 */
	public void Build_Max_Heap() {
		// a part of me think this is supposed to be size/2 + 1?
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

	public void HeapSort() {
		Build_Max_Heap();
		for (int i = size - 1; i > 0; i--) {
			swap(0, i);
			size--;
			Max_Heapify(0);
		}
		size = originSize;
	}

	public int Heap_Maximum() {
		return arr[0].getScore();
	}

	/**
	 * use this instead of Heap_Maximum in !highscore for more verbosenesse
	 */
	public void Print_Heap_Maximum() {
		System.out.println("Top website is: " + arr[0].getName());
		System.out.println("Score: " + arr[0].getScore());
	}

	/**
	 * increases the node at i to the value of key and rearranges array the heap to
	 * maintain integrity
	 * 
	 * @param i
	 *            node's index.
	 * @param key
	 *            new value for node.
	 */
	public void Heap_Increase_Key(int i, int key) {
		if (key < arr[i].getScore()) {
			System.out.println("new key is smaller than current key @ Heap increase key");
		}
		// arr[i] = key;
		arr[i].setScore(key);

		while (i > 0 && arr[parent(i)].getScore() < arr[i].getScore()) {
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
		int max = arr[0].getScore();
		arr[0] = arr[size - 1];
		size--;
		// make new array for new size
		Url[] newArr = new Url[size];
		for (int i = 0; i < size; i++) {
			newArr[i] = arr[i];
		}
		this.arr = newArr;

		Max_Heapify(0);
		return max;
	}

	// hastily added for priority queue implementation. Added seperate method so I
	// don't break anything. This is just for extracting URLs intead of integers.
	public Url Heap_Extract_Max_Url() {
		if (size < 1) {
			System.out.println("heap underflow @ Heap_Extract_Max");
		}
		Url max = arr[0];
		arr[0] = arr[size - 1];
		size--;
		// make new array for new size
		Url[] newArr = new Url[size];
		for (int i = 0; i < size; i++) {
			newArr[i] = arr[i];
		}
		this.arr = newArr;

		Max_Heapify(0);
		return max;
	}

	/**
	 * inserts a value into the heap (Array) and rebuilds heap
	 * 
	 * @param name
	 *            url being inserted
	 * @param score
	 *            custom score of the Url
	 */

	public void Max_Heap_Insert(String name, int key) {
		size++;
		// this means make a new array with +1 size... :')

		// newArr[size-1] = Integer.MIN_VALUE;
		Url[] newArr = new Url[size];
		Url dummy = new Url(name, Integer.MIN_VALUE); // ensures it goes last?
		newArr[size - 1] = dummy;

		for (int i = 0; i < size - 1; i++) {
			newArr[i] = arr[i];
		}
		this.arr = newArr;

		Heap_Increase_Key(size - 1, key); // sets whatever is at size - 1 to have that new score.

	}

	/**
	 * basic swap method
	 */
	public void swap(int i, int j) {
		Url temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	/**
	 * Prints the values of all elements with their index
	 */
	public void printValuesWithId() {
		for (int x = 0; x < arr.length; x++) {
			System.out.println("ID: " + x);
			System.out.println(arr[x].getName());
			System.out.println("score: " + arr[x].getScore());
			System.out.println();
		}
	}

	/**
	 * prints without index
	 */
	public void printValues() {
		for (Url x : arr) {
			System.out.println(x.getName());
			System.out.println("Recommendation score: " + x.getScore());
			System.out.println();
		}
	}

	public void printScores() {
		for (Url x : arr) {
			System.out.println(x.getScore());
		}
	}

	public Url[] getArray() {
		return arr;
	}

	public int getScore(int ID) {
		return arr[ID].getScore();
	}

	public String getOneName(int ID) {
		return arr[ID].getName();
	}

	/**
	 * From heap's array, prints the name from INDEX: ID and score from INDEX: ID
	 * 
	 * @param ID
	 *            index from heap's array you'd like to pull from
	 */
	public void printValue(int ID) {
		System.out.println(arr[ID].getName());
		System.out.println("Score: " + arr[ID].getScore());
	}

	/**
	 * Defies Law of Demeter intentionally. Used by editvalues() Lets person get a
	 * REFERENCE to the Url at this index to edit that Url.
	 * 
	 * @param index
	 *            the index of item you need reference to
	 */
	public Url getUrl(int index) {
		return arr[index];
	}
}
