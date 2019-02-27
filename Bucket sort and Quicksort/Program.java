package QuickSortTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This is the real main method. Handles the processing of commands by system
 * and the implementation of those commands with the tree
 */
public class Program {
	SearchBucket bucket = new SearchBucket(); // bucket that holds searches
	Scanner scan = new Scanner(System.in);
	Url urls[]; // holds the results of the first search
	Url sorted[]; // holds the sorted array after quicksort. I think I forgot to use this
	UrlTree tree; // the tree that holds the websites
	// this ArrayList is used in delete and searchbyrank
	ArrayList<UrlNode> nodes;
	String input = "";

	ArrayList<String> searches = new ArrayList<String>();

	/**
	 * Look at the comments below. Outer loop keeps you inside the program unless
	 * you !exit. Inner loop breaks only if you want to search again
	 */
	public void start() {
		while (true) { // the first for loop keeps everything constantly scanning, cannot break out of
						// it
			initSearchAndQuickSortPrint();
			tree = new UrlTree();
			createTree(tree);

			while (true) { // the second for loop is for constantly processing commands.
				System.out.println("Type !commands to view commands");
				input = scan.nextLine();
				if (input.equals("!search")) { // the only special command is !search.
					// by breaking out of a new while loop, you erase the info from the old heap
					break;
				} else {
					runCommands();
				}
			}
		}
	}

	/**
	 * Only called at the end of initSearchAndQuickSortPrint(). Url[] urls should
	 * not be empty so that the tree can be created, whether it be from old results
	 * or new ones.
	 */
	public void createTree(UrlTree tree) {
		for (Url x : urls) {
			UrlNode node = new UrlNode(x);
			tree.insert(node);
		}
	}

	/**
	 * Prompts the user to search for a term. If the term has been searched
	 * for in the past, pull the results tied to the term. Else, create
	 * a new search and store those results in a the bucket.
	 * Note: The way searchbucket works != UrlBucket. 
	 */
	public void initSearchAndQuickSortPrint() {
		Spider spider = new Spider();
		System.out.println("Enter your search term.");

		String term = scan.nextLine();
		term = term.toLowerCase();
		searches.add(term);

		// get a reference to the linkedlist
		// inside the bucket that the term is stored in
		char first = term.charAt(0);
		int slot = (int) first;
		Search dummy = new Search(term, new Url[0]);
		LinkedList<Search>[] searches = bucket.getList();
		LinkedList<Search> list = searches[slot];

		// If searched term exists, get a reference
		// to the term. Get the list of URLs tied
		// to that term to display ordered results
		if (this.searchForTerm(term)) {

			for (Search x : list) {
				if (x.equals(dummy)) {
					urls = x.getResults();
					quickSortDisplay();
					break;
				}
			}
			System.out.println("Words you've searched starting with the letter " + first);
			for (Search x : list) {
				System.out.println(x.getSearch());
			}
			System.out.println();

		}

		else { // only searches if search hasn't been previously Searched

			String[] sites = spider.search("https://en.wikipedia.org/wiki/List_of_most_popular_websites", term);

			// takes all the sites successfully searched by spider and stores it in urls
			urls = new Url[sites.length];

			// take the urls and build class Url
			for (int i = 0; i < urls.length; i++) {

				urls[i] = new Url(sites[i]);
				// raw pagerank
				urls[i].setRankBeforeSort(i);
			}

			// creates a Search to tie the term to the urls
			Search search = new Search(term, urls);
			quickSortDisplay();

			bucket.add(search);
			System.out.println("Words you've searched starting with the letter " + first);
			for (Search x : list) {
				System.out.println(x.getSearch());
			}
			System.out.println();
		}

	}

	/**
	 * used by scanner to check commands
	 */
	public void runCommands() {
		if (input.equals("!display")) {
			display();
		}

		else if (input.equals("!ranksearch")) {
			searchByRank();
		}

		else if (input.equals("!add")) {
			add();
		}

		else if (input.equals("!delete")) {
			delete();
		}

		else if (input.equals("!commands")) {
			printCommands();
		} else if (input.equals("!sort")) {
			sortUrls();
		} else if (input.equals("!showtop")) {
			showTopSearches();
		} else if (input.equals("!topsort")) {
			topTermUrlSort();
		}

	}

	// lets you add a URL to the tree by supplying name and score
	public void add() {
		System.out.println("Enter the URL of the new website. (STRING)");
		String url = scan.nextLine();
		System.out.println("Enter the score of the website (+INTEGER)");
		int paid = scan.nextInt();
		Url website = new Url(url, paid);
		UrlNode siteNode = new UrlNode(website);
		tree.insert(siteNode);
	}

