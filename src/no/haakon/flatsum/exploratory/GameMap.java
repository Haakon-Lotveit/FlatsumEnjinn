package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import no.flatsum.exploratory.impl.IntPair;
import no.haakon.flatsum.level.LevelGraphics;

public class GameMap {
	final LevelGraphics level;
	volatile Map<IntPair, Location> places;
	volatile List<GameWorldObject> gameWorldObjects;
	volatile List<Unit> units;
	public GameMap(LevelGraphics level) {
		this.level = level;
		places = new HashMap<>();
		gameWorldObjects = new LinkedList<>();
		units = new LinkedList<>();
	}
	
	public void putLocation(Location location) {
		places.put(location.getCoordinates(), location);
	}

	public void paint(Graphics gfx) {		
		level.paint(gfx);
		
		for(Location l : places.values()) {
			l.paint(gfx);
		}
		
		for(GameWorldObject gwo : gameWorldObjects) {
			gwo.paint(gfx);
		}
		
		for(Unit unit : units) {
			unit.paint(gfx);
		}
	}

	public synchronized void putGameWorldObject(GameWorldObject item) {
		gameWorldObjects.add(item);
	}

	public synchronized void addUnit(Unit unit) {
		units.add(unit);		
	}
}
