package no.flatsum.exploratory.impl;

import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;
import no.flatsum.exploratory.inter.Tile;

public class Static64x64Tile implements Tile {
	
	private IntPair tileLocation;
	private BufferedImage graphic;

	public Static64x64Tile(BufferedImage graphic, IntPair tileLocation) {
		this.graphic = graphic;
		this.tileLocation = tileLocation;
	}

	@Override
	public IntPair tilePosition() {
		return new IntPair(tileLocation);
	}

	@Override
	public IntPair pixelOffset() {
		return IntPair.zeroedIntPair();
	}

	@Override
	public IntPair pixelLocation() {
		return new IntPair(tileLocation.x * 64, tileLocation.y * 64);
	}

	@Override
	public BufferedImage getGraphics() {
		return graphic;	
	}

	@Override
	public IntPair startAt() {
		return pixelLocation();
	}
	
	
}
