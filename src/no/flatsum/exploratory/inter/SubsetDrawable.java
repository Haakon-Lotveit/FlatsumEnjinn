package no.flatsum.exploratory.inter;

import java.awt.image.BufferedImage;

/**
 * Anything that is SubsetDrawable can draw a subset of itself.
 * By drawing we mean that they can return a buffered image that represents that subset.
 * 
 * @author haakon
 *
 */
public interface SubsetDrawable {
	/**
	 * Returns a subset of an image to be drawn.
	 * @param the centre of the "camera" so to speak, in pixels
	 * @param width how wide the image is supposed to be
	 * @param height how tall the image is supposed to be
	 * @return a BufferedImage representing the subset it's supposed to draw.
	 */
	public BufferedImage subsection(int x, int y, int screenWidthPixels, int screenHeightPixels);
}
