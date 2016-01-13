package no.haakon.flatsum.exploratory;

public interface Direction {
	public CompassDirection getDirection();
	public void turnLeft();
	public void turnRight();
	public void turnAround();
	public void setDirection(CompassDirection direction);
}
