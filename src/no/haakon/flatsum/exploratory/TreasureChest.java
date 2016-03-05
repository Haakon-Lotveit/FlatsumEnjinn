package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import no.flatsum.exploratory.impl.IntPair;

public class TreasureChest implements GameWorldObject {
	BufferedImage sprite;
	private IntPair tileLocation;
	private IntPair pixelLocation;
	private final int width;
	private final int height;

	public TreasureChest(BufferedImage sprite, IntPair tileLocation, int width, int height) {
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.tileLocation = tileLocation;
		updatePixelLocation();
	}
	
	private void updatePixelLocation() {
		this.pixelLocation = new IntPair(tileLocation.x * width, tileLocation.y * height);
	}
	
	@Override
	public CompassDirection getDirection() {
		return CompassDirection.SOUTH;
	}

	@Override
	public void turnLeft() {
		// Do nothing
	}

	@Override
	public void turnRight() {
		// Do nothing
	}

	@Override
	public void turnAround() {
		// Do nothing		
	}

	@Override
	public void setDirection(CompassDirection direction) {
		// Do nothing		
	}

	@Override
	public void paint(Graphics gfx) {
		gfx.drawImage(sprite, pixelLocation.x, pixelLocation.y, null);
	}
	
	@Override
	public IntPair getTileLocation() {
		return tileLocation;
	}
	
	@Override
	public IntPair getPixelLocation() {
		return pixelLocation;
	}
	
	@Override
	public void setTileLocation(IntPair location) {
		this.tileLocation = location;
		updatePixelLocation();
	}
	
}
