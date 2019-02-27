package test4;

/**
 * An instance of a URL pulled from the Crawler
 * Turns it into a class with associated values randomly generated
 * Implements comparable for Queue implementation
 *@author Leo
 */
public class Url implements Comparable<Url> {
	int rankBeforeSort = 0;
	int score = 0;
	int years = 0;
	int links = 0;
	int keywords = 0;
	int paid = 0;
	
	public String name = "";
	
	/**
	 * Makes Url object with randomized values.
	 * @param name - the URL
	 */
	public Url(String name) {
		years = (int) (Math.random()*100);
		links = (int) (Math.random()*100);
		keywords = (int) (Math.random()*200);
		paid = (int) (Math.random()*100);
		
		score = years + links + keywords + paid;
		this.name = name;	
	}
	
	/**
	 * Used for adding a site with customized values. 
	 */
	public Url(String name, int paid, int years, int keywords,int links) {
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
	 * used by editscore()
	 * After 4 values have been changed to custom, updates the score.
	 */
	public void refreshScore() {
		this.score = years + links + keywords + paid;
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
	public void printValues() {
		System.out.println(name);
		System.out.println("score: " + score);
	}

	public int compareTo(Url o) {
		if (this.score == o.score) {
			return 0;
		}
		else if(this.score > o.getScore()) {
			return -1;
		}
		else return 1;
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

}

