package test4;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * This is the real main method. I do it this way to keep it neat. Handles the
 * processing of commands by system.in and the implementation of those commands
 * with the heap
 */
public class Program {
	ArrayList<String> searches = new ArrayList<>();
	Scanner scan = new Scanner(System.in);;
	UrlHeap heap; // holds the heap that handles the array of Urls
	Url urls[];

	String input = "";

	/**
	 * Look at the comments below. Outer loop keeps you inside the program unless
	 * you !exit. Inner loop breaks only if you want to search again
	 */
	public void start() {
		while (true) { // the first for loop keeps everything constantly scanning, cannot break out of
						// it
			initSearchAndHeap();
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
	 * initiates the search and creates a max heap based off of the searches. In its
	 * own method to separate it from commands.
	 */
	public void initSearchAndHeap() {

		System.out.println("Enter your search term.");

		String term = scan.nextLine();
		searches.add(term);
		Spider spider = new Spider();
		String[] sites = spider.search("https://en.wikipedia.org/wiki/List_of_most_popular_websites", term);

		// takes all the sites successfully searched by spider and stores it in urls
		urls = new Url[sites.length];

		// take the urls and build class Url
		for (int i = 0; i < urls.length; i++) {

			urls[i] = new Url(sites[i]);
			// pagerank assigned by webCrawler, not google
			urls[i].setRankBeforeSort(i);
		}
		heap = new UrlHeap(urls);
		heap.Build_Max_Heap();
		this.heapSortDisplay();
		System.out.println();

	}

	/**
	 * uses heap priority queue and extract max to print out the top 10 values.
	 */
	public void qDisplay() {

		UrlHeap heap2 = new UrlHeap(heap.getArray().clone());

		PriorityQueue<Url> queue = new PriorityQueue<Url>();
		int size = heap2.getArray().length;
		/**
		 * uses four for loops instead of two to prevent array OOB error if results
		 * remaining is less than 10
		 */
		if (size <= 10) {
			for (int i = 0; i < size; i++) {
				Url extracted = heap2.Heap_Extract_Max_Url();
				queue.add(extracted);
			}
		} else if (size > 10) {
			for (int i = 0; i < 10; i++) {
				Url extracted = heap2.Heap_Extract_Max_Url();
				queue.add(extracted);
			}
		}
		//prints out from queue
		size = queue.size();
		if (size <= 10) {
			for (int i = 0; i < size; i++) {
				int rank = i + 1;
				System.out.println("Google PageRank: " + rank);
				queue.remove().printValues();
				System.out.println();

			}
		} else if (size > 10) {
			for (int i = 0; i < 10; i++) {
				int rank = i + 1;
				System.out.println("Google PageRank: " + rank);
				queue.remove().printValues();
				System.out.println();
			}
		}

	}

	/**
	 * Prints out heapsorted array (WITHOUT QUEUUE IMPLEMENTATOIN). please see
	 * qdisplay() for queue implementation.
	 * 
	 */
	public void heapSortDisplay() {
		System.out.println("calling heapSortDisplay()");
		// we use heap2 because the heapsort messes up the size variable in UrlHeaps...
		UrlHeap heap2 = new UrlHeap(heap.getArray().clone());
		heap2.HeapSort();
		Url[] sorted = heap2.getArray();
		int x = 1; //variable is for pagerank ORDER
		if (sorted.length >= 10) {
			for (int i = sorted.length - 1; i > sorted.length - 11; i--) {
				System.out.println("Google PageRank: " + x);
				x++;

				int beforeSorted = sorted[i].getRankBeforeSort() + 1; // arrays start at 0.
				System.out.println("PageRank BEFORE being sorted: " + beforeSorted);

				heap2.printValue(i);
				System.out.println();

			}
		}

		else if (sorted.length < 10) {
			for (int i = sorted.length - 1; i >= 0; i--) {
				System.out.println("Google PageRank: " + x);
				x++;
				int beforeSorted = sorted[i].getRankBeforeSort() + 1; // arrays start at 0.
				System.out.println("PageRank BEFORE being sorted: " + beforeSorted);
				heap2.printValue(i);
				System.out.println();
			}
		}
	}

	/**
	 * used by scanner to check commands
	 */
	public void runCommands() {

		if (input.equals("!highscore")) {
			highscore();
		}

		else if (input.equals("!bantop")) {
			banTop();
		} else if (input.equals("!editscore")) {
			editScore();
		} else if (input.equals("!customedit")) {
			customEdit();
		}

		else if (input.equals("!qdisplay")) {
			qDisplay();
		}

		else if (input.equals("!addsite")) {
			addSite();
		}

		else if (input.equals("!displayraw")) {
			displayRaw();
		} else if (input.equals("!pay")) {
			pay();
		} else if (input.equals("!addcustomsite")) {
			addCustomSite();
		}

		else if (input.equals("!exit")) {
			System.exit(0);
		}

		else if (input.equals("!commands")) {
			printCommands();
		}

		else if (input.equals("!display")) {
			display();
		} else if (input.equals("!topsearches")) {
			displayTop();
		}

		else {
			System.out.println("Command not recognized. Type !commands for a list of commands.");
		}

	}

	// removes top website. Gets us sued.
	private void banTop() {
		System.out.println("removed a website with progress " + heap.Heap_Extract_Max());

	}

	/**
	 * adds a site with a custom score
	 */
	private void addSite() {
		// h.bmh
		System.out.println("Enter the URL of the new website. (STRING)");
		String url = scan.nextLine();
		System.out.println("Enter the amount of money they paid to the nearest (+INTEGER)");
		int paid = scan.nextInt();
		heap.Max_Heap_Insert(url, paid);

	}

	/**
	 * allows you to add a custom website with a supplied amount paid, date, links,
	 * and keywords
	 */
	private void addCustomSite() {
		// h.bmh
		System.out.println("Enter the URL of the new website. (STRING)");
		String url = scan.nextLine();
		System.out.println("Enter the amount of money they paid to the nearest (+INTEGER)");
		int paid = scan.nextInt();
		System.out.println("Enter the number of years old they are (+INTEGER)");
		int date = scan.nextInt();
		System.out.println("Enter the number of keywords this website has related to your search (+INTEGER)");
		int keywords = scan.nextInt();
		System.out.println("Enter the number of links this website has to it (+INTEGER)");
		int links = scan.nextInt();

		Url score = new Url(url, paid, date, keywords, links);
		heap.Max_Heap_Insert(url, score.getScore());

	}

	// prints out urls and info in array
	public void displayRaw() {
		heap.printValues();

	}

	public void display() {
		this.heapSortDisplay();
		// h.bmh
	}

	public void highscore() {
		heap.Print_Heap_Maximum();
	}

	/**
	 * used by !commands. Displays all the commands for the user to see
	 */
	public void printCommands() {

		System.out.println();
		System.out.println("!search - search again");
		System.out.println("!topsearches - shows yours most frequently searched terms");
		System.out.println("!qdisplay - uses priority queue implementation to show top searches");
		System.out.println("!addsite - adds a website with a custom score");
		System.out.println("!pay - increase a customer's score by X amount");
		System.out.println("!customedit - edit the 4 values of a single website");
		System.out.println("!editscore - directly edit the score of one website");
		System.out.println("!bantop - Removes the #1 ranked website from Google.");
		System.out.println("!highscore - shows the relevancy score of the most relevant website");
		System.out.println("!addcustomsite - Don't call this unless you want to. Implemented it by accident.");
		System.out.println("!exit - exits the program. Useless.");
		System.out.println("!displayraw - Displays ALL 35 websites. Used for debugging.");
		System.out.println(
				"!display - prints out the websites in their proper order with webcrawler pull order with heapsort implementation. Only intended to be used by the initial search.");
		System.out.println();

	}

	/**
	 * displays your most frequent searches
	 * SortTracker will return an array of SearchFrequencies, which hold the term and its frequency
	 * SearchHeap will sort that array
	 */
	public void displayTop() {
		SortTracker tracker = new SortTracker(searches);
		SearchHeap searchHeap = new SearchHeap(tracker.getSearchFrequencies());
		searchHeap.HeapSort();
		SearchFrequency[] uniques = searchHeap.getArray();
		//handles printing. Two different loops prevent OOB error.
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
	 * adds x to the score of the customer because they paid x amount
	 */
	public void pay() {
		this.heapSortDisplay(); // for convenience of tester
		//prints out the IDs so user can select
		for (int i = 0; i < heap.getArray().length; i++) {
			// id = index
			System.out.println("ID: " + i);
			heap.printValue(i);
			System.out.println();
		}
		System.out.println("Enter the ID of the customer who paid. (INTEGER)");
		int ID = scan.nextInt();
		// their new score will be old score + amount paid
		int score = heap.getScore(ID);
		System.out.println("The customer is " + heap.getOneName(ID));
		System.out.println("Enter the amount this customer paid, rounded to the nearest +INTEGER");
		int paid = scan.nextInt();
		int newScore = score + paid;
		heap.Heap_Increase_Key(ID, newScore);
		System.out.println("Customer's score has been increased by " + paid);

	}

	/**
	 * edits all 4 values of one score.
	 */
	public void customEdit() {
		this.qDisplay(); // for convenience of tester.

		for (int i = 0; i < heap.getArray().length; i++) {
			System.out.println("ID: " + i);
			heap.printValue(i);
			System.out.println();
		}
		System.out.println("Enter the ID of the customer who you want to edit. (INTEGER)");
		int ID = scan.nextInt();
		Url recieved = heap.getUrl(ID);
		System.out.println("Customer recieved: " + recieved.getName());
		System.out.println("Score of customer: " + recieved.getScore());
		System.out.println();

		System.out.println("KEYWORDS value of this site: " + recieved.getKeywords());
		System.out.println("Enter the new KEYWORD value for this site (+INTEGER).");
		int keywords = scan.nextInt();
		recieved.setKeywords(keywords);
		;

		System.out.println("AGE value of this site: " + recieved.getYears());
		System.out.println("Enter the new AGE value for this site (+INTEGER).");
		int years = scan.nextInt();
		recieved.setYears(years);

		System.out.println("LIKNKS value of this site: " + recieved.getLinks());
		System.out.println("Enter the new LINKS value for this site (+INTEGER).");
		int links = scan.nextInt();
		recieved.setLinks(links);

		System.out.println("PAID value of this site: " + recieved.getPaid());
		System.out.println("Enter the new PAID value for this site (+INTEGER).");
		int paid = scan.nextInt();
		recieved.setPaid(paid);

		//recalculates score of the Url based off of the variables you edited
		recieved.refreshScore();

		System.out.println(" new KEYWORDS value of this site: " + recieved.getKeywords());
		System.out.println("new AGE value of this site: " + recieved.getYears());
		System.out.println("new LIKNKS value of this site: " + recieved.getLinks());
		System.out.println("new PAID value of this site: " + recieved.getPaid());
		System.out.println("new Score value of this site: " + recieved.getScore());
		
		//maintains heap structure no matter the values entered. Can't use heap_increase_key because value might be below original
		heap.Build_Max_Heap();
	}

	/**
	 * lets you edit the score of one Url
	 */
	public void editScore() {
		qDisplay();
		//displays all the IDs for the user so that they may select an ID
		for (int i = 0; i < heap.getArray().length; i++) {
			System.out.println("ID: " + i);
			heap.printValue(i);
			System.out.println();
		}
		System.out.println("Enter the ID of the customer who you want to edit. (INTEGER)");
		int ID = scan.nextInt();
		Url recieved = heap.getUrl(ID);
		System.out.println("Customer recieved: " + recieved.getName());
		System.out.println("Score of customer: " + recieved.getScore());
		System.out.println("Enter their new score");
		int newScore = scan.nextInt();
		recieved.setScore(newScore);
		heap.Build_Max_Heap(); // re heapifies. Can't use Increase key. Increase key only applies to bigger
								// values.
		System.out.println("Their new score is " + recieved.getScore());
	}
}
