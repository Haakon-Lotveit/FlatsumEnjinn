package no.flatsum.exploratory.inter;

import java.awt.image.BufferedImage;

import no.flatsum.exploratory.convenience.IntPair;

/**
 * Anything that is drawable can be asked to provide an image that is supposed to be drawn.
 * Most classes that are drawable can also be expected to be geographic, but this is not enforced.
 * 
 * @author haakon
 *
 */
public interface Drawable {
	/**
	 * Provides whatever graphical solution you're using with the image that represents this entity.
	 * This call is not guaranteed to be cheap, nor is it guaranteed to stay the same between calls.
	 * Implementing classes are allowed to assume that this method will be called once for every frame, and only once.
	 * 
	 * @return the graphical representation of an entity, for a given frame.
	 */
	public BufferedImage getGraphics();
	
	/**
	 * Tells the renderer where to start drawing at 
	 * @return an IntPair where the x and y values correspond to coordinates.
	 */
	public IntPair startAt();
}
