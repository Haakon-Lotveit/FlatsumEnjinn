package no.haakon.flatsum.auxilliary.resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import no.haakon.flatsum.exploratory.SpriteSheet;

public class FileImageLoader {
	Map<String, BufferedImage> imageBuffer;

	public FileImageLoader(File resourceConfig) throws IOException {
		imageBuffer = new HashMap<>();
		loadConfiguration(resourceConfig);
	}

	public void loadConfiguration(File resourceConfig) throws IOException {
		ResourceConfigurationParser ircp = new ResourceConfigurationParser(resourceConfig);
		ircp.parse().forEach(this::loadImage);
	}
	
	/**
	 * Grabs all the names of all the images that have been registered in the loader
	 * @return a collection of Strings, where each string is a single name.
	 */
	public Collection<String> getNames() {
		return imageBuffer.keySet();
	}
	
	/**
	 * Looks up and returns an image with a given name.
	 * @param name the name of the image, as specified by the configuration files that were used to start the FileImageLoader or added later (via the load method)
	 * @return a buffered version of said image
	 * @throws NoSuchElementException if the image does not exist.
	 */
	public BufferedImage getImage(String name) throws NoSuchElementException {
		if(imageBuffer.containsKey(name)) {
			return imageBuffer.get(name);
		}
		else {
			throw new NoSuchElementException(String.format("No image with the name <%s> is registered", name));
		}
	}
	private void loadImage(String[] parsed) {
		try {
			if(parsed.length != 2) {
				throw new IllegalArgumentException(String.format("Expected an array of length two, but got:%n%s", Arrays.toString(parsed)));
			}
			String name = parsed[0];
			File file = new File(parsed[1]);
			loadImage(name, file);
		}
		catch(IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void loadImage(String name, File imageFile) throws IOException {
		BufferedImage image = ImageIO.read(imageFile);
		imageBuffer.put(name, image);

	}

	public SpriteSheet makeSpriteSheet(String imageName, int width, int height) {
		if(!imageBuffer.containsKey(imageName)) {
			throw new IllegalArgumentException(String.format("No image by the name \"%s\" loaded", imageName));
		}
		BufferedImage sprite = getImage(imageName);
		return new SpriteSheet(sprite, width, height);
	}
}
