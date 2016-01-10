package no.haakon.flatsum.level;

import java.awt.Graphics;

import no.haakon.flatsum.graphic.tile.None;
import no.haakon.flatsum.graphic.tile.Tile;

public enum NoLayer implements Layer {
	NONE;

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public Tile getTile(int x, int y) {
		return None.NOTILE;
	}

	@Override
	public void setTile(Tile t, int x, int y) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void paint(Graphics gfx) {
		return;
	}

	@Override
	public void paintSubset(Graphics gfx, int topmost, int leftmost,
			int rightmost, int bottommost) {
		return;
	}

}
