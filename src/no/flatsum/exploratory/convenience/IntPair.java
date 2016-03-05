package no.flatsum.exploratory.convenience;

public final class IntPair {
	public int x;
	public int y;
	
	public IntPair() {
		x = 0;
		y = 0;
	}
	
	public IntPair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public IntPair(IntPair copyMe) {
		this.x = copyMe.x;
		this.y = copyMe.y;		
	}
	
	@Override
	public String toString() {
		return String.format("IntPair<%d, %d>", x, y);
	}
	
	public static IntPair zeroedIntPair() {
		return new IntPair(0, 0);
	}
}
