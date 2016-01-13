package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Town implements Location {
	
	private final IntPair pixelCoordinates;
	private final IntPair tileCoordinates;
	private final BufferedImage sprite;

	public Town(IntPair pixelCoordinates, IntPair tileCoordinates, BufferedImage sprite) {
		this.pixelCoordinates = pixelCoordinates;
		this.tileCoordinates = tileCoordinates;
		this.sprite = sprite;
	}
	
	@Override
	public void onAction() {
		System.out.println("On action does nothing");
	}

	@Override
	public IntPair getCoordinates() {
		return tileCoordinates;
	}

	@Override
	public int getXPosition() {
		return tileCoordinates.x;
	}

	@Override
	public int getYPosition() {
		return tileCoordinates.y;
	}

	@Override
	public void paint(Graphics gfx) {
		gfx.drawImage(sprite, pixelCoordinates.x, pixelCoordinates.y, null);
	}

}
