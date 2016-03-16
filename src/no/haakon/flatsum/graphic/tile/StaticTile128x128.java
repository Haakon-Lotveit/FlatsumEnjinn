package no.haakon.flatsum.graphic.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;

public final class StaticTile128x128 implements Tile {
	private final int width = 128;
	private final int height = 128;
	private final BufferedImage image;
	private final int tileXPosition;
	private final int tileYPosition;
	private final int topmostPixelCoordinate;
	private final int leftmostPixelCoordinate;
	/**
	 * There are edge cases where you'd want to set pixel coordinates to be something else than what you'd expect from their tile positions, so that is allowed.
	 * This class is still in flux, and there will be changes. Do not assume that everything here or in the interface will remain unchanged.
	 * 
	 * There is also a builder you can use that will be easier to use, which is suggested to use instead.
	 * @param image a buffered image that is supposed to be used. Cannot be null. Must be 128x128.
	 * @param tileXPosition The X-position of this tile, where 0 is the most left you can go, and higher numbers go further to the right.
	 * @param tileYPosition The Y-position of this tile, where 0 is the top row, and higher numbers go further down.
	 */
	public StaticTile128x128(BufferedImage image, int tileXPosition, int tileYPosition) {
		if(null == image) {
			throw new IllegalArgumentException("Argument image cannot be null");
		}
		if(image.getHeight() != 128) {
			throw new IllegalArgumentException(String.format("image has to be 128 pixels tall, was %d", image.getHeight()));
		}
		if(image.getWidth() != 128) {
			throw new IllegalArgumentException(String.format("image has to be 128 pixels wide, was %d", image.getWidth()));
		}
		
		this.image = image;
		this.tileXPosition = tileXPosition;
		this.tileYPosition = tileYPosition;
		this.topmostPixelCoordinate = getYCoordinate() * getPixelHeight();
		this.leftmostPixelCoordinate = getXCoordinate() * getPixelWidth();
		

	}
	
	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(image, getLeftmostPixelPosition(), getTopPixelPosition(), width, height, null);
	}

	@Override
	public int getXCoordinate() {
		return tileXPosition;
	}

	@Override
	public int getYCoordinate() {
		return tileYPosition;
	}

	@Override
	public int getTopPixelPosition() {
		
		return topmostPixelCoordinate;
	}

	@Override
	public int getLeftmostPixelPosition() {
		
		return leftmostPixelCoordinate;
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
		return new BufferedImage[] {image};
	}

	@Override
	public String toString(){
		return String.format("StaticTile128x128{TopleftPixel:%d/%d Coordinates %d/%d}", getLeftmostPixelPosition(), getTopPixelPosition(), getXCoordinate(), getYCoordinate());
	}

	@Override
	public int hashCode() {
		int hash = 5419;
		hash *= leftmostPixelCoordinate * 31;
		hash *= topmostPixelCoordinate  * 31;				
		hash *= tileXPosition           * 31;
		hash *= tileYPosition           * 31;
		hash *= image.hashCode()        * 31;

		return hash;
	}

	public boolean equals(Object o) {
		if(o instanceof StaticTile128x128){
			StaticTile128x128 cand = (StaticTile128x128) o;
			return
					this.width == cand.width &&
					this.height == cand.width &&
					this.leftmostPixelCoordinate == cand.width &&
					this.topmostPixelCoordinate == cand.width &&
					this.tileXPosition == cand.width &&
					this.tileYPosition == cand.width &&
					this.image.equals(cand.image);
		}
		return false;
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private BufferedImage image = null;
		private int tileXPosition = 0;
		private int tileYPosition = 0;

		public Builder image(BufferedImage image)  {
			this.image = image;
			validate();
			return this;
		}

		public Builder tileXPosition(int tileXPosition)  {
			this.tileXPosition = tileXPosition;
			return this;
		}

		public Builder tileYPosition(int tileYPosition)  {
			this.tileYPosition = tileYPosition;
			return this;
		}

		private void validate() {
			boolean errors = false;
			StringBuilder errorMsg = new StringBuilder("Errors:");
			if(null == image) {
				errors = true;
				errorMsg.append(" image is null:");
			}
			else {
				if(image.getHeight() != 128) {
					errors = true;
					errorMsg.append(String.format(" image is not 128 pixels high (%d pixels)", image.getHeight()));
				}
				if(image.getWidth() != 128) {
					errors = true;
					errorMsg.append(String.format(" image is not 128 pixels wide (%d pixels)", image.getWidth()));
				}
			}
			if(tileXPosition < 0)  {
				errors = true;
				errorMsg.append(" tileXPosition is < 0");
			}
			if(tileYPosition < 0)  {
				errors = true;
				errorMsg.append(" tileYPosition is < 0");
			}

			if(errors) {
				throw new RuntimeException(errorMsg.toString());
			}

		}
		
		public StaticTile128x128 build() {
			validate();
			return new StaticTile128x128(image, tileXPosition, tileYPosition);
		}

		public Builder coordinates(IntPair coords) {
			return this.tileXPosition(coords.x).tileYPosition(coords.y);
		}
	}
}
