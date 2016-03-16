package no.flatsum.exploratory.level;

import java.io.File;

public class TMXTileset {
    private int firstGid;
    private String name;
    private int tileWidth;
    private int tileHeight;
    private File source;
    private int imageWidth;
    private int imageHeight;

    public int getFirstGid() {
		return firstGid;
	}
	public void setFirstGid(String firstGid) {
	    this.firstGid = Integer.valueOf(firstGid);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTileWidth() {
		return tileWidth;
	}
	public void setTileWidth(String tileWidth) {
	    this.tileWidth = Integer.valueOf(tileWidth);
	}
	public int getTileHeight() {
		return tileHeight;
	}
	public void setTileHeight(String tileHeight) {
	    this.tileHeight = Integer.valueOf(tileHeight);
	}
	public File getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = new File(source);
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(String imageWidth) {
	    this.imageWidth = Integer.valueOf(imageWidth);
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(String imageHeight) {
	    this.imageHeight = Integer.valueOf(imageHeight);
	}

    @Override
	public String toString() {
		return "TMXTileset [firstGid=" + firstGid + ", name=" + name + ", tileWidth=" + tileWidth + ", tileHeight="
				+ tileHeight + ", source=" + source + ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight
				+ "]";
	}

	
}
