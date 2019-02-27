package QuickSortTree;

import QuickSortTree.Url;

/**
 * search is the term you searched, and results is the string of 31 URLs that
 * the search is tied to. This is so if you enter the search again, the program
 * can jump to the results instead of invoking web crawler.
 *
 */
public class Search {

	Url[] results; //results tied to search term
	String search; //search term

	public Search(String search, Url[] results) {
		this.results = results;
		this.search = search.toLowerCase();
	}

	// gets the ascii code of the first character
	public int getFirstAscii() {
		char first = search.charAt(0);
		int ascii = (int) first;
		return ascii;
	}

	//compares based off of String search term
	public boolean equals(Object other) {
		Search o = (Search) other;
		boolean equals = this.search.equals(o.getSearch());
		return equals;
	}

	public void printResults() {
		for (Url x : results) {
			x.printAllInfo();
		}
	}

	public String getSearch() {
		return search;
	}

	public Url[] getResults() {
		return results;
	}
}
