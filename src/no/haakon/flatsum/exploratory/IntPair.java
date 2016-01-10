package no.haakon.flatsum.exploratory;

import java.util.List;

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
	
	@Override
	public String toString() {
		return String.format("IntPair<%d, %d>", x, y);
	}
}
