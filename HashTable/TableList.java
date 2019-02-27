package HashTable;

/**
 * The list used by the HashTable class. Do not confuse with FriendsList class.
 *
 */
public class TableList {
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
			x = x.next;
		}
		return x;

	}

	/**
	 * inserts a Person object into an this table
	 */
	public void insert(Person x) {
		x.next = head;
		if (head != null) {
			head.previous = x;
		}
		head = x;
		x.previous = null;
	}

	// deletes a person from the Hash table. Not used.
	public void delete(Person x) {
		if (x.previous != null) {
			x.previous.next = x.next;
		} else {
			head = x.next;
		}
		if (x.next != null) {
			x.next.previous = x.previous;
		}
	}

	/**
	 * Returns a string with all the people in the list. Used for debugging and
	 * display.
	 */
	public String toString() {
		Person x = head;
		String main = "";
		while (x != null) {
			main = main + x.getName() + " -> ";
			x = x.next;
		}
		return main;
	}
}
