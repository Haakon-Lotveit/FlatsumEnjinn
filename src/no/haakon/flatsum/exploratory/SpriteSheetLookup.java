package no.haakon.flatsum.exploratory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import no.flatsum.exploratory.convenience.IntPair;

public class SpriteSheetLookup {
	Map<String, IntPair> registry;
	
	public SpriteSheetLookup() {
		registry = new HashMap<>();
	}
	
	public SpriteSheetLookup(File file) throws NumberFormatException, IOException {
		this();
		this.addNames(file);
	}

	public void addNames(File tileDescriptions) throws NumberFormatException, IOException {
		try(Scanner freader = new Scanner(tileDescriptions)) {
			while(freader.hasNextLine()) {
				parseLine(freader.nextLine());
			}
		}
	}
	
	private void parseLine(String line) {
		try(Scanner lineReader = new Scanner(line)) {
			String name = lineReader.next();
			int xCoordinate = lineReader.nextInt();
			int yCoordinate = lineReader.nextInt();
			
			setAddress(name, xCoordinate, yCoordinate);
		}
	}
	
	public void setAddress(String name, int xCoordinate, int yCoordinate) {
		setAddress(name, new IntPair(xCoordinate, yCoordinate));
	}
	
	public void setAddress(String name, IntPair address) {
		registry.put(name, address);
	}
	
	public IntPair getAddress(String name) {
		IntPair address = registry.get(name);
		if(null == address) {
			throw new IllegalArgumentException(String.format("No address saved for the string \"%s\"", name));
		}
		
		return address;
	}
}
