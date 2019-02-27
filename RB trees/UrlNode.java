package v5_add_comments_pretty_up;

//the node that gets manipulted by the tree. This comment keeps deleting itself.
public class UrlNode {
	Url url;
	String color;
	UrlNode left;
	UrlNode right;
	UrlNode parent;

	int score; // NOT THE ACTUAL SCORE, JUST MAKES DRAWING WITH DEBUGGER EASIER

	public UrlNode(Url url) {
		this.url = url;
	}

	//used for debugging
	public UrlNode(int score) {
		url = new Url("dummy", score);
	}
	
	//used for debugging
	public UrlNode(int score, String name) {
		url = new Url(name, score);
	}

	public UrlNode(Url url, String color) {
		this.color = color;
		this.url = url;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void refreshScore() {
		score = url.getScore();
	}

	// public void setRed() {
	// this.color = "red";
	// }
	//
	// public void setBlack() {
	// this.color = "black";
	// }
	//
	// public boolean isBlack() {
	// if (color.equals("black")) {
	// return true;
	// }
	// return false;
	// }
	//
	// public boolean isRed() {
	// if (color.equals("red")) {
	// return true;
	// }
	// return false;
	// }

	public String getColor() {
		return color;
	}

	public int getScore() {
		return url.getScore();
	}

	public void printInfo() {
		System.out.println("Color: " + this.color);
		url.printAllInfo();
	}

	public void setScore(int x) {
		url.setScore(x);
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}
}
