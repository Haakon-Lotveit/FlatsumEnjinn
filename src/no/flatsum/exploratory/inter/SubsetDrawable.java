package no.flatsum.exploratory.inter;

import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;

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
	 * @param topLeft where to start drawing.
	 * @param width how wide the image is supposed to be
	 * @param height how tall the image is supposed to be
	 * @return a BufferedImage representing the subset it's supposed to draw.
	 */
	public BufferedImage subsection(IntPair topLeft, int width, int height);
}
