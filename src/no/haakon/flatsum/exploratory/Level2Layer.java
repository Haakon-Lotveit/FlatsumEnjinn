package no.haakon.flatsum.exploratory;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import no.haakon.flatsum.auxilliary.resource.ResourceConfigurationParser;
import no.haakon.flatsum.exploratory.FlatLevel.Builder;
import no.haakon.flatsum.level.Layer;
import no.haakon.flatsum.level.Level;

public class Level2Layer implements Level {

	private Layer layer1;
	private Layer layer2;

	public Level2Layer(Layer layer1, Layer layer2) {
		this.layer1 = layer1;
		this.layer2 = layer2;
	}
	
	@Override
	public int getNumberOfLayers() {
		return 2;
	}

	@Override
	public void setLayer(Layer layer, int place) {
		switch(place) {
		case 1:
			setLayer1(layer);
			break;
		case 2:
			setLayer2(layer);
			break;
		default:
			throw new IllegalArgumentException(String.format("Place must be either 1 or 2, was %d", place));
		}	
	}

	public void setLayer1(Layer layer) {
		this.layer1 = layer;
	}

	public void setLayer2(Layer layer) {
		this.layer2 = layer;
	}

	@Override
	public void paint(Graphics gfx) {
		layer1.paint(gfx);
		layer2.paint(gfx);		
	}

	@Override
	public void paintSubset(Graphics gfx, int topmost, int leftmost, int rightmost, int bottommost) {
		layer1.paintSubset(gfx, topmost, leftmost, rightmost, bottommost);
		layer2.paintSubset(gfx, topmost, leftmost, rightmost, bottommost);		
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private Map<String, IntPair> tileNames;
		private SpriteSheet spriteSheet;
		private File layer1;
		private File layer2;

		public Builder() {
			tileNames = new HashMap<>();
		}
		
		public Builder addNames(File tileDescriptions) throws NumberFormatException, IOException {
			ResourceConfigurationParser parser = new ResourceConfigurationParser(tileDescriptions);
			parser.parse().forEach(x -> tileNames.put(x[0], new IntPair(Integer.parseInt(x[1]), Integer.parseInt(x[2]))));
			return this;
		}
		
		public Builder setTileNames(Map<String, IntPair> tileNames) {
			this.tileNames = tileNames;
			return this;
		}


		public Builder setSpriteSheet(SpriteSheet spriteSheet) {
			this.spriteSheet = spriteSheet;
			return this;
		}

		public Builder setLayer1(File layer1) {
			this.layer1 = layer1;
			return this;
		}

		public Builder setLayer2(File layer2) {
			this.layer2 = layer2;
			return this;
		}

		private void validate() {
			StringBuilder msg = new StringBuilder("Error(s):");
			boolean error = false;
			if(0 == tileNames.size()) {
				msg.append(" No name -> coordinate mappings have been defined.");
				error = true;
			}
			if(null == spriteSheet) {
				msg.append(" spriteSheet is not set");
				error = true;
			}
			if(null == layer1) {
				msg.append(" layer1 is not set");
				error = true;
			}
			if(null == layer2) {
				msg.append(" layer2 is not set");
				error = true;
			}
			if(error) {
				throw new IllegalStateException(msg.toString());
			}
		}

		public Level2Layer build() throws FileNotFoundException {
			validate();
			Layer layer1 = SimpleLayer128x128.fromFile(this.layer1, tileNames, spriteSheet);
			Layer layer2 = SimpleLayer128x128.fromFile(this.layer2, tileNames, spriteSheet);
			
			return new Level2Layer(layer1, layer2);
		}
	}

}

