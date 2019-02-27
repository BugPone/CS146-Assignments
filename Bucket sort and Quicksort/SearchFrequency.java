package QuickSortTree;


/**
 * Class is used to help keep track of the searches and their frequencies by SearchTracker.
 *
 */
public class SearchFrequency {
	String search = "";
	int frequency = 0;

	public SearchFrequency(String search, int frequency) {
		this.search = search;
		this.frequency = frequency;
	}
	
	public String getSearch() {
		return search;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setScore(int i) {
		frequency = i;
	}
}
