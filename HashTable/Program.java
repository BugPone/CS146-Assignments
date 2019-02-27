package HashTable;

import java.util.Scanner;

/**
 * Holds the data structure we are manipulating. Takes user commands so that
 * user can manipulate the data structure
 **/

public class Program {
	Scanner scan = new Scanner(System.in);
	HashTable table = new HashTable();
	String input = "";

	/**
	 * Initializes everything that needs to happen at the beginning.
	 */
	public void start() {
		// fills the array with names
		fillArray();

		// prints the names
		printAll();

		// prints the commands user can do
		printCommands();

		// while(true) keeps the user inside the program so the user can keep running
		// different commands.
		while (true) {
			System.out.println("Type 'commands' to view commands");
			input = scan.nextLine();
			runCommands();
		}

	}

	/**
	 * Checks what the user entered. Executes the corresponding command.
	 */
	void runCommands() {
		if (input.equals("new")) {
			create();
		}
		if (input.equals("add")) {
			addFriend();
		}

		if (input.equals("del")) {
			removeFriend();
		}

		if (input.equals("check")) {
			checkFriends();
		}

		if (input.equals("checktwo")) {
			checkTwoFriends();
		}

		if (input.equals("print")) {
			printAll();
		}

		if (input.equals("qwertyuiop")) {
			fillArray();
		}

		if (input.equals("commands")) {
			printCommands();
		}

	}

	private void printAll() {
		table.print();

	}

	/**
	 * User enters two names. First name is user A, second name is user B. If A is
	 * friends with B AND B is friends with A, prints "YES". Else prints "NO".
	 */
	private void checkTwoFriends() {
		System.out.println("Check if person A and person B are BOTH friends.");
		System.out.println("Prints yes if A is friends with B AND B is friends with A");
		System.out.println("===============================");
		System.out.println("Enter the name of person A");
		String personA = scan.nextLine();

		// gets the Person object tied to the String name.
		Person personAObj = table.search(personA);

		// if null, doesn't exist.
		if (personAObj == null) {
			System.out.println("PERSON NOT FOUND, EXITING.");
			return;
		}

		// same process as previous comments
		String personB = scan.nextLine();
		Person personBObj = table.search(personB);
		if (personBObj == null) {
			System.out.println("PERSON NOT FOUND, EXITING.");
			return;
		}

		// gets the friends list of both objects.
		FriendsList listA = personAObj.getFriends();
		FriendsList listB = personBObj.getFriends();

		System.out.println();
		System.out.println();

		// checks if personB exists in personA's friends list. If null, then it doesn't
		// exist, print "no".
		Person checker = listA.Search(personB);
		if (checker == null) {
			// null means was not found in friends list
			System.out.println("NO");
			return;
		}

		// vice versa.
		Person checker2 = listB.Search(personA);
		if (checker2 == null) {
			System.out.println("NO");
			return;
		}
		// they both have each other added because we could find a person in each list.
		System.out.println("YES");

	}

	/**
	 * User enters a name and can view the friends of that Person.
	 */
	private void checkFriends() {
		System.out.println("Enter the name of the person you want to see the friends of");
		String personName = scan.nextLine();

		// gets the Person object tied to that name
		Person personObj = table.search(personName);

		// if null, then the person does not exist --> either you entered an invalid
		// name or my
		// program bugged.
		if (personObj == null) {
			System.out.println("PERSON NOT FOUND, EXITING.");
			return;
		}

		// gets the friends list tied to that Person and prints it.
		FriendsList friends = personObj.getFriends();
		System.out.println("Friends of this person: " + friends.toString());

	}

	/**
	 * User enters two names. The first name Person A is the person who is deleting.
	 * The second name Person B is the person being deleted. Person A removes Person
	 * B from their friends list.
	 */
	private void removeFriend() {
		System.out.println("Enter the name of the SENDER of the remove request");
		String senderName = scan.nextLine();

		// gets the Person object tied to the name. If null --> Person doesn't exist,
		// exits.
		Person senderObj = table.search(senderName);
		if (senderObj == null) {
			System.out.println("PERSON NOT FOUND, EXITING.");
			return;
		}

		System.out.println("CURRENT FRIENDS OF PERSON: " + senderObj.getFriends().toString());
		System.out.println();
		System.out.println("Enter the name of the RECIEVER of the remove request");

		// same process as before, but for Person B.
		String recieverName = scan.nextLine();
		Person recieverObj = table.search(recieverName);
		if (recieverObj == null) {
			System.out.println("PERSON NOT FOUND, EXITING.");
			return;
		}

		senderObj.removeFriend(recieverName);
		System.out.println("CURRENT FRIENDS OF PERSON: " + senderObj.getFriends().toString());

	}

