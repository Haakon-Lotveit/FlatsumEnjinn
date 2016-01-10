package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

import no.haakon.flatsum.auxilliary.resource.ResourceConfigurationParser;
import no.haakon.flatsum.graphic.tile.StaticTile128x128;
import no.haakon.flatsum.graphic.tile.Tile;
import no.haakon.flatsum.level.Layer;
import no.haakon.flatsum.level.Level;

public class FlatLevel implements Level {
	private Layer layer;

	public FlatLevel(Layer layer) throws IOException {
		this.layer = layer;
	}

	@Override
	public int getNumberOfLayers() {
		return 1;
	}

	@Override
	public void setLayer(Layer layer, int place) {
		this.layer = layer;
	}

	@Override
	public void paint(Graphics gfx) {
		layer.paint(gfx);
	}

	@Override
	public void paintSubset(Graphics gfx, int topmost, int leftmost, int rightmost, int bottommost) {
		layer.paintSubset(gfx, topmost, leftmost, rightmost, bottommost);
	}

	public static FlatLevel.Builder builder() {
		return new Builder();
	}

	static class Builder {
		private Map<String, IntPair> nameMappings;
		private SpriteSheet sprites;

		public Builder() {
			this.nameMappings = new HashMap<>();
		}

		public Builder setSpriteSheet(SpriteSheet sprites) {
			this.sprites = sprites;
			return this;
		}

		public Builder addNames(File tileDescriptions) throws NumberFormatException, IOException {
			ResourceConfigurationParser parser = new ResourceConfigurationParser(tileDescriptions);
			parser.parse().forEach(x -> nameMappings.put(x[0], new IntPair(Integer.parseInt(x[1]), Integer.parseInt(x[2]))));
			return this;
		}

		public FlatLevel buildLevel(File levelFile) throws FileNotFoundException {
			try {
				Layer l = SimpleLayer128x128.fromFile(levelFile, nameMappings, sprites);
				return new FlatLevel(l);
			}
			catch(IOException e) {
				throw new RuntimeException("An IOException escaped.", e);
			}
		}
	}
}
