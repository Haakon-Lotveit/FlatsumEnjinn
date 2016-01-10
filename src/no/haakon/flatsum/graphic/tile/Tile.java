package no.haakon.flatsum.graphic.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface Tile {
	/**
	 * Tells the Tile to paint itself using the Graphics object
	 * @param graphics the object that will be used for painting
	 */
	public void paint(Graphics graphics);
	
	/**
	 * Grabs the coordinate in the 2d map. The map is square, not hexagonal.
	 * @return X-coordinate of this tile.
	 */
	public int getXCoordinate();
	
	/**
	 * Grabs the coordinate in the 2d map. The map is square, not hexagonal.
	 * @return Y-coordinate of this tile.
	 */
	public int getYCoordinate();
	
	/**
	 * If the whole map was rendered as one image, with (0,0) being the top-left coordinate, this would be the Y-coordinate of the top left pixel in this tile.
	 * @return the y-coordinates of the topmost pixels.
	 */
	public int getTopPixelPosition();
	
	/**
	 * If the whole map was rendered as one image, with (0,0) being the top-left coordinate, this would be the X-coordinate of the top left pixel in this tile.
	 * @return the x-coordinates of the leftmost pixels.
	 */
	public int getLeftmostPixelPosition();
	
	/**
	 * This returns, in pixels, how wide the tile is.
	 * @return the width of the tile.
	 */
    public int getPixelWidth();
    
    /**
     * This returns, in pixels, how tall the tile is.
     * @return the height of the tile
     */
	public int getPixelHeight();
	
	/**
	 * This gets you the image[s] that is used by the tile.
	 * This is not a copy, so you can mutate it, which is not recommended. 
	 * @return the image[s] used by the tile.
	 */
	public BufferedImage[] getImage();
	
}
