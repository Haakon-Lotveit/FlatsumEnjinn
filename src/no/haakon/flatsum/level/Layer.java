package no.haakon.flatsum.level;

import java.awt.Graphics;

import no.haakon.flatsum.graphic.tile.Tile;

/**
 * This describes the basic idea behind a layered level
 * @author Haakon
 *
 * A level consists amongst other things, several layers.
 * Layers are collections of tiles that are drawn to make up the graphical background. Layers with higher numbers are drawn first.
 * An example of a tile on a level would be: A soldier (not part of a layer) standing on top of a road, going through a plain.
 * For that you would use two layers, one to give the basic terrain type (a plain) and one to show the structures on top of the terrain (a road).
 * 
 *  Note that all interfaces are in flux, and things are subject to change.
 */
public interface Layer {
	
	/**
	 * The width of the layer is measured in how many tiles it is across. The layer does not know anything about pixels.
	 * @return the width of the layer
	 */
	public int getWidth();
	
	/**
	 * The height of the layer is measured in how many tiles tall it is. The layer does not know anything about pixels.
	 * @return the height of the layer
	 */	
	public int getHeight();
	
	/**
	 * Returns the tile in the given position. Not every position must have a tile, and a null-value may be returned.
	 * This will in the future be replaced with some sort of NoTile object, but for now, it will be a null
	 * If you specify a tile that does not exist, then an IllegalArgumentException is thrown.
	 * Note also that the tile returned is the actual tile, so and mutation of state will be reflected later.
	 * @param x the x-position. 0 is leftmost.
	 * @param y the y-position. 0 is topmost.
	 * @throws IllegalArgumentException if an illegal position is given
	 * @return the correct Tile.
	 */
	public Tile getTile(int x, int y);

	/**
	 * Sets the tile at the given location. 
	 * @param t the Tile to be set.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @throws IllegalArgumentException if the coordinates are out of bounds
	 */
	public void setTile(Tile t, int x, int y);
	
	/**
	 * Gives the order to paint the layer. The tiles are painted in whatever order the implementing class decides, and all will be painted before it's over.
	 * This will paint the whole layer.
	 * @param gfx the Graphics that will be used to paint. Can not be null.
	 */
	public void paint(Graphics gfx);
	
	/**
	 * This method is not thoroughly defined yet, so massive changes may be incoming.
	 * @param gfx
	 * @param topmost
	 * @param leftmost
	 * @param rightmost
	 * @param bottommost
	 */
	public void paintSubset(Graphics gfx, int topmost, int leftmost, int rightmost, int bottommost);
}