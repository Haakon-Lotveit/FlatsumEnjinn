package no.haakon.flatsum.graphic.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class is a placeholder for empty tiles;
 * @author Haakon
 *
 */
public class NoTile implements Tile {
    private int xCoordinate;
    private int yCoordinate;
    private int leftPixel;
    private int topPixel;
    private int height;
    private int width;
    
	public NoTile(int xCoordinate, int yCoordinate, int height, int width){
	    this.xCoordinate = xCoordinate;
	    this.yCoordinate = yCoordinate;
	    this.height = height;
	    this.width = width;
	    this.leftPixel = xCoordinate * width;
	    this.topPixel = yCoordinate * height;
	}

	@Override
	public void paint(Graphics graphics) {
		return;
	}

	@Override
	public int getXCoordinate() {
		return xCoordinate;
	}

	@Override
	public int getYCoordinate() {
		return yCoordinate;
	}

	@Override
	public int getTopPixelPosition() {
		return topPixel;
	}

	@Override
	public int getLeftmostPixelPosition() {
		return leftPixel;
	}

	@Override
	public int getPixelWidth() {
		return width;
	}

	@Override
	public int getPixelHeight() {
		return height;
	}

	@Override
	public BufferedImage[] getImage() {
	    BufferedImage[] images = new BufferedImage[0];
	    return images;
	}



}
