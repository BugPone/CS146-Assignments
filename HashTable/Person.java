package HashTable;

import java.util.ArrayList;

/**
 * A Person represents a person with a name, who is added to the the hash table
 * / friends list.
 */
public class Person implements Cloneable {
	private String name = "";

	// Person pointers for the hash table lists.
	public Person next;
	public Person previous;

	// Person pointers for the friends list
	public Person nextFriend;
	public Person previousFriend;

	FriendsList friends = new FriendsList();

	public Person(String name) {
		this.name = name;
	}

	/**
	 * Add a friend to this person's friends list
	 * 
	 * @param other
	 *            - the person you want to add to this person's friends list.
	 */
	public void addFriend(Person other) {
		System.out.println("Friends before: " + friends.toString());
		// it needs to be clone() so that one person can be a friend of several people.
		friends.insert(other.clone());
		System.out.println("Friends after: " + friends.toString());
	}

	/**
	 * Delete a friend
	 * 
	 * @param name
	 *            - the name of the friend you want to delete.
	 */
	public void removeFriend(String name) {
		friends.delete(name);
	}

	/**
	 * This is the hashcode function. It was taken from page 263 of the book. For
	 * any given string, lets say the string is "pie", it will convert all the
	 * letters to ascii. It will multiply all except the last letter by 128. Then,
	 * it will add the sum of all the asciis.
	 * 
	 * Let N = the number of letters in a string (Letter1*128 + Letter2*128 + ... +
	 * + LetterN-1*128 + LetterN) mod (HashTable.TABLE_SIZE) [division method].
	 * 
	 * @param name
	 *            - The string you want the hashcode for
	 * @return The hashcode for that String
	 */
	public static int stringHashCode(String name) {
		// holds all the ascii numbers
		ArrayList<Integer> numbers = new ArrayList<>();

		// splits the String into individial characters
		char[] chars = name.toCharArray();

		if (name.length() == 0) { // Just in case
			System.out.println("name's length is 0. You probably broke the program.");
		} else if (name.length() <= 1) { // Just in case the next for loop gives an index OOB error.
			// Since size <= 1, the input is only one letter. Just return the ascii of that
			// one letter
			int ascii = (int) name.charAt(0);
			numbers.add(ascii);
			System.out.print(ascii);
		} else {
			// else the String is more than 1 letter long.
			System.out.print(name + " -> ");

			// for all the letters except the last...
			for (int i = 0; i < chars.length - 1; i++) {
				char character = chars[i];
				System.out.print(character);
				// convert the letter into ascii.
				int ascii = (int) (character);
				System.out.print(ascii + ", ");
				int mult = ascii * 128;

				// add it to the arraylist so it can be summed.
				numbers.add(mult);

			}
			System.out.print(chars[chars.length - 1] + "" + (int) chars[chars.length - 1] + " -> *128 = -> ");
			// adds the last letter of the string to the list of numbers to be summed. This
			// last letter is not multiplied by 128.
			numbers.add((int) chars[chars.length - 1]);

		}
		System.out.print(numbers + " -> ");
		int sum = 0;
		// sums all the numbers that were converted to ascii.
		for (int x : numbers) {
			sum = sum + x;
		}
		// mods the sum by the table size (k mod m).
		int slot = sum % HashTable.TABLE_SIZE;
		System.out.println(sum + " % " + HashTable.TABLE_SIZE + " -> " + slot);
		return slot;
	}

	// so I don't break previously written code after I made the new hash function.
	public int myHashCode() {
		return stringHashCode(name);
	}

	public boolean equals(Person other) {
		return other.name.equals(this.name);
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public FriendsList getFriends() {
		return friends;
	}

	@Override
	public Person clone() {
		return new Person(name);

	}

}
