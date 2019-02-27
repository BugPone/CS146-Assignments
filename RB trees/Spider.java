package v5_add_comments_pretty_up;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * imported web crawler
 *
 */
public class Spider {
	// # of attempts before filling with dummy nodes
	private static final int MAX_PAGES_TO_SEARCH = 100;

	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();

	// # of urls you want in your data structure
	private static final int SUCCESSES_NEEDED = 30;

	// tracks # of successful urls found. Needs to be outside the loop.
	int successes = 0;

	// successful urls
	String[] urlsFound = new String[SUCCESSES_NEEDED];

	// tracks total searched websites
	int breaker = 0;

	/**
	 * Our main launching point for the Spider's functionality. Internally it
	 * creates spider legs that make an HTTP request and parse the response (the web
	 * page).
	 * 
	 * @param url
	 *            - The starting point of the spider
	 * @param searchWord
	 *            - The word or string that you are searching for
	 * @return at the end of the method, it returns urlsFound[], AKA the successful
	 *         Urls.
	 */
	public String[] search(String url, String searchWord) {
		while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH && breaker < MAX_PAGES_TO_SEARCH) {
			System.out.println("search # " + breaker + "/" + MAX_PAGES_TO_SEARCH);
			System.out.println("successes: " + successes + "/" + SUCCESSES_NEEDED);
			breaker++;
			String currentUrl;
			SpiderLeg leg = new SpiderLeg();
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
									// SpiderLeg
			boolean success = leg.searchForWord(searchWord);
			if (success) {
				System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));

				// if success, add that successful URL to the URL you were searching.
				urlsFound[successes] = currentUrl;

				successes++; // breaks loop if you have the number of urls you want
				if (successes == SUCCESSES_NEEDED) {
					break;
				}
			}
			this.pagesToVisit.addAll(leg.getLinks());
		}
		System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");

		/**
		 * Finished max # of searches. Enters "if" statement if you could not find the
		 * number URLs you wanted after the max attempts of searches.
		 * Fills the rest of the array with Dummy URLs because term was too vague.
		 */
		if (successes < SUCCESSES_NEEDED) {
			// prevents error in the next for loop.

			/**
			 * fills the rest of the array with dummy URLs
			 */
			Iterator<String> iterator = pagesVisited.iterator();
			for (int i = successes; i < urlsFound.length; i++) {

				String dummyUrl = "null2";

				// (gives it a base URL to make dummy from?)
				if (iterator.hasNext()) {
					dummyUrl = iterator.next();
				}
				dummyUrl = "DUMMY_" + i + "_" + dummyUrl;
				urlsFound[i] = dummyUrl;
			}
		}
		for (String x : urlsFound) {
			System.out.println(x);
		}

		return urlsFound;
	}

	public void incBreaker() {
		breaker++;
	}

	public int getBreaker() {
		return breaker;
	}

	/**
	 * Returns the next URL to visit (in the order that they were found). We also do
	 * a check to make sure this method doesn't return a URL that has already been
	 * visited.
	 * 
	 * @return
	 */
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
}