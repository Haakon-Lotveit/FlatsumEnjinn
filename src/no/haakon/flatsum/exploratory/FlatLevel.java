package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		private SpriteSheetLookup nameMappings;
		private SpriteSheet sprites;

		public Builder() {
			this.nameMappings = new SpriteSheetLookup();
		}

		public Builder setSpriteSheet(SpriteSheet sprites) {
			this.sprites = sprites;
			return this;
		}

		public Builder addNames(File tileDescriptions) throws NumberFormatException, IOException {
			nameMappings.addNames(tileDescriptions);
			return this;
		}

		public FlatLevel buildLevel(File levelFile) throws FileNotFoundException, IOException {
			Layer l = SimpleLayer128x128.builder()
					.setCsvFile(levelFile)
					.setLookup(nameMappings)
					.setSpriteSheet(sprites)
					.build();
			return new FlatLevel(l);
		}
	}
}
