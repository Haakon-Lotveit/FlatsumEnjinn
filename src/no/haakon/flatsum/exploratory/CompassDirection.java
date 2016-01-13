package no.haakon.flatsum.exploratory;

/**
 * Directions in the game. These directions are always relative to the map, and never anything else.
 * NORTH = up on the map, WEST = left on the map, EAST = right on the map, and SOUTH = down on the map.
 * But I'm betting you guessed that anyway, didn't you.
 * @author Haakon Løtveit
 *
 */
public enum CompassDirection {
	NORTH, WEST, EAST, SOUTH;

	public CompassDirection turnLeft() {
		CompassDirection newTurn = this;
		switch(this) {
		case NORTH:
			newTurn = CompassDirection.WEST;
			break;
		case WEST:
			newTurn = CompassDirection.SOUTH;
			break;
		case EAST:
			newTurn = CompassDirection.NORTH;
			break;
		case SOUTH:
			newTurn = CompassDirection.EAST;
			break;
		default:
			throw new IllegalStateException(String.format("CompassDirection does not know how to turn left from direction %s", this.toString()));
		}		
		return newTurn;
	}

	public CompassDirection turnRight() {
		CompassDirection out;
		switch(this) {
		case NORTH:
			out = EAST;
			break;
		case WEST:
			out = NORTH;
			break;
		case EAST:
			out = SOUTH;
			break;
		case SOUTH:
			out = WEST;
			break;
		default:
			throw new IllegalStateException(String.format("CompassDirection does not know how to turn right from direction %s", this.toString()));
		} 
		return out;
	}

	public CompassDirection turnAround() {
		CompassDirection out;
		switch(this) {
		case NORTH:
			out = SOUTH;
			break;
		case WEST:
			out = EAST;
			break;
		case EAST:
			out = WEST;
			break;
		case SOUTH:
			out = NORTH;
			break;
		default:
			throw new IllegalStateException(String.format("CompassDirection does not know how to turn right from direction %s", this.toString()));
		}
		return out;
	}
}
