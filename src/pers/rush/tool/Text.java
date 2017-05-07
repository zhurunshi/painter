package pers.rush.tool;

public class Text {
	private String content;
	private int x;
	private int y;
	public Text(String content, int x, int y) {
		super();
		this.content = content;
		this.x = x;
		this.y = y;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	};
	
}
