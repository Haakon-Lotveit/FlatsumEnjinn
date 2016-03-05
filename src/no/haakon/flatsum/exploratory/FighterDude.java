package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;


public class FighterDude implements Unit {
	private volatile CompassDirection facing;
	SpriteSheet sprites;
	BufferedImage currentSprite;
	IntPair tileLocation;
	IntPair pixelLocation;
	int tileWidth;
	int tileHeight;

	public FighterDude(SpriteSheet sprites, IntPair location, int tileHeight, int tileWidth) {
		facing = CompassDirection.SOUTH;
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		this.tileLocation = location;
		updatePixelLocation();
		this.sprites = sprites;
		updateSprite();
	}
	private void updatePixelLocation() {
		this.pixelLocation = new IntPair(tileLocation.x * tileWidth, tileLocation.y * tileHeight);
	}
	@Override
	public CompassDirection getDirection() {
		return facing;
	}

	@Override
	public void turnLeft() {
		setDirection(facing.turnLeft());
	}

	@Override
	public void turnRight() {
		setDirection(facing.turnRight());	
	}

	@Override
	public void turnAround() {
		setDirection(facing.turnAround());
	}

	@Override
	public void setDirection(CompassDirection direction) {
		facing = direction;
		updateSprite(); 
	}

	private void updateSprite() {
		switch(facing) {
		case NORTH:
			currentSprite = sprites.getSprite(0, 0);
			break;
		case WEST:
			currentSprite = sprites.getSprite(0, 4);
			break;
		case EAST:
			currentSprite = sprites.getSprite(0, 6);
			break;
		case SOUTH:
			currentSprite = sprites.getSprite(0, 2);
			break;
		default:
			throw new IllegalStateException(String.format("Facing %s is not recognized", facing.toString()));
		}
	}
	@Override
	public void paint(Graphics gfx) {		
		gfx.drawImage(currentSprite, pixelLocation.x, pixelLocation.y, null);
	}

	@Override
	public IntPair getTileLocation() {
		return tileLocation;
	}

	@Override
	public IntPair getPixelLocation() {
		return pixelLocation;
	}

	int index = 0;
	int[] xDiffs = { 0, 1,  0, -1};
	int[] yDiffs = { 1, 0, -1,  0};
	@Override
	public void tick() {
		tileLocation.x += xDiffs[index];
		tileLocation.y += yDiffs[index];
		System.out.println(tileLocation);
		setTileLocation(tileLocation);
		++index;
		if(index >= xDiffs.length) { index = 0; }
	}


}
