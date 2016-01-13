package test.no.haakon.flatsum.exploratory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import no.haakon.flatsum.exploratory.CompassDirection;

public class CompassDirectionTest {

	@Test
	public void testTurnLeft() {
		CompassDirection test;
		test = CompassDirection.NORTH.turnLeft();
		assertEquals("Cannot turn left properly when facing north", CompassDirection.WEST, test);
	
		test = CompassDirection.WEST.turnLeft();
		assertEquals("Cannot turn left properly when facing west", CompassDirection.SOUTH, test);
		
		test = CompassDirection.EAST.turnLeft();
		assertEquals("Cannot turn left properly when facing east", CompassDirection.NORTH, test);
		
		test = CompassDirection.SOUTH.turnLeft();
		assertEquals("Cannot turn left properly when facing south", CompassDirection.EAST, test);
		
	}

	@Test
	public void testTurnRight() {
		CompassDirection test;
	    test = CompassDirection.NORTH.turnRight();
	    assertEquals("Cannot turn right properly when facing north", CompassDirection.EAST, test);
	    
	    test = CompassDirection.WEST.turnRight();
	    assertEquals("Cannot turn right properly when facing west", CompassDirection.NORTH, test);
	    
	    test = CompassDirection.EAST.turnRight();
	    assertEquals("Cannot turn right properly when facing east", CompassDirection.SOUTH, test);
	    
	    test = CompassDirection.SOUTH.turnRight();
	    assertEquals("Cannot turn right properly when facing south", CompassDirection.WEST, test);
	    
	}

	@Test
	public void testTurnAround() {
		CompassDirection test;
		
	    test = CompassDirection.NORTH.turnAround();
	    assertEquals("Cannot turn around properly when facing north", CompassDirection.SOUTH, test);
	    
	    test = CompassDirection.WEST.turnAround();
	    assertEquals("Cannot turn around properly when facing west", CompassDirection.EAST, test);
	    
	    test = CompassDirection.EAST.turnAround();
	    assertEquals("Cannot turn around properly when facing east", CompassDirection.WEST, test);
	    
	    test = CompassDirection.SOUTH.turnAround();
	    assertEquals("Cannot turn around properly when facing south", CompassDirection.NORTH, test);
	    
	}

}
