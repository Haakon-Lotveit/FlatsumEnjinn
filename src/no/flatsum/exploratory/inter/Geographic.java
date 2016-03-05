package no.flatsum.exploratory.inter;

import no.flatsum.exploratory.convenience.IntPair;

/**
 * A Geographic entity has a location. In terms of which tile it is situated, where within that tile it might be situated (in case of walking for example) and in terms of its pixel position.
 * 
 * @author haakon
 *
 */
public interface Geographic {
	public IntPair tilePosition();
	public IntPair pixelOffset();
	public IntPair pixelLocation();
}
