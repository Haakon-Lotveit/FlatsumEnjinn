package no.flatsum.exploratory.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import no.flatsum.exploratory.impl.Tiler64x64;
import no.flatsum.exploratory.inter.Tile;

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
		if(tiledMap.getLayer(1).encoding != TMXLevelLayer.Encoding.CSV) {
			throw new IllegalStateException("We can only recreate CSV-endoded maps, and this map was encoded using " + tiledMap.getLayer(1).getEncoding());
		}
		
		int width = tiledMap.getWidth();
		int height = tiledMap.getHeight();
		int tileWidth = tiledMap.getTilewidth();
		int tileHeight = tiledMap.getTileheight();
		
		TMXSpriter spriter = new TMXSpriter(tileWidth, tileHeight);
		Tiler64x64 tiler = new Tiler64x64(0, 0, null);
		
		
		
		Tile[][] tiles = new Tile[width][];
		for(int i = 0; i < tiles.length; ++i) {
			tiles[i] = new Tile[height];
		}
	
		for(TMXTileset tileset : tiledMap.tilesets()) {			
			BufferedImage sprite = ImageIO.read(tileset.getSource());
			int startingGid = tileset.getFirstGid();
			String name = tileset.getName();

			spriter.addSpriteSheet(name, sprite, startingGid);
		}

		// This is not the best way of doing it, but it works. ;)
		String data = tiledMap.getLayer(1).getData().trim().replace("\n", "").replace("\\s", "");
		String[] datapoints = data.trim().split(",");
		int index = 0;
		for(int col = 0; col < width; ++col){
			tiler.setColumn(col);
			for(int row = 0; row < height; ++row) {
				tiler.setRow(row);
				int tileID = Integer.valueOf(datapoints[index++]);
				tiler.setSprite(spriter.getSprite(tileID));
				tiles[col][row] = tiler.tile();
				
			}
		}
			
		OrthographicTopDownSquareMap map = new OrthographicTopDownSquareMap(tiles, tileWidth, tileHeight);
		
		return map;
	}
}
