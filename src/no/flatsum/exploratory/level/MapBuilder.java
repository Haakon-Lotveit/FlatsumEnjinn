package no.flatsum.exploratory.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import no.flatsum.exploratory.impl.LayeredTile;
import no.flatsum.exploratory.impl.Tiler64x64;
import no.flatsum.exploratory.level.tmx.TMXLevel;
import no.flatsum.exploratory.level.tmx.TMXLevelLayer;
import no.flatsum.exploratory.level.tmx.TMXSpriter;
import no.flatsum.exploratory.level.tmx.TMXTileset;

/**
 * This builds an {@link OrthographicTopDownSquareMap} out of a TMXLevel
 * The functionality will probably be moved to {@link OrthographicTopDownSquareMap} later.
 * @author haakon
 *
 */
public class MapBuilder {
	private TMXLevel tiledMap;

	public MapBuilder(TMXLevel tmxLevel) {
		this.tiledMap = tmxLevel; 
	}
	public OrthographicTopDownSquareMap build() throws IOException {
		System.out.println("NB! We only use one layer (the bottom layer) as of now");
		
		// Sanity checks
		if(tiledMap.getRenderorder() != TMXLevel.RenderOrder.RIGHT_DOWN) {
			throw new IllegalStateException("We can only parse LEFT-DOWN maps, and this map was " + tiledMap.getRenderorder());
		}
		if(tiledMap.getOrientation() != TMXLevel.Orientation.ORTHOGONAL) {
			throw new IllegalStateException("We can only recreate ORTHOGONAL maps, and this map was " + tiledMap.getOrientation());			
		}
		if(tiledMap.getLayer(1).getEncoding() != TMXLevelLayer.Encoding.CSV) {
			throw new IllegalStateException("We can only recreate CSV-endoded maps, and this map was encoded using " + tiledMap.getLayer(1).getEncoding());
		}
		
		int width = tiledMap.getWidth();
		int height = tiledMap.getHeight();
		int tileWidth = tiledMap.getTilewidth();
		int tileHeight = tiledMap.getTileheight();
		
		TMXSpriter spriter = new TMXSpriter(tileWidth, tileHeight);
		Tiler64x64 tiler = new Tiler64x64();
		tiler.setNumLayers(tiledMap.getNumLayers());
		
		
		LayeredTile[][] tiles = new LayeredTile[width][];
		for(int column = 0; column < tiles.length; ++column) {
			tiles[column] = new LayeredTile[height];
			tiler.setColumn(column);
			for(int row = 0; row < tiles[column].length; ++row) {
				tiler.setRow(row);
				tiles[column][row] = tiler.tile();
			}
		}
	
		for(TMXTileset tileset : tiledMap.tilesets()) {			
			BufferedImage sprite = ImageIO.read(tileset.getSource());
			int startingGid = tileset.getFirstGid();
			String name = tileset.getName();

			spriter.addSpriteSheet(name, sprite, startingGid);
		}

		// This is not the best way of doing it, but it works. ;)
		// TODO: Extend this for all layers (IN PROGRESS)
		for(int layer = 0; layer < tiledMap.getNumLayers(); ++layer) {
			String data = tiledMap.getLayer(layer+1).getData().replace("\n", "").replace("\\s", "");
			String[] datapoints = data.trim().split(",");
			int index = 0;
			for(int row = 0; row < height; ++row){
				for(int col = 0; col < width; ++col) {
					int tileID = Integer.valueOf(datapoints[index++]);
					BufferedImage sprite = spriter.getSprite(tileID);
					tiles[col][row].setLayer(0, sprite);
				}
			}
		}
		
		OrthographicTopDownSquareMap map = new OrthographicTopDownSquareMap(tiles, tileWidth, tileHeight);
		
		return map;
	}
}
