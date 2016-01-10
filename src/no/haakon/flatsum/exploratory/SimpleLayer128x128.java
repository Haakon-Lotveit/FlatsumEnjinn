package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

import no.haakon.flatsum.graphic.tile.NoTile;
import no.haakon.flatsum.graphic.tile.StaticTile128x128;
import no.haakon.flatsum.graphic.tile.Tile;
import no.haakon.flatsum.level.Layer;

public class SimpleLayer128x128 implements Layer {
	private final int width = 128;
	private final int height = 128;
	private final Tile[][] tiles;

	public SimpleLayer128x128(Tile[][] tiles) {
		this.tiles = tiles;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	@Override
	public void setTile(Tile t, int x, int y) {
		tiles[x][y] = t;
	}

	@Override
	public void paint(Graphics gfx) {
		for(int i = 0; i < tiles.length; ++i) {
			for(int j = 0; j < tiles[i].length; ++j) {
				tiles[i][j].paint(gfx);
			}
		}		
	}

	@Override
	public void paintSubset(Graphics gfx, int topmost, int leftmost, int rightmost, int bottommost) {
		throw new UnsupportedOperationException("Not implemented yet");

	}

	/**
	 * Builds a layer from the specified information.
	 * It takes an array of names from the (CSV) file, and look up their meanings in the given mappings.
	 * Then it fetches the correct image from the spritesheet and creates a tile from that information.
	 * Once it has created the entire array of tiles it creates a new layer from it, and returns that.
	 * 
	 * @param levelFile the CSV-file that holds the information in the layer. This method will not check that it's a CSV file before attempting to parse it.
	 * @param nameMappings the mapping from names of things to the coordinates in the spritesheet
	 * @param spriteSheet the spritesheet that we're using
	 * @return a new {@link SimpleLayer128x128} with the specified tiles.
	 * @throws FileNotFoundException if the CSV file cannot be found.
	 * @throws RuntimeException if the opened resources cannot be closed.
	 */
	public static SimpleLayer128x128 fromFile(File levelFile, Map<String, IntPair> nameMappings, SpriteSheet spriteSheet) throws FileNotFoundException {
		try(FileReader freader = new FileReader(levelFile);
				CSVReader reader = new CSVReader(freader);) {

			List<String[]> lines = reader.readAll();
			lines.forEach(line -> System.out.println(Arrays.toString(line)));
			Tile[][] tiles = new Tile[lines.size()][];

			for(int currentRow = 0; currentRow < tiles.length; ++currentRow) {
				String[] line = lines.remove(0);
				tiles[currentRow] = new Tile[line.length];
				makeLine(tiles[currentRow], currentRow, line, nameMappings, spriteSheet);
			}			
			SimpleLayer128x128 l = new SimpleLayer128x128(tiles);

			return l;
		}
		catch (FileNotFoundException e) {
			String msg = String.format("Couldn't find the specified file when creating level. File in question: %s", levelFile.getAbsolutePath());
			FileNotFoundException throwMe = new FileNotFoundException(msg);
			// Ensures we get the original stacktrace.
			throwMe.setStackTrace(e.getStackTrace());
			throw throwMe;
		}
		catch (IOException e) {
			String msg = String.format("Couldn't close the the file resources when opening readers. File in question: %s", levelFile.getAbsolutePath());
			throw new RuntimeException(msg, e);
		}
	}

	private static void makeLine(Tile[] tileLine, int yPosition, String[] sourceLine, Map<String,IntPair> nameMappings, SpriteSheet spriteSheet) {
		for(int xPosition = 0; xPosition < sourceLine.length; ++xPosition) {
			if(null == tileLine) {
				throw new IllegalArgumentException("null sent as a tileLine");
			}
			String name = sourceLine[xPosition];
			if(name == null || name.equals("")) {
				tileLine[xPosition] = new NoTile(xPosition, yPosition, 128, 128);
				System.out.print(" empty");
			}
			else {
				IntPair coords = nameMappings.get(name);
				BufferedImage image = spriteSheet.getSprite(coords);
				tileLine[xPosition] = StaticTile128x128.builder().tileXPosition(xPosition).tileYPosition(yPosition).image(image).build();
				System.out.print(" " + name);
			}
		}
		System.out.println();
	}
}
