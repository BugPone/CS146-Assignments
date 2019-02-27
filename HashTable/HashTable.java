package HashTable;

/**
 * The hash table that represents facebook. This is where all the user data is
 * stored.
 */
public class HashTable {
	public static final int TABLE_SIZE = 10;
	TableList[] listArray = new TableList[TABLE_SIZE];

	/**
	 * We have an array with TABLE_SIZE slots. All the slots get filled with
	 * TableList objects (linked lists)
	 */
	public HashTable() {
		for (int i = 0; i < TABLE_SIZE; i++) {
			listArray[i] = new TableList();
		}
	}

	/**
	 * searches for a Person based off of their name
	 * 
	 * @param name
	 *            - person you want to serach for
	 * @return null if not found, the person if found.
	 */
	public Person search(String name) {
		System.out.println("searching...");

		// gets the hashcode (slot / index) for the name.
		int code = Person.stringHashCode(name);

		// gets the linkedlist inside the hashtable based off of the code (slot /
		// index), because
		// that slot
		// is where the person should be found.
		TableList list = listArray[code];

		// searches the linkedlist found in that slot for the person. If null, person
		// doesn't exist in the linkedlist --> return null to be caught by
		// Program.
		Person found = list.Search(name);
		if (found == null) {
			System.out.println("person not found");
			return null;
		} else {
			return found;
		}
	}

	/**
	 * Adds a Person to the friends list.
	 * 
	 * @param Person
	 *            you want to add.
	 */
	public void add(Person person) {
		// gets hash code for the person you want to add.
		int code = person.myHashCode();

		// gets the list that that person belongs in.
		TableList list = listArray[code];

		// inserts that person into said list.
		list.insert(person);
	}

	/**
	 * For debugging and display, prints all the items in the table.
	 */
	public void print() {
		int i = 0;
		for (TableList list : listArray) {
			System.out.println(i + ": " + list.toString());
			i++;
		}
	}
}
