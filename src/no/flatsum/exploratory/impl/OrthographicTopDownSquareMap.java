package no.flatsum.exploratory.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;
import no.flatsum.exploratory.inter.GameMap;
import no.flatsum.exploratory.inter.Tile;

/**
 * Orthographic: Items do not get smaller as they get farther away.
 * Top-down: The view is from the top down, as opposed to isometric.
 * Square: The tiles are square.
 *
 * @author haakon
 */
public class OrthographicTopDownSquareMap implements GameMap {
	private final Tile[][] tiles;
	private int tileWidth;
	private int tileHeight;
	
	public OrthographicTopDownSquareMap(Tile[][] tiles, int tileWidth, int tileHeight) {
		this.tiles = tiles;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	@Override
	public BufferedImage getGraphics() {
		BufferedImage map = new BufferedImage(tiles.length * tileWidth, tiles[0].length * tileHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D gfx = map.createGraphics();
		for(Tile[] row : tiles) {
			for(Tile field : row) {
				IntPair location = field.pixelLocation();
				gfx.drawImage(field.getGraphics(), location.x, location.y, null);
			}
		}
		
		return map;
	}

	@Override
	public IntPair startAt() {
		return IntPair.zeroedIntPair();
	}
	
}
