package no.flatsum.exploratory.impl;

import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;

public class Tiler64x64 {
	private int row;
	private int column;
	private BufferedImage sprite;
	
	public Tiler64x64(int row, int column, BufferedImage sprite) {
		this.row = row;
		this.column = column;
		this.sprite = sprite;
	}

	public Tiler64x64 setRow(int row) {
		this.row = row;
		return this;
	}

	public Tiler64x64 setColumn(int column) {
		this.column = column;
		return this;
	}

	public Tiler64x64 setSprite(BufferedImage sprite) {
		this.sprite = sprite;
		return this;
	}
	
	public Static64x64Tile tile() {
		return new Static64x64Tile(sprite, new IntPair(column, row));
	}
	
	
	
	
}
