package no.flatsum.exploratory.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;
import no.flatsum.exploratory.inter.Tile;

public class LayeredTile implements Tile{
	private final int width;
	private final int height;
	
	private BufferedImage[] layers;
	private BufferedImage graphic;
	
	private IntPair tilePosition;
	
	public LayeredTile(int numLayers, int width, int height, IntPair tilePosition) {
		this.tilePosition = new IntPair(tilePosition);
		this.width = width;
		this.height = height;
		layers = new BufferedImage[numLayers];
		graphic = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * This sets a given layer of the tile to hold a specific image, and then recreates the graphic.
	 * The graphic is recreated when the layer changes, not when getGraphics is called.
	 * Layer is 0 indexed, and the lower numbers are below the higher numbers.
	 * That means, that layer 1 is drawn on top of layer 1.
	 * 
	 * @param layer the layer you wish to change
	 * @param graphic the {@link BufferedImage} you wish to set the layer to. 
	 * In the event of the image being too large, the image is cropped and the top-left of the image is preserved.
	 */
	public void setLayer(int layer, BufferedImage graphic) {
		layers[layer] = graphic;
		updateGraphics();
	}
	
	/*
	 * This recreates the image, which might be expensive, but we don't care right now, because it's fast enough.
	 */
	private void updateGraphics() {
		Graphics2D draw = graphic.createGraphics();
		for(BufferedImage img : layers) {
			if(null != img) {
				draw.drawImage(img, 0, 0, null);
			}
		}
		draw.dispose();
	}
	
	@Override
	public BufferedImage getGraphics() {
		return graphic;
	}

	@Override
	public IntPair startAt() {
		return pixelLocation();
	}

	@Override
	public IntPair tilePosition() {
		return new IntPair(tilePosition);
	}

	@Override
	public IntPair pixelOffset() {
		return IntPair.zeroedIntPair();
	}

	@Override
	public IntPair pixelLocation() {
		return new IntPair(tilePosition.x * width, tilePosition.y * height);
	}
	
}
