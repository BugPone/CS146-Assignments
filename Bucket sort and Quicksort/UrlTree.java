package QuickSortTree;

public class UrlTree {
	UrlNode root = null;

	// adds a node to the tree
	public void insert(UrlNode z) {
		UrlNode y = null;
		UrlNode x = root;
		while (x != null) {
			y = x;
			if (z.getScore() < x.getScore()) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;
		if (y == null) {
			this.root = z;
		}

		else if (z.getScore() < y.getScore()) {
			y.left = z;
		} else {
			y.right = z;
		}

	}

	// removes a node based off of a its SCORE
	public UrlNode delete(int del) {
		UrlNode z = search(root, del);
		if (z == null) {
			System.out.println("NODE NOT FOUND");
			return null;
		}
		UrlNode y = null;
		UrlNode x = null;
		if (z.left == null || z.right == null) {
			y = z;
		}

		else {
			y = successor(z);
		}

		if (y.left != null) {
			x = y.left;
		}

		else {
			x = y.right;
		}

		if (x != null) {
			x.parent = y.parent;
		}

		if (y.parent == null) {
			root = x;
		}

		else if (y == y.parent.left) {
			y.parent.left = x;
		}

		else {
			y.parent.right = x;
		}

		// z.key = y.key
		if (y != z) {
			Url key = y.getUrl();
			z.setUrl(key);
			;
		}
		return y;
	}

	/**
	 * IF YOU USE THIS METHOD make a statement so that if node if the node you
	 * reference found to is null, then just print, return, do nothing.
	 * 
	 * @param x
	 *            starting node (root is good)
	 * @param score
	 *            score of node found
	 * @return a REFERENCE to the node we found
	 */
	public UrlNode search(UrlNode x, int score) {
		if (x == null || score == x.getScore()) {
			// Demeter --> returns reference to x?
			return x;
		}

		if (score < x.getScore()) {
			return search(x.left, score);
		} else {
			return search(x.right, score);
		}

	}

	/**
	 * uses the method searchByNode instead because we want to delete by name
	 * because two nodes can have the same score
	 */
	public UrlNode deleteNodeByName(UrlNode node) {
		UrlNode z = searchByNode(root, node);
		if (z == null) {
			System.out.println("NODE NOT FOUND");
			return null;
		}
		UrlNode y = null;
		UrlNode x = null;
		if (z.left == null || z.right == null) {
			y = z;
		}

		else {
			y = successor(z);
		}

		if (y.left != null) {
			x = y.left;
		}

		else {
			x = y.right;
		}

		if (x != null) {
			x.parent = y.parent;
		}

		if (y.parent == null) {
			root = x;
		}

		else if (y == y.parent.left) {
			y.parent.left = x;
		}

		else {
			y.parent.right = x;
		}

		// z.key = y.key
		if (y != z) {
			Url key = y.getUrl();
			z.setUrl(key);
			;
		}
		return y;
	}

	/**
	 * Searches for a node based off of it's name, used by the deleter
	 * 
	 */
	public UrlNode searchByNode(UrlNode x, UrlNode search) {
		if (x == null || search.getUrl().getName().equals(x.getUrl().getName())) {
			// Demeter --> returns reference to x?
			return x;
		}

		if (search.getScore() < x.getScore()) {
			return searchByNode(x.left, search);
		} else {
			return searchByNode(x.right, search);
		}

	}

	// prints the items from the tree in sorted order
	public void inOrderPrint(UrlNode x) {
		if (x != null) {
			inOrderPrint(x.right);
			x.printInfo();
			inOrderPrint(x.left);
		}

	}

	/**
	 * finds the lowest largest value of UrlNode x
	 */
	public UrlNode successor(UrlNode x) {
		// has right subtree
		if (x.right != null) {
			return minimum(x.right);
		}
		UrlNode y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	public UrlNode minimum(UrlNode x) {
		while (x.left != null) {
			x = x.left;
		}
		return x;
	}

	public UrlNode maximum(UrlNode x) {
		while (x.right != null) {
			x = x.right;
		}
		return x;
	}

	public UrlNode getRoot() {
		return root;
	}

}
