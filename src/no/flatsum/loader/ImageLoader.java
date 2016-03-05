package no.flatsum.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class ImageLoader {
	public static final String SPRITESHEET_NAME = "spritesheet.name";
	public static final String IMAGE_FILE = "image.file";
	public static final String SPRITE_WIDTH = "sprite.width";
	public static final String SPRITE_HEIGHT = "sprite.height";


	private Map<String, SpriteSheet> spritesheets;

	public ImageLoader() {
		spritesheets = new HashMap<>();
	}

	/**
	 * Adds a spritesheet as specified by the properties file
	 * @param propFile A file that complies with java.util.Properties file formats.
	 * @return the unique name of the spritesheet that has been loaded.
	 * @throws IOException if parsing does not succeed, if the spritesheet's name is not unique, or if there are missing properties.
	 */
	public String addSpriteSheet(File propFile) throws IOException {
		String out;
		try(FileInputStream fis = new FileInputStream(propFile)) {
			 out = addSpriteSheet(fis); 
		}
		return out;
	}
	
	/**
	 * Adds a spritesheet to the configuration.
	 * Reads the configuration file and adds it to its known spritesheets.
	 * 
	 * @param configuration An InputStream that can read the config. This can be from a File, URL, or something else.
	 * @return the unique name of the spritesheet that has been loaded.
	 * @throws IOException if parsing does not succeed, if the spritesheet's name is not unique, or if there are missing properties.
	 */
	public String addSpriteSheet(InputStream configuration) throws IOException {
		Properties properties = new Properties();
		properties.load(configuration);

		validateProperties(properties);			

		SpriteSheet ss = new SpriteSheet(properties);
		spritesheets.put(properties.getProperty(SPRITESHEET_NAME), ss);

		return properties.getProperty(SPRITESHEET_NAME);
	}

	/**
	 * Returns the sprite at a given position from a given spritesheet.
	 * @param name The name of the spritesheet
	 * @param column The zero-indexed column you wish to get the sprite from
	 * @param row The zero-indexed row you wish to get the sprite from
	 * @return the sprite at the given location
	 * @throws IllegalArgumentException if there is no such spritesheet, or if the row and/or column doesn't exist
	 */
	public BufferedImage getSprite(String name, int column, int row) throws IllegalArgumentException {
		ensureHasSpriteSheet(name);

		return spritesheets.get(name).getSprite(column, row);
	}

	/**
	 * This is a utility method that makes it somewhat easier to load sprites from maps made with Tiled.
	 * In Tiled, the tiles do not have any semantic information that can be used directly by our tiles, and it uses a different system to refer to various tiles on a spritesheet.
	 * This can be a bit of a pain, but it's not Tiled's fault. Tiled makes maps, not games.
	 * Our job would therefore be to use what Tiled knows about the map (the layout, the layers, etc.) and use that to rebuild it here.
	 * The ImageLoader can therefore sort-of understand what Tiled means when it says "tile 5 in the spritesheet called 'static-tiles'", and try to do the right thing.
	 * The trap here however, is that ImageLoader will assume that the name is unique, when Tiled does no such thing. It will also not care about what file Tiled uses, it will only care about the name.
	 * So if you have a spritesheet called 'tiles', and ImageLoader has a spritesheet called 'tiles' it will assume those are the same spritesheets.
	 * 
	 * @param spritesheetName The name of the spritesheet to load from.
	 * @param spriteNumber the sprite-number to load. This is translated to a column and row.
	 * @return a sprite from the relevant spritesheet. Does not load anything.
	 */
	public BufferedImage getSpriteFromTmx(String spritesheetName, int spriteNumber) {
		ensureHasSpriteSheet(spritesheetName);
		
		--spriteNumber;
		SpriteSheet ss = spritesheets.get(spritesheetName);
		
		int numColumns = ss.numberOfColumns();
		int column = spriteNumber % numColumns;
		int row = spriteNumber / numColumns; 
		
		return ss.getSprite(column, row);
	}

	private void ensureHasSpriteSheet(String name) {
		if(!spritesheets.containsKey(name)) {
			throw new IllegalArgumentException(String.format("No spritesheet with the name \"%s\" is registered", name));
		}
	}
	
	/*
	 * This is a simple method for checking that all the constraints in the properties are met.
	 * Namely:
	 *  - That all the necessary fields are set
	 *  - That there is no name collision.
	 *  
	 *  If there are any such errors, it will build an error string, and report back immediately.
	 */
	private void validateProperties(Properties props) throws IOException {
		boolean throwError = false;
		StringBuilder errorMsg = new StringBuilder().append("Errors:");
		if(!props.containsKey(SPRITESHEET_NAME)) {
			throwError = true;
			errorMsg.append(" Spritesheet has not been given a name.");
		}
		if(!props.containsKey(IMAGE_FILE)) {
			throwError = true;
			errorMsg.append(" No image file specified.");
		}
		if(!props.containsKey(SPRITE_WIDTH)) {
			throwError = true;
			errorMsg.append(" No sprite width specified.");
		}
		if(!props.containsKey(SPRITE_HEIGHT)) {
			throwError = true;
			errorMsg.append(" No sprite height specified.");
		}

		if(spritesheets.containsKey(props.getProperty(SPRITESHEET_NAME))) {
			String name = props.getProperty(SPRITESHEET_NAME);
			throwError = true;
			errorMsg.append(String.format("SpriteSheet with name %s is already loaded: %s.", name, spritesheets.get(name)));
		}
		if(throwError) {
			throw new IOException(errorMsg.toString());
		}
	}
	
	@Override
	public int hashCode() {
		return 31 * spritesheets.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof ImageLoader) {
			ImageLoader il = (ImageLoader) o;
			return il.spritesheets.equals(this.spritesheets);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("<%s with spritesheets: %s>", this.getClass().getSimpleName(), spritesheets.keySet().toString());
	}
}
