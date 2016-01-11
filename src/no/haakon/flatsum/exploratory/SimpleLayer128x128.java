package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		SpriteSheetLookup lookup;
		SpriteSheet spriteSheet;
		File csvFile;

		private Builder() {

		}

		public Builder setLookup(SpriteSheetLookup lookup) {
			this.lookup = lookup;
			return this;
		}

		public Builder setSpriteSheet(SpriteSheet spriteSheet) {
			this.spriteSheet = spriteSheet;
			return this;
		}

		public Builder setCsvFile(File csvFile) {
			this.csvFile = csvFile;
			return this;
		}

		public void validate() {
			StringBuilder errorMsg = new StringBuilder("Errors: ");
			boolean error = false;

			if(null == lookup) {
				error = true;
				errorMsg.append(" lookup was null");
			}

			if(null == spriteSheet) {
				error = true;
				errorMsg.append(" spriteSheet was null");
			}

			if(null == csvFile) {
				error = true;
				errorMsg.append(" csvFile was null");
			}
			if(error) {
				throw new IllegalStateException(errorMsg.toString());
			}
		}
		
		public SimpleLayer128x128 build() throws FileNotFoundException, IOException {
			validate();
			
			Tile[][] tiles = readTiles();
			
			return new SimpleLayer128x128(tiles);
		}
		
		private Tile[][] readTiles() throws FileNotFoundException, IOException {
			List<String[]> csvData = readCSVFile();
			
			Tile[][] out = new Tile[csvData.size()][];
			for(int lineNumber = 0; lineNumber< out.length; ++lineNumber) {
				String[] inLine = csvData.remove(0);
				out[lineNumber] = readLine(lineNumber, inLine);
			}
			
			return out;
		}

		private List<String[]> readCSVFile() throws FileNotFoundException, IOException {
			try(FileReader freader = new FileReader(csvFile);
					CSVReader reader = new CSVReader(freader, ',', '\'')) {
				return reader.readAll();
			}
		}
		
		private Tile[] readLine(int lineNumber, String[] inLine) {
			Tile[] line = new Tile[inLine.length];
			for(int i = 0; i < line.length; ++i) {
				String name = inLine[i];
				Tile tile = makeTile(name, i, lineNumber);
				line[i] = tile;
			}
			
			return line;
		}
		
		private Tile makeTile(final String name, final int xPosition, final int yPosition) {
			switch(name) {
			case "":
					return new NoTile(xPosition, yPosition, 128, 128);
			default:
				IntPair address = lookup.getAddress(name);
				BufferedImage sprite = spriteSheet.getSprite(address);
				Tile tile = new StaticTile128x128(sprite, xPosition, yPosition);
				return tile;
			}
		}
	}
}
