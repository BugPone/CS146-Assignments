package v5_add_comments_pretty_up;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
**PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
*PROGRAM2 IS FOR DEBUGGING. PLEASE SEE THE REGULAR PROGRAM CLASS
 */
public class Program2 {
	ArrayList<UrlNode> usedNodes = new ArrayList<>();
	Scanner scan = new Scanner(System.in);
	Url urls[]; // holds the results of the first search
	Url sorted[]; // holds the sorted array after quicksort. I think I forgot to use this
	UrlTree tree = new UrlTree(); // the tree that holds the websites
	// this ArrayList is used in delete and searchbyrank
	ArrayList<UrlNode> nodes;
	String input = "";

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
	 * Prompts the user to search for a term. If the term has been searched for in
	 * the past, pull the results tied to the term. Else, create a new search and
	 * store those results in a the bucket. Note: The way searchbucket works !=
	 * UrlBucket.
	 */
	public void initSearchAndTreePrint() {
		int[] arr = new int[] {
				230, 333, 243, 345, 304, 220, 161, 167, 240, 322, 334, 115, 211, 213, 325, 235, 117,
				260, 116, 243, 283, 171, 257, 277, 199, 270, 260, 153, 169, 331, };
		int j = 1;
		for (int x : arr) {
			UrlNode node = new UrlNode(x, "dummy" + j);
			node.url.rankBeforeSort = j;
			j++;
			usedNodes.add(node);
			tree.insert(node);
		}
		display();
		tree.refreshNodeScores(tree.root);

	}

	/**
	 * used by scanner to check commands
	 */
	public void runCommands() {
		if (input.equals("!display")) {
			display();
		}

		if (input.equals("!index")) {
			insertOrder();
		}

		else if (input.equals("!ranksearch")) {
			searchByRank();
		}

		else if (input.equals("!add")) {
			add();
		}

		else if (input.equals("!delete")) {
			delete();
			tree.refreshNodeScores(tree.root);
		}

		else if (input.equals("!commands")) {
			printCommands();
		}
		
		//used for debugging
		
//		} else if (input.equals("print")) {
//			tree.printTree(tree.root);
//			tree.refreshNodeScores(tree.root);
//			System.out.println("done");
//		}
//		else if (input.equals("print2")) {
//			tree.TopView(tree.root);
//		}

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
			System.out.println(nodes.get(i).color);
			System.out.println();
		}
		System.out.println();
		System.out.println("Enter the ID of the node you would like to delete (INTEGER, ~0-30)");
		int id = scan.nextInt();
		UrlNode delete = nodes.get(id);
		System.out.println("deleteing the node: ");
		delete.printInfo();
		// get a reference to the node
		System.out.println("searching...");
		UrlNode deleteThis = tree.searchByNode(tree.root, delete);
		System.out.println("deleting...");
		deleteThis.printInfo();
		tree.delete(deleteThis);
		tree.refreshNodeScores(tree.root);
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
			nodes.get(i).printInfo();
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

		if (x != tree.nil) {
			addRecurse(x.right);
			nodes.add(x);
			addRecurse(x.left);
		}
	}

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
		System.out.println("!search - Search again.");
		System.out.println("!add - Add a website by supplying score and URL.");
		System.out.println("!delete - Remove a website from the tree.");
		System.out.println("!display - Show all items in the tree. Use after add / delete.");
		System.out.println("!ranksearch - Enter a PageRank, get the corresponding website.");
		System.out.println();

	}

}