	/**
	 * User enters two names. The first name Person A is the person who is adding to
	 * their friends list. The second name person B is the person who is being added
	 * by person A. Person A adds person B to their friends list.
	 */
	public void addFriend() {
		System.out.println("Enter the name of the SENDER of the friend request");
		String senderName = scan.nextLine();

		// gets the Person object tied to the name. if null --> you likely entered an
		// invalid name --> exit.
		Person senderObj = table.search(senderName);
		if (senderObj == null) {
			System.out.println("PERSON NOT FOUND, EXITING.");
			return;
		}

		FriendsList oldList = senderObj.getFriends();
		System.out.println();
		System.out.println("CURRENT FRIENDS OF PERSON: " + oldList.toString());
		System.out.println();

		System.out.println("Enter the name of the RECIEVER of the friend request");
		String recieverName = scan.nextLine();

		// same process, but for person B.
		Person recieverObj = table.search(recieverName);
		if (recieverObj == null) {
			System.out.println("PERSON NOT FOUND, EXITING.");
			return;
		}

		senderObj.addFriend(recieverObj);
	}

	/**
	 * User makes a new account by supplying a name. Used by 'new' command.
	 */
	public void create() {
		System.out.println("enter the name of the person you'd like to create");
		String name = scan.nextLine();
		Person person = new Person(name);
		table.add(person);
	}

	/**
	 * used by !commands to show user what they can do. 
	 */
	public void printCommands() {

		System.out.println();
		System.out.println("'print' - Prints all the items in the table.");
		System.out.println("'new' - Creates a person with the supplied name.");
		System.out.println("'add' - The FIRST name you enter (SENDER) is the name of the person whose friends list "
				+ "you want to add to, the SECOND name you enter (RECIEVER) is the person who is added "
				+ "to the first person's friends list.");
		System.out.println(
				"'del' - The FIRST name you enter is the name of the person whose friends list you are removing from, "
						+ "the SECOND name you enter is the name you want to remove from the list.");
		System.out.println("'check' - Enter the name of a person to view their friends.");
		System.out.println("'checktwo' - Prints 'YES' if person A has person B added AND "
				+ "person B has person A added. Else prints 'NO'.");
		System.out.println();

	}

	/**
	 * initializes the array with the words below.
	 */
	public void fillArray() {
		String text = "library," + "historical," + "unused," + "bell," + "fear," + "pet," + "rhythm," + "violent,"
				+ "useless," + "harbor," + "ruin," + "onerous," + "wholesale," + "puffy," + "satisfy," + "greet,"
				+ "easy," + "sofa," + "park," + "year," + "hill," + "plantation," + "faithful," + "observation,"
				+ "wilderness," + "push," + "steadfast," + "weary," + "peel," + "knock," + "worthless," + "love,"
				+ "last," + "zany," + "young," + "check," + "dress," + "legal," + "gorgeous," + "flavor," + "shock,"
				+ "blushing," + "incompetent," + "rustic," + "zippy," + "teaching," + "nerve," + "back," + "improve,";
		// + "gabby," + "fluttering," + "visit," + "poor," + "comfortable," + "action,"
		// + "accept," + "obeisant,"
		// + "pass," + "stone," + "great," + "poke," + "island," + "squeal," +
		// "volcano," + "tow," + "glove,"
		// + "record," + "dream," + "delightful," + "frogs," + "giddy," + "agreeable," +
		// "appliance," + "bad,"
		// + "cobweb," + "peace," + "educate," + "seal," + "handy," + "government," +
		// "meeting," + "familiar,"
		// + "observant," + "van," + "halting," + "fancy," + "left," + "rule," +
		// "jelly," + "shop," + "hat,"
		// + "glass," + "worm," + "evanescent," + "empty," + "acoustics," + "floor," +
		// "toys," + "oven,"
		// + "board,";
		String[] words = text.split(",");
		for (String x : words) {
			table.add(new Person(x));

		}
		System.out.println("Number of words " + words.length);
	}

}
