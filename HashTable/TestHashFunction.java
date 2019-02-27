package HashTable;

import java.util.ArrayList;

public class TestHashFunction {

	public static void main(String[] args) {
		String name = "pika";
		ArrayList<Integer> numbers = new ArrayList<>();
		char[] chars = name.toCharArray();

		if (name.length() == 0) {
			System.out.println("name's length is 0.");
		} else if (name.length() <= 1) {
			int ascii = (int) name.charAt(0);
			numbers.add(ascii);
			System.out.print(ascii);
		} else {
			System.out.print(name + " -> ");
			for (int i = 0; i < chars.length - 1; i++) {
				char character = chars[i];
				System.out.print(character);
				int ascii = (int) (character);
				System.out.print(ascii + ", ");
				int mult = ascii * 128;
				numbers.add(mult);

			}
			System.out.print(chars[chars.length - 1] + "" + (int) chars[chars.length - 1] + " -> *128 = -> ");
			numbers.add((int) chars[chars.length - 1]);

		}
		System.out.print(numbers + " -> ");
		int sum = 0;
		for (int x : numbers) {
			sum = sum + x;
		}
		int slot = sum % HashTable.TABLE_SIZE;
		System.out.println(sum + " % "+ HashTable.TABLE_SIZE + " -> " + slot);
	}

}
