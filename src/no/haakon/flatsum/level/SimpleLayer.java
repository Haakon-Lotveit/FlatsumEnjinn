package no.haakon.flatsum.level;

import java.awt.Graphics;
import java.util.Arrays;

import no.haakon.flatsum.graphic.tile.None;
import no.haakon.flatsum.graphic.tile.Tile;

public class SimpleLayer implements Layer {
	Tile[][] tiles;
	int height;
	int width;
	public SimpleLayer(int width, int height) {
		if(width <= 0) {
			throw new IllegalArgumentException(String.format("Width of a layer cannot be 0 or lower, was %d", width));
		}
		
		if(width <= 0) {
			throw new IllegalArgumentException(String.format("Height of a layer cannot be 0 or lower, was %d", height));
		}
		
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
		for(Tile[] row : tiles) {
			Arrays.fill(row, None.NOTILE);
		}
	}
	
	@Override
	public int getWidth() {
		return tiles.length;
	}

	@Override
	public int getHeight() {
		return tiles[0].length;
	}

	@Override
	public Tile getTile(int x, int y) {
		Tile out = tiles[x][y];
		return out;
	}

	@Override
	public void paint(Graphics gfx) {
	}

	@Override
	public void setTile(Tile t, int x, int y) {
		tiles[x][y] = t;
	}

	@Override //TODO: Implement this somewhere! :o
	public void paintSubset(
			Graphics gfx, 
			int topmost, int leftmost,
			int rightmost, int bottommost) {
		for(int x = topmost; x <= bottommost; ++x) {
			for(int y = leftmost; y <= rightmost; ++y) {
				getTile(x,y).paint(gfx); // TODO: this will obviously not work when we try. The tiles don't know where to start painting themselves, and the layer doesn't know enough to tell them.
			}
		}
	}
}
