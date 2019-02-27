package QuickSortTree;

import java.util.LinkedList;

/**
 * https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html ^ This is
 * how I checked that ascii codes go up to 127
 * 
 * SearchBucket has 127 slots. Slots store items based on their FIRST LETTER
 * Each slot has a linked list of Searches. Searches hold the search term and an
 * array of results tied to the search
 *
 */
public class SearchBucket {
	// instead of n to 1, the ASCII values go from 0 - 127
	LinkedList<Search>[] arr = new LinkedList[127];

	public SearchBucket() {
		// fills the array with empty lists
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new LinkedList<Search>();
		}
	}

	public LinkedList<Search>[] getList() {
		return arr;
	}

	/**
	 * adds a search term to the list of terms First finds the index of the bucket
	 * it should be at by converting first letter of term into ascii code and going
	 * to the index of the ascii code.
	 */
	public void add(Search term) {
		// finds the list depending on the first letter of search term
		String search = term.getSearch();
		char first = search.charAt(0);
		int slot = (int) first;
		LinkedList<Search> list = arr[slot];
		// adds to found list
		list.add(term);

		// look at the method below
		this.sort(term);

	}

	/**
	 * after a word is added to a list, that list is sorted using insertion sort so
	 * the word can be put in its proper place.
	 */
	public void sort(Search term) {
		// get the index of the linkedlist we want based off of the search term's first
		// letter
		String name = term.getSearch();
		char first = name.charAt(0);
		int index = (int) first;

		LinkedList<Search> list = arr[index];

		for (int j = 1; j < list.size(); j++) {
			Search key = list.get(j);
			int i = j - 1;
			// we are swapping searches, but what we want Searches to be compared by string
			// ABC order
			while (i >= 0 && list.get(i).getSearch().compareTo(key.getSearch()) > 0) {
				list.set(i + 1, list.get(i));
				i = i - 1;
			}
			list.set(i + 1, key);
		}

	}

}
