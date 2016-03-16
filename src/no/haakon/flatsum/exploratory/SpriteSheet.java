package no.haakon.flatsum.exploratory;

import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;

public class SpriteSheet {
	private final int columns, rows, spriteWidth, spriteHeight;
	private final BufferedImage spriteSheet;
	
	public SpriteSheet(BufferedImage spriteSheet, int spriteWidth, int spriteHeight) {		
		if(spriteSheet.getHeight() % spriteHeight != 0) {
			String msg = "wrong sized height make better error msg";
			throw new IllegalArgumentException(msg);
		}
		if(spriteSheet.getWidth() % spriteWidth != 0) {
			String msg = "wrong sized width make better error msg";
			throw new IllegalArgumentException(msg);
		}
		
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.spriteSheet = spriteSheet;
		this.rows = spriteSheet.getHeight() / spriteHeight;
		this.columns = spriteSheet.getWidth() / spriteWidth;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getRows() {
		return rows;
	}
	
	public BufferedImage getSprite(int xCoord, int yCoord) {
		return spriteSheet.getSubimage(xCoord * spriteWidth, yCoord * spriteHeight, spriteWidth, spriteHeight);
	}
	
	public BufferedImage getSprite(IntPair coordinates) {
		return getSprite(coordinates.x, coordinates.y);
	}
	
	public BufferedImage getSubImage(int topCoordinate, int leftmostCoordinate, int width, int height) {
		return spriteSheet.getSubimage(topCoordinate, leftmostCoordinate, width, height);
	}
	
}