	// lets the user delete from the tree by selecting the ID they'd like to delete
	// deletes based off of name, not score.
	public void delete() {
		nodes = new ArrayList<UrlNode>();
		addRecurse(tree.getRoot());
		// displays so user can select which ID to delete
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println("ID: " + i);
			nodes.get(i).getUrl().printValues();
			System.out.println();
		}
		System.out.println();
		System.out.println("Enter the ID of the node you would like to delete (INTEGER, ~0-30)");
		int id = scan.nextInt();
		UrlNode delete = nodes.get(id);
		tree.deleteNodeByName(delete);
		System.out.println("DELETED");
	}

	// displays the sorted array using addRecurse
	public void display() {
		nodes = new ArrayList<UrlNode>();
		this.addRecurse(tree.root);
		int rank = 1;
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println("PageRank: " + rank);
			rank++;
			nodes.get(i).getUrl().printAllInfo();
		}

	}

	// it's O(n) anyways so I'm making it O(n) but easier to write/debug
	public void searchByRank() {
		System.out.println("Enter a reasonable number, ~1-30, to get a website of that PageRank");
		int rank = scan.nextInt();
		UrlNode x = tree.getRoot();
		nodes = new ArrayList<UrlNode>();
		addRecurse(x);
		UrlNode found = nodes.get(rank - 1);
		System.out.println("PageRank: " + rank);
		found.printInfo();
	}

	/**
	 * instead of printing the node information, adds the nodes to an arraylist of
	 * nodes so that it can be used
	 */
	public void addRecurse(UrlNode x) {

		if (x != null) {
			addRecurse(x.right);
			nodes.add(x);
			addRecurse(x.left);
		}
	}

	/**
	 * used by !commands
	 */
	public void printCommands() {

		System.out.println();
		System.out.println("!search - Search again.");
		System.out.println("!add - Add a website by supplying score and URL.");
		System.out.println("!delete - Remove a website from the tree.");
		System.out.println("!display - Show all items in the tree. Use after add / delete.");
		System.out.println("!ranksearch - Enter a PageRank, get the corresponding website.");
		System.out.println("!sort - Display the CURRENT url results in ABC order.");
		System.out.println("!topsort - Sorts the urls(results) of the most frequently searched term.");
		System.out.println("!showtop - shows most frequently searched words.");
		System.out.println();

	}

	/**
	 * uses quick sort to print out the website based off of their scores
	 */
	public void quickSortDisplay() {
		sorted = urls.clone();
		UrlQuickSort sorter = new UrlQuickSort();
		sorter.quickSort(sorted, 0, sorted.length - 1);
		int rank = 1;
		// from last to first to print in descending order
		for (int i = sorted.length - 1; i >= 0; i--) {
			System.out.println("PageRank: " + rank);
			sorted[i].printAllInfo();
			System.out.println();
			rank++;
		}
	}

	/**
	 * If the term exists in the bucket, return true, else return false Please read
	 * SearchBucket comments
	 * 
	 */
	public boolean searchForTerm(String term) {
		char first = term.charAt(0);
		int slot = (int) first;
		Search dummy = new Search(term, new Url[0]);
		LinkedList<Search>[] searches = bucket.getList();
		LinkedList<Search> list = searches[slot];
		// nodes are compared to each other based off of String search term
		// look at Search's.equals method.
		if (list.contains(dummy)) {
			return true;
		}

		return false;

	}

	/**
	 * I was confused on whether we were supposed to sort in abc order the searched
	 * keywords or the urls so I'm adding both features by using my code for sorting
	 * keywords to sort urls.
	 */
	public void sortUrls() {
		UrlBucket bucket = new UrlBucket();

		// add all the urls into the bucket
		for (Url url : urls) {
			bucket.add(url);
		}

		// unlike the SearchBucket, sorting has to happen manually
		bucket.sort();
		LinkedList<Url> sorted = bucket.appendAll();
		for (Url x : sorted) {
			System.out.println(x.getName());
		}
	}

	/**
	 * used for debugging to find the most popular search result. Practically copy
	 * pasted from PA1. Prints the top 10 most popular search results
	 */
	public void showTopSearches() {
		//SortTrackers take an arraylist of searches and return an
		//array of SearchFrequency objects with
		//String term and int number of times term was searched as its data
		SortTracker tracker = new SortTracker(searches);
		SearchFrequencyQuickSort sorter = new SearchFrequencyQuickSort();
		SearchFrequency[] uniques = tracker.getSearchFrequencies();
		sorter.quickSort(uniques, 0, uniques.length - 1);

		if (uniques.length <= 10) {
			for (int i = uniques.length - 1; i >= 0; i--) {
				System.out.println("Term: " + uniques[i].getSearch());
				System.out.println("Frequency: " + uniques[i].getFrequency());
				System.out.println();
			}
		} else if (uniques.length > 10) {
			for (int i = uniques.length - 1; i > uniques.length - 11; i--) {
				System.out.println("Term: " + uniques[i].getSearch());
				System.out.println("Frequency: " + uniques[i].getFrequency());
				System.out.println();
			}
		}
	}

	/**
	 * gets the most frequently searched term. Gets the urls tied to that term sorts
	 * those urls in abc order and prints the result
	 */
	public void topTermUrlSort() {
		// gets the frequency of all the searches
		SortTracker tracker = new SortTracker(searches);
		SearchFrequencyQuickSort sorter = new SearchFrequencyQuickSort();
		SearchFrequency[] uniques = tracker.getSearchFrequencies();
		sorter.quickSort(uniques, 0, uniques.length - 1);

		// gets the top most searched term of all the terms
		SearchFrequency top = uniques[uniques.length - 1];
		String term = top.getSearch();
		System.out.println("Top search is: " + term);
		System.out.println("Searches: " + top.getFrequency());

		// we know the Search term exists in our bucket of searches
		// we want to get the list of urls tied to that search term
		char first = term.charAt(0);
		int slot = (int) first;

		Search dummy = new Search(term, new Url[0]);
		LinkedList<Search>[] searches = bucket.getList();
		LinkedList<Search> list = searches[slot];
		// iterate through the list until we get our search term
		for (Search search : list) {
			if (search.equals(dummy)) {
				Url[] results = search.getResults();
				// Url buckets are for sorting Urls
				UrlBucket urlBucket = new UrlBucket();

				// add all the urls tied to the search term we want to the bucket
				for (Url result : results) {
					urlBucket.add(result);
				}

				urlBucket.sort();
				LinkedList<Url> sorted = urlBucket.appendAll();
				for (Url x : sorted) {
					System.out.println(x.getName());
				}

				break;
			}
		}

	}
}
