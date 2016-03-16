package no.flatsum.exploratory.inter;

public interface GameMap extends Drawable, SubsetDrawable {
	/**
	 * 
	 * @return The width of the map in pixels;
	 */
	public int widthInPixels();
	
	/**
	 * 
	 * @return The height of the map in pixels;
	 */
	public int heightInPixels();
}
