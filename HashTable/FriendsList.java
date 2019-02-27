package HashTable;

/**
 * Every Person object has a Friends list. It's for storing friends. NOT the
 * list used by HashTable. See TableList for that.
 *
 */
public class FriendsList {
	private Person head = null;

	/**
	 * Searches for a person based off of their name
	 * 
	 * @param name
	 *            - the name of the person you are searching for
	 * @return a reference to the person
	 */
	public Person Search(String name) {
		Person x = head;
		while (x != null && !(x.getName().equals(name))) {
			x = x.nextFriend;
		}
		return x;

	}

	/**
	 * Adds a person to this list
	 */
	public void insert(Person x) {
		x.nextFriend = head;
		if (head != null) {
			head.previousFriend = x;
		}
		head = x;
		x.previousFriend = null;
	}

	/**
	 * Finds a person based off of their name. Deletes them by using this liked
	 * list's search() function.
	 */
	public void delete(String name) {
		Person x = Search(name);
		if (x == null) {
			System.out.println("PERSON NOT FOUND IN FRIENDS LIST, RETURNING...");
			return;
		}
		if (x.previousFriend != null) {
			x.previousFriend.nextFriend = x.nextFriend;
		} else {
			head = x.nextFriend;
		}
		if (x.nextFriend != null) {
			x.nextFriend.previousFriend = x.previousFriend;
		}
	}

	/**
	 * gets everyone in the list as a string for debugging and display.
	 */
	public String toString() {
		Person x = head;
		String main = "";
		while (x != null) {
			main = main + x.getName() + " ";
			x = x.nextFriend;
		}
		return main;
	}
}
