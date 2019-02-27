package v5_add_comments_pretty_up;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;

//A tree for UrlNode objects.
public class UrlTree {
	Url dummy = new Url("NIL", -1);
	UrlNode nil = new UrlNode(dummy, "BLACK");

	// When the tree initiates, we need to tie whatever root node we have
	// to the NIL node so that we can still maintain the connection to the
	// nil node through new adds and deletes.
	UrlNode root = nil;

	public UrlTree() {
		// I don't think this is needed but I've already tested my code
		// and am too scared to remove this. I think I originally
		// added it while wondering what was causing a nullpointerexception
		root.left = nil;
		root.right = nil;
		root.parent = nil;
	}

	// rb insert
	public void insert(UrlNode z) {
		// y is parent
		UrlNode y = nil;

		UrlNode x = root;
		while (x != nil) {
			y = x;
			if (z.getScore() < x.getScore()) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;

		if (y == nil) {
			// tree was empty
			this.root = z;
		}

		else if (z.getScore() < y.getScore()) {
			y.left = z;
		} else {
			y.right = z;
		}

		// lead node l/r points to nil
		z.left = nil;
		z.right = nil;
		z.setColor("RED");
		// system.out.println("calling insert fixup on...");
		// z.printInfo();
		insertFixup(z);
	}

	public void insertFixup(UrlNode z) {
		while (z.parent.color.equals("RED")) {
			// system.out.println("in while loop");
			if (z.parent == z.parent.parent.left) {
				UrlNode y = z.parent.parent.right;
				// case 1
				if (y.color.equals("RED")) {
					System.out.println("case 1");
					z.parent.setColor("BLACK");

					y.setColor("BLACK");

					z.parent.parent.setColor("RED");

					z = z.parent.parent;

				}

				// case 2
				// the book was not saying else if, it was saying else{if()}...
				else {
					if (z == z.parent.right) {
						System.out.println("case 2");
						z = z.parent;
						leftRotate(z);

					}
					// case 3
					System.out.println("case 3");
					z.parent.setColor("BLACK");
					z.parent.parent.setColor("RED");
					rightRotate(z.parent.parent);

				}

			} else {

				UrlNode y = z.parent.parent.left; //
				// case 1 if statement
				if (y.color.equals("RED")) {
					System.out.println("case 1b");
					z.parent.setColor("BLACK");
					y.setColor("BLACK");
					z.parent.parent.setColor("RED");
					z = z.parent.parent;

				}

				else {
					// case 2
					if (z == z.parent.left) { //
						System.out.println("case 2b");
						z = z.parent;
						rightRotate(z); //

					}
					System.out.println("case 3b");
					refreshNodeScores(root);
					// case 3
					z.parent.setColor("BLACK");
					z.parent.parent.setColor("RED");
					leftRotate(z.parent.parent); //
					refreshNodeScores(root);

				}
			}
		}
		root.setColor("BLACK");

	}

	public void deleteFixup(UrlNode x) {
		System.out.println("deletefixup on node...");
		 x.printInfo();
		while (x != root && x.color.equals("BLACK")) {
			// system.out.println("in while loop");
			if (x == x.parent.left) {
				// x is a left child
				UrlNode w = x.parent.right;
				// w is the sibling to the right
				// case 1 --> 2,3,4
				if (w.color.equals("RED")) {
					System.out.println("case 1 - sibling red");
					w.setColor("BLACK");
					x.parent.setColor("RED");
					leftRotate(x.parent);

				}

				w = x.parent.right; //////
				x.parent.right.printInfo();

				// both of sibling's children are black
				// case 2
				if (w.left.color.equals("BLACK") && w.right.color.equals("BLACK")) {
					System.out.println("case 2 - sibling and both children black");
					w.setColor("RED");
					x = x.parent;

				}

				// case 3, w is black, left red, right black
				else {
					if (w.right.color.equals("BLACK")) {
						System.out.println("case 3 - sibling black, left red right black");
						w.left.setColor("BLACK");
						w.setColor("RED");
						rightRotate(w);

					}
					// case 4
					System.out.println("case 4 - right red");
					w = x.parent.right;
					w.setColor(x.parent.getColor());
					x.parent.setColor("BLACK");
					w.right.setColor("BLACK");
					leftRotate(x.parent);
					x = root;

				}
			}
			////////////

			else {
				// x is a RIGHT child
				UrlNode w = x.parent.left; //
				// w is the sibling to the left
				// case 1 --> 2,3,4
				if (w.color.equals("RED")) {
					System.out.println("case 1b");
					w.setColor("BLACK");
					x.parent.setColor("RED");
					rightRotate(x.parent); //

				}

				w = x.parent.left; //

				// both of sibling's children are black
				// case 2
				if (w.right.color.equals("BLACK") && w.left.color.equals("BLACK")) {////
					System.out.println("case 2b");
					w.setColor("RED");
					x = x.parent;

				}

				// case 3, w is black, left red, right black
				else {
					if (w.left.color.equals("BLACK")) { //
						
						w.right.setColor("BLACK"); //
						w.setColor("RED");

						leftRotate(w); //
						System.out.println("case 3bb");
					}
					// case 4
					System.out.println("case 4b");
					w = x.parent.left; //
					w.setColor(x.parent.getColor());
					x.parent.setColor("BLACK");
					w.left.setColor("BLACK"); //
					rightRotate(x.parent); //
					x = root;
				}
			}

		}
		x.setColor("BLACK");
	}

	public void leftRotate(UrlNode x) {
		UrlNode y = x.right; // let y = x's right
		x.right = y.left; // X's right points to b
		if (y.left != nil) { // if y's left. aka b, is not a leaf
			y.left.parent = x; // set b's parent to x
			// x and b are now a full relationship
			// y can be freed of left, see part A.
		}
		// see part A
		y.parent = x.parent;

		// if x is root.
		if (x.parent == nil) {
			root = y;
		}

		// x is a left child
		else if (x == x.parent.left) {
			x.parent.left = y;
			// now y is the left child.
		}

		else {
			// x is the right child, now y is right child
			x.parent.right = y;
		}
		// part A
		// now x is Y's left child.
		y.left = x;

		// YEET
		x.parent = y;
	}

	// lets try to write right rotate
	public void rightRotate(UrlNode y) {
		UrlNode x = y.left; // set x
		y.left = x.right; // after right rotate, y's left will be x's current right
		if (x.right != nil) {
			x.right.parent = y;
			// if the node B...
			// make B into full relationship
		}

		x.parent = y.parent;

		// is y a root?
		if (y.parent == nil) {
			root = x;
		}
		// if y is left child
		else if (y == y.parent.left) {
			y.parent.left = x;
		}
		// else y is rightchild
		else {
			y.parent.right = x;
		}

		// connector between x and y
		x.right = y;
		y.parent = x;
	}

	// rb trnasplant
	public void transplant(UrlNode u, UrlNode v) {
		if (u.parent == nil) {
			root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	// rb delete
	public void delete(UrlNode z) {
		UrlNode y = z;
		UrlNode x;
		String y_origin_color = y.getColor();
		if (z.left == nil) {
			// system.out.println("no left child");
			x = z.right;
			transplant(z, z.right);
		} else if (z.right == nil) {
			// system.out.println("left but no right");
			x = z.left;
			transplant(z, z.left);
		}

		else {
			// system.out.println("two children");
			// z has 2 children
			y = minimum(z.right);
			y_origin_color = y.getColor();
			// system.out.println("New color - minimum color: " + y_origin_color);

			x = y.right;
			// system.out.println("x is minimum's right, which is: ");
			// x.printInfo();
			if (y.parent == z) {
				x.parent = y;
			} else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (y_origin_color.equals("BLACK")) {
			deleteFixup(x);
		} else if (y_origin_color.equals("RED")) {
			// system.out.println("original color red, no delete fixup");
		}

	}

	/**
	 * IF YOU USE THIS METHOD make a statement so that if node found is nil, then
	 * just print, return, do nothing.
	 * 
	 * @param x
	 *            starting node (root is good)
	 * @param score
	 *            score of node found
	 * @return a REFERENCE to the node we found
	 */
	public UrlNode search(UrlNode x, int score) {
		if (x == nil || score == x.getScore()) {
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
	 * Searches for a node based off of it's name, used by the deleter We want a
	 * reference to a certain node.
	 */
	public UrlNode searchByNode(UrlNode x, UrlNode search) {
		if (x == nil || search.getUrl().getName().equals(x.getUrl().getName())) {
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

	int i = 1;

	public void debugPrint() {
		i = 1;
		debugInOrder(root);
	}

	public void debugInOrder(UrlNode x) {
		if (x != nil) {
			debugInOrder(x.right);
			System.out.println("PageRank: " + i);
			System.out.println(x.url.score);
			System.out.println("index: " + x.url.rankBeforeSort);
			System.out.println(x.color);
			System.out.println();
			i++;
			debugInOrder(x.left);
		}

	}

	public void inOrderPrint(UrlNode x) {
		if (x != nil) {
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
		if (x.right != nil) {
			return minimum(x.right);
		}
		UrlNode y = x.parent;
		while (y != nil && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	public UrlNode minimum(UrlNode x) {
		while (x.left != nil) {
			x = x.left;
		}
		return x;
	}

	public UrlNode maximum(UrlNode x) {
		while (x.right != nil) {
			x = x.right;
		}
		return x;
	}

	public UrlNode getRoot() {
		return root;
	}

	// MAKES DRAWING WITH DEBUGGER EASIER
	// this is so that all nodes display their scores so
	// when i use debugger to draw so I don't have to
	// peek at the url and I can just draw from looking at the nodes.
	public void refreshNodeScores(UrlNode x) {
		if (x != nil) {
			refreshNodeScores(x.right);
			x.refreshScore();
			refreshNodeScores(x.left);
		}

	}

	// next 2 methods are used for getting an arraylist of all the nodes in the tree
	// usually used for debugging
	ArrayList<UrlNode> nodes = new ArrayList<>();

	public ArrayList<UrlNode> getNodes() {
		nodes = new ArrayList<>();
		addRecurse(root);
		return nodes;
	}

	public void addRecurse(UrlNode x) {
		if (x != nil) {
			addRecurse(x.right);
			nodes.add(x);
			addRecurse(x.left);
		}
	}

}
