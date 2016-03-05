package no.flatsum.loader;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private final int width;
	private final int height;
	BufferedImage sheet;
	
	public SpriteSheet(Properties properties) throws IOException {
		width = Integer.parseInt(properties.getProperty(ImageLoader.SPRITE_WIDTH));
		height = Integer.parseInt(properties.getProperty(ImageLoader.SPRITE_HEIGHT));
		
		sheet = ImageIO.read(new File(properties.getProperty(ImageLoader.IMAGE_FILE)));
	}

	public BufferedImage getSprite(int column, int row) {
		BufferedImage out;
		try{
			out = sheet.getSubimage(column * width, row * height, width, height);
		}
		catch(RasterFormatException rfe) {
			throw new IllegalArgumentException(String.format("Cannot look up sprite at position <%d,%d>", column, row), rfe);
		}
		return out;
	}
	
	public int tileWidth() {
		return width;
	}
	
	public int tileHeight() {
		return height;
	}

	public int numberOfColumns() {
		return sheet.getWidth() / width;
	}
	
	public int numberOfRows() {
		return sheet.getHeight() / height;
	}

}
