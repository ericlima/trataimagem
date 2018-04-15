package trataImagens;

public class Ponto {

	private int x;
	private int y;
	
	public Ponto() {}
	
	public Ponto(int x, int y) {
		this.x = x;
		this.y = y;
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
	}
	
	public String toString() {
		return String.format("x = %1$d, y = %2$d", this.x, this.y);
	}
	
	
}
