package v5_add_comments_pretty_up;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This is the real main method. Handles the processing of commands by system
 * and the implementation of those commands with the tree
 */
public class Program {

	Scanner scan = new Scanner(System.in);
	Url urls[]; // holds the results of the first search
	Url sorted[]; // holds the sorted array after quicksort. I think I forgot to use this
	UrlTree tree; // the tree that holds the websites
	// this ArrayList is used in delete and searchbyrank
	ArrayList<UrlNode> nodes;
	String input = "";

	// used strictly for debugging. Helped me create program2 class.
	ArrayList<UrlNode> debugNodes = new ArrayList<>();

	ArrayList<String> searches = new ArrayList<String>();

	/**
	 * Look at the comments below. Outer loop keeps you inside the program unless
	 * you !exit. Inner loop breaks only if you want to search again
	 */
	public void start() {
		while (true) { // the first for loop keeps everything constantly scanning, cannot break out of
						// it
			initSearchAndTreePrint();

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
	 * Prompts the user to search for a term. Creates a new spider to search for
	 * that term. Based off of the urls that you get, turn those urls into Url
	 * objects with a score turn those Url objects into a UrlNode.
	 */
	public void initSearchAndTreePrint() {
		Spider spider = new Spider();
		System.out.println("Enter your search term.");

		// takes the input from the user
		String term = scan.nextLine();

		String[] sites = spider.search("https://en.wikipedia.org/wiki/List_of_most_popular_websites", term);

		// takes all the sites successfully searched by spider and stores it in urls
		urls = new Url[sites.length];

		// take the urls and build class Url
		for (int i = 0; i < urls.length; i++) {

			urls[i] = new Url(sites[i]);
			// raw pagerank
			urls[i].setRankBeforeSort(i);
		}

		tree = new UrlTree();

		// add all the nodes to the tree.
		for (Url x : urls) {
			UrlNode node = new UrlNode(x);
			debugNodes.add(node);
			tree.insert(node);
		}
		display();

	}

	/**
	 * used by scanner to check commands
	 */
	public void runCommands() {
		// Displays the urls in a sorted fashion
		if (input.equals("!display")) {
			display();
		}

		// used for debugging
		if (input.equals("!index")) {
			insertOrder();
		}

		// lets the user search for a website by rank
		else if (input.equals("!ranksearch")) {
			searchByRank();
		}

		// add a website to the tree
		else if (input.equals("!add")) {
			add();
		}

		// delete a website from the tree
		else if (input.equals("!delete")) {
			delete();
		}

		// lists all the possible commands
		else if (input.equals("!commands")) {
			printCommands();

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
		// adds all the nodes from the tree into an ArrayList
		addRecurse(tree.getRoot());
		// displays from the arrayList so the user can select which node to delete
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println("ID: " + i);
			nodes.get(i).getUrl().printValues();
			System.out.println(nodes.get(i).color);
			System.out.println();
		}
		System.out.println();
		System.out.println("Enter the ID of the node you would like to delete (INTEGER, ~0-29)");
		int id = scan.nextInt();
		UrlNode delete = nodes.get(id);
		System.out.println("deleteing the node: ");
		delete.printInfo();
		// get a reference to the node
		System.out.println("searching...");
		// searches for a node with the same name as the node you want to delete
		UrlNode deleteThis = tree.searchByNode(tree.root, delete);
		System.out.println("deleting the node: ");
		deleteThis.printInfo();
		tree.delete(deleteThis);
		System.out.println("Deleted. Type !display to view results");
	}

	// displays the sorted array using addRecurse
	public void display() {
		nodes = new ArrayList<UrlNode>();
		this.addRecurse(tree.root);

		int rank = 1;
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println("PageRank: " + rank);
			rank++;
			nodes.get(i).printInfo();
		}

	}

	// lets the user enter a pagerank so that they can get the website tied to that
	// pageRank
	public void searchByRank() {
		System.out.println("Enter a reasonable number, ~1-30, to get a website of that PageRank");
		int rank = scan.nextInt();
		UrlNode x = tree.getRoot();

		nodes = new ArrayList<UrlNode>();
		addRecurse(x);

		// the arraylist is sorted because of tree's inorderwalk, so just pull from the
		// arraylist
		UrlNode found = nodes.get(rank - 1);
		System.out.println("PageRank: " + rank);
		
		//print the node's information
		found.printInfo();
	}

	/**
	 * instead of printing the node information, adds the nodes to an arraylist of
	 * nodes so that it can be used
	 */
	public void addRecurse(UrlNode x) {

		if (x != tree.nil) {
			addRecurse(x.right);
			nodes.add(x);
			addRecurse(x.left);
		}
	}

	//used for debugging
	public void insertOrder() {
		for (UrlNode x : debugNodes) {
			System.out.println(x.url.score + ",");
		}
	}

	/**
	 * used by !commands
	 */
	public void printCommands() {

		System.out.println();
		System.out.println("!add - Add a website by supplying score and URL.");
		System.out.println("!delete - Remove a website from the tree.");
		System.out.println("!display - Show all items in the tree. Use after add / delete.");
		System.out.println("!ranksearch - Enter a PageRank, get the corresponding website.");
		System.out.println();

	}

}
