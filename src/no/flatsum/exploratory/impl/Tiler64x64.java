package no.flatsum.exploratory.impl;

import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;
import no.flatsum.exploratory.inter.Tile;

public class Tiler64x64 {
	private int row;
	private int column;
	private BufferedImage sprite;
	private int numLayers;
	
	/**
	 * Builds {@link Tile} objects for us, sized 64x64 pixels It's currently building {@link LayeredTile} objects.
	 * 
	 * @param row The row that it will tell the tile it's on.
	 * @param column The column that it will tell the tile it's on.
	 * @param sprite The sprite it will be given as its first layer.
	 * @param numLayers The total amount of layers supported.
	 */
	public Tiler64x64(int row, int column, BufferedImage sprite, int numLayers) {
		this.row = row;
		this.column = column;
		this.sprite = sprite;
		this.numLayers = numLayers;
	}
		
	/**
	 * Builds {@link Tile} objects for us, sized 64x64 pixels It's currently building {@link LayeredTile} objects.
	 * This constructor will build Tile objects with only one (1) layer, but you can set it later.
	 * 
	 * @param row The row that it will tell the tile it's on.
	 * @param column The column that it will tell the tile it's on.
	 * @param sprite The sprite it will be given as its first layer.
	 */
	public Tiler64x64(int row, int column, BufferedImage sprite) {
		this(row, column, sprite, 1);
	}

	public Tiler64x64 setNumLayers(int numLayers) {
		this.numLayers = numLayers;
		return this;
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
	
	public LayeredTile tile() {
		LayeredTile tile = new LayeredTile(numLayers, 64, 64, new IntPair(column, row));
		tile.setLayer(0, sprite);
		return tile;
	}
	
	
	
	
}
