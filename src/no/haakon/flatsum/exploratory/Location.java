package no.haakon.flatsum.exploratory;

import java.awt.Graphics;

import no.flatsum.exploratory.convenience.IntPair;

public interface Location {
	public void onAction();
	public IntPair getCoordinates();
	public int getXPosition();
	public int getYPosition();
	public void paint(Graphics gfx);
	
}
