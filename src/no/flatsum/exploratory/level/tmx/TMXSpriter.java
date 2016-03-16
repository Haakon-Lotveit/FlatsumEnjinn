package no.flatsum.exploratory.level.tmx;

import java.awt.image.BufferedImage;
import java.util.PriorityQueue;
import java.util.Queue;

public class TMXSpriter {
	private final int tileWidth;
	private final int tileHeight;
	
	private Queue<TMXSheet> sheets = new PriorityQueue<>();
	
	public TMXSpriter(int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	public void addSpriteSheet(String name, BufferedImage sprite, int startingGid) {
		sheets.add(new TMXSheet(name, sprite, startingGid));
	}

	public BufferedImage getSprite(int tileID) {
		// If there is nothing there, then the tileID will be 0
		if(0 == tileID) {
			return null;
		}
		
		TMXSheet sheet = findSheet(tileID);
		int localID = tileID - sheet.startingGid;
		int col = localID % sheet.numCols;
		int row = localID / sheet.numCols;
		
		return sheet.sprite.getSubimage(tileWidth * col, tileHeight * row, tileWidth, tileHeight);
	}
	
	private TMXSheet findSheet(int tileID) {
		for(TMXSheet sheet : sheets) {
			if(sheet.startingGid <= tileID) {
				return sheet;
			}
		}
		return null;
	}

	private class TMXSheet implements Comparable<TMXSheet>{		
		public final String name;
		public final BufferedImage sprite;
		public final int startingGid;
		public final int numCols;
		
		public TMXSheet(String name, BufferedImage sprite, int startingGid) {
			this.name = name;
			this.sprite = sprite;
			this.startingGid = startingGid;
			this.numCols = sprite.getWidth() / tileWidth;
		}

		@Override
		public int compareTo(TMXSheet o) {
			return Integer.compare(startingGid, o.startingGid);
		}

		@Override
		public String toString() {
			return "TMXSheet [name=" + name + ", startingGid=" + startingGid + "]";
		}
		
	}
}
