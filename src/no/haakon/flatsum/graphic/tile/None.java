package no.haakon.flatsum.graphic.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public enum None implements Tile {
	NOTILE;

	@Override
	public void paint(Graphics graphics) {
		return;
	}

	@Override
	public int getXCoordinate() {
		return -1;
	}

	@Override
	public int getYCoordinate() {
		return -1;
	}

	@Override
	public int getTopPixelPosition() {
		return -1;
	}

	@Override
	public int getLeftmostPixelPosition() {
		return -1;
	}

	@Override
	public int getPixelWidth() {
		return -1;
	}

	@Override
	public int getPixelHeight() {
		return -1;
	}

	@Override
	public BufferedImage[] getImage() {
		return new BufferedImage[0];
	}

}
