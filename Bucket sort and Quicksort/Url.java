package QuickSortTree;

/**
 * An instance of a URL pulled from the Crawler Turns it into a class with
 * associated values randomly generated Implements comparable for Queue
 * implementation
 * 
 * @author Leo
 */
public class Url {
	int rankBeforeSort = -1;
	int score = 0;
	int years = 0;
	int links = 0;
	int keywords = 0;
	int paid = 0;

	public String name = "";

	/**
	 * Makes Url object with randomized values. supply the URL.
	 */
	public Url(String name) {
		years = (int) (Math.random() * 100);
		links = (int) (Math.random() * 100);
		keywords = (int) (Math.random() * 200);
		paid = (int) (Math.random() * 100);

		score = years + links + keywords + paid;
		this.name = name;
	}

	/**
	 * Used for adding a site with customized values.
	 */
	public Url(String name, int paid, int years, int keywords, int links) {
		this.paid = paid;
		this.name = name;
		this.years = years * 10;
		this.keywords = keywords * 20;
		this.links = links * 20;
		this.refreshScore();
	}

	/**
	 * adding a site with only name and score, but not customized values.
	 */
	public Url(String name, int score) {
		this.score = score;
		this.name = name;
	}

	/**
	 * used by editscore() After 4 values have been changed to custom, updates the
	 * score. (used by old program and constructur)
	 */
	public void refreshScore() {
		this.score = years + links + keywords + paid;
	}

	public void printValues() {
		System.out.println(name);
		System.out.println("score: " + score);
	}

	public void printAllInfo() {
		System.out.println("Index before QuickSort (if applicable): " + rankBeforeSort);
		System.out.println(name);
		System.out.println("score: " + score);
		System.out.println();

	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int i) {
		score = i;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public int getLinks() {
		return links;
	}

	public void setLinks(int links) {
		this.links = links;
	}

	public int getKeywords() {
		return keywords;
	}

	public void setKeywords(int keywords) {
		this.keywords = keywords;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public int getRankBeforeSort() {
		return rankBeforeSort;
	}

	public void setRankBeforeSort(int i) {
		this.rankBeforeSort = i;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Example: if name is https://en.wikipedia.org/wiki/Page_view
	 * 
	 * @return Page_View
	 */
	public String getNameAfterDashes() {
		int counter = 0; // number of dashes found
		int progress = 0; // progress throughout string

		for (int i = 0; i < name.length(); i++) {
			String letter = name.substring(i, i + 1);
			if (letter.equals("/")) {
				counter++;
			}
			progress++;
			// we want to find four dashes
			if (counter == 4) {
				break;
			}

		}
		// if 4 dashes not found, return full URL from beginning
		if (counter != 4) {
			return name;
		}
		// return the string after the 4th dash
		String important = name.substring(progress);
		return important;

	}

}
