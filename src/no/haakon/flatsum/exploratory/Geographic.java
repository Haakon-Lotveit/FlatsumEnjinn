package no.haakon.flatsum.exploratory;

public interface Geographic {
	public IntPair getTileLocation();
	public IntPair getPixelLocation();
	public void setTileLocation(IntPair location);
}
