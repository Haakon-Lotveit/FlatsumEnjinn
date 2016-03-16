package no.haakon.flatsum.exploratory;

import no.flatsum.exploratory.convenience.IntPair;

public interface Geographic {
	public IntPair getTileLocation();
	public IntPair getPixelLocation();
	public void setTileLocation(IntPair location);
}
