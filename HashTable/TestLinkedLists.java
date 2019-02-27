package HashTable;

public class TestLinkedLists {

	public static void main(String[] args) {
		TableList list = new TableList();
		Person bob = new Person("bob");
		Person jill = new Person("jill");
		Person kony = new Person("kony");
		Person zeus = new Person("zeus");

		list.insert(zeus);
		list.insert(kony);
		list.insert(jill);
		list.insert(bob);
		System.out.println();
		System.out.println(list.toString());
		System.out.println();
		Person foundBegin = list.Search("bob");

		Person foundMid = list.Search("jill");
		Person foundKony = list.Search("kony");

		Person foundEnd = list.Search("zeus");

		list.delete(foundBegin);

		System.out.println(list.toString());

		Person notFound = list.Search("renkdsm");
		if (notFound == null) {
			System.out.println("not found");
		} else {
			System.out.println(notFound);
		}

		// list.delete(foundBegin);
		// System.out.println(list.toString());

	}

}
