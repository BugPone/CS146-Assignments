package QuickSortTree;

//the node that gets manipulted by the tree. This comment keeps deleting itself.
public class UrlNode {
	Url url;
	UrlNode left;
	UrlNode right;
	UrlNode parent;

	public UrlNode(Url url) {
		this.url = url;
	}

	public int getScore() {
		return url.getScore();
	}

	public void printInfo() {
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
