package no.flatsum.exploratory.level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

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

	/**
	 * This is not a performant implementation.
	 */
	@Override
	public BufferedImage subsection(int x, int y, int width, int height) {
		BufferedImage all = getGraphics();
		int maxX = all.getWidth() - width;
		int maxY = all.getHeight() - height;
		
		int left = Math.min(Math.max(x - (width / 2), 0), maxX);
		int top = Math.min(Math.max(y - (height / 2), 0), maxY);
		
		BufferedImage out;
		try {

			out =  all.getSubimage(left, top, width, height);
		}
		catch (RasterFormatException e) {
			System.err.printf("Error in call, x=%d, y=%d, width=%d, height=%d, left=%d, top=%d%n", x, y, width, height, left, top);
			throw e;
		}
		return out;
	}

	@Override
	public int widthInPixels() {
		return tiles.length * tileWidth;
	}

	@Override
	public int heightInPixels() {
		if(tiles.length > 0) {
			return tiles[0].length * tileHeight;
		}
		else {
			return 0;
		}
	}
	
}
