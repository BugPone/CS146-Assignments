package QuickSortTree;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Takes a arrayList of Strings that are the searches and ranks the searches based
 * off of the most unique Then, takes the top 10 out of that and returns it in a
 * new array.
 *
 *basically takes ArrayList<String> searches and spits SearchFrequency[] to be used by quicksort for ordering.
 */
public class SortTracker {
	ArrayList<String> searches;

	public SortTracker(ArrayList<String> passed) {
		this.searches = new ArrayList<String>(passed);
	}
	
	/**
	 * Takes the arrayList of searches in the field
	 * and converts them into SearchFrequency objects,
	 * which hold the search term and the number
	 * of times that search was made.
	 * 
	 * @return returns an array of SearchFrequency. 
	 * Used by quicksort to be organized
	 */
	public SearchFrequency[] getSearchFrequencies() {
		//ties every search to a frequency. Hashmaps are sets.
		Map<String, Integer> map = new HashMap<>();
		for(String x : searches) {
			map.put(x,Collections.frequency(searches, x));	
		}
		
		//SearchFrequency objects have the search and their frequency
		ArrayList<SearchFrequency> searches = new ArrayList<>();
		for(String x : map.keySet()) {
			SearchFrequency search = new SearchFrequency(x, map.get(x));
			searches.add(search);
		}
		
		//im doing it this way because I don't know how to add from keySet to array
		//adds all items from ArrayList to Array
		SearchFrequency[] searchArr = new SearchFrequency[searches.size()];
		for(int x = 0; x < searches.size(); x++) {
			searchArr[x] = searches.get(x);
		}
		
		SearchFrequencyQuickSort sorter = new SearchFrequencyQuickSort();
		sorter.quickSort(searchArr, 0, searchArr.length-1);
		return searchArr;
		
	}

	public void printValues() {
		for (String x : searches) {
			System.out.println(x);
		}
	}

	public void add(String x) {
		searches.add(x);
	}
}
