package QuickSortTree;

import java.util.LinkedList;

/**
 * I was confused on the requirements on whether we should be bucket sorting the
 * searched terms or the Urls tied to the search terms, so I'm making both
 * options available.
 * 
 * The code for this was taken from my SearchBucket. Because either way, we are
 * using bucket sort on strings.
 * 
 * The only difference is that it uses the first character after the 4th dash to
 * sort into buckets instead of the first since all websites start with https://
 * all would end up in "h" bucket.
 *
 */
public class UrlBucket {
	LinkedList<Url>[] arr = new LinkedList[127];

	public UrlBucket() {
		// fills the array with empty lists
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new LinkedList<Url>();
		}
	}

	public LinkedList<Url>[] getList() {
		return arr;
	}

	/**
	 * add a Url to the bucket of urls.
	 */
	public void add(Url term) {
		// finds the list depending on the first letter after the fourth dash.
		char first = term.getNameAfterDashes().charAt(0);
		int slot = (int) first;
		LinkedList<Url> list = arr[slot];
		// adds to found list
		list.add(term);
	}

	/**
	 * after all the words are added to a list, that list is sorted using insertion sort so
	 * the words can be put in their proper place
	 */
	public void sort() {
		// get the index of the linkedlist we want based off of the search term's first
		// letter

		for (LinkedList<Url> list : arr) {
			for (int j = 1; j < list.size(); j++) {
				Url key = list.get(j);
				int i = j - 1;
				// we are swapping searches, but what we want urls to be compared by string
				// ABC order
				while (i >= 0 && list.get(i).getNameAfterDashes().compareTo(key.getNameAfterDashes()) > 0) {
					list.set(i + 1, list.get(i));
					i = i - 1;
				}
				list.set(i + 1, key);
			}
		}

	}

	/**
	 * All the linked lists become one big linked list and that linked list is
	 * returned
	 */
	public LinkedList<Url> appendAll() {
		LinkedList<Url> urls = new LinkedList<Url>();
		for (int i = 0; i < arr.length; i++) {
			urls.addAll(arr[i]);
		}
		return urls;

	}
}
