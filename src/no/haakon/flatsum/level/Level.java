package no.haakon.flatsum.level;

import java.awt.Graphics;

public interface Level {

	public int getNumberOfLayers();

	public void setLayer(Layer layer, int place);

	public void paint(Graphics gfx);

	public void paintSubset(Graphics gfx, int topmost, int leftmost,
			int rightmost, int bottommost);

}