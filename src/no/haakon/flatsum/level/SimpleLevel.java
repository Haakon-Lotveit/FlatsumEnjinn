package no.haakon.flatsum.level;

import java.awt.Graphics;
import java.util.Arrays;

public class SimpleLevel implements Level {
	Layer[] layers;
	
	public SimpleLevel(int numLayers) {
		
		if(numLayers <= 0) {
			throw new IllegalArgumentException("You need to have at least one layer");
		}
		
		this.layers = new Layer[numLayers];
		Arrays.fill(layers, NoLayer.NONE);
	}
	
	@Override
	public int getNumberOfLayers() {
		return layers.length;
	}
	
	@Override
	public void setLayer(Layer layer, int place) {
		if(place < 0){
			throw new IllegalArgumentException("You cannot have a negative index on layers. It's 0 and up.");
		}
		if(place > getNumberOfLayers() - 1) {
			throw new IllegalArgumentException(String.format("Highest layer is %d, you gave %d", getNumberOfLayers() - 1, place));
		}
		
		layers[place] = layer;
	}
	
	@Override
	public void paint(Graphics gfx){
		for(int i = 0; i < layers.length; ++i) { 
			layers[i].paint(gfx);
		}
	}
	
	@Override
	public void paintSubset(Graphics gfx, int topmost, int leftmost, int rightmost, int bottommost) {
		for(int i = 0; i < layers.length; ++i) { 
			layers[i].paintSubset(gfx, topmost, leftmost, rightmost, bottommost);
		}
	}
}
