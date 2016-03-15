package no.flatsum.exploratory.level;
import java.util.HashMap;
import java.util.Map;

public class TMXLevel {

	public String version;
	public Orientation orientation;
	public RenderOrder renderOrder;
	public int width;
	public int height;
	public int tilewidth;
	public int tileheight;
	public String nextobjectid;
	Map<String, TMXLevelLayer> layers = new HashMap<>();
	
	public void addLayer(TMXLevelLayer layer) {
		layers.put(layer.name, layer);
	}
	
	public TMXLevelLayer getLayer(String layerName) {
		return layers.get(layerName);
	}
	
	public String getNextobjectid() {
		return nextobjectid;
	}

	public void setNextobjectid(String nextobjectid) {
		this.nextobjectid = nextobjectid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
	    this.orientation = Orientation.fromString(orientation);
	}

	public RenderOrder getRenderorder() {
		return renderOrder;
	}

	public void setRenderorder(String renderOrder) {
		this.renderOrder = RenderOrder.fromString(renderOrder);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(String width) {
	    this.width = Integer.parseInt(width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(String height) {
	    this.height = Integer.parseInt(height);
	}

	public int getTilewidth() {
		return tilewidth;
	}

	public void setTilewidth(String tilewidth) {
	    this.tilewidth = Integer.parseInt(tilewidth);
	}

	public int getTileheight() {
		return tileheight;
	}

	public void setTileheight(String tileheight) {
	    this.tileheight = Integer.parseInt(tileheight);
	}

	@Override
	public String toString() {
		return "LevelFromTMX [version=" + version + ", orientation=" + orientation + ", renderorder=" + renderOrder
				+ ", width=" + width + ", height=" + height + ", tilewidth=" + tilewidth + ", tileheight=" + tileheight
				+ ", layers=" + layers.keySet()
				+ "]";
	}

	public static enum RenderOrder {
		RIGHT_DOWN, LEFT_DOWN, RIGHT_UP, LEFT_UP;

		public static RenderOrder fromString(final String renderOrder) {
			switch(renderOrder) {
			case "right-down":
			case "RIGHT_DOWN":
				return RenderOrder.RIGHT_DOWN;
			case "right-up":
			case "RIGHT_UP":
				return RenderOrder.RIGHT_UP;
			case "left-down":
			case "LEFT_DOWN":
				return RenderOrder.LEFT_DOWN;
			case "left-up":
			case "LEFT_UP":
				return RenderOrder.LEFT_UP;

			default:
				throw new IllegalArgumentException(String.format("No RenderOrder by name %s", renderOrder));
			}
		}
	}
	
	public static enum Orientation {
		ISOMETRIC, HEXAGONAL, ORTHOGONAL, STAGGERED;
		
		public static Orientation fromString(final String orientation) {
			switch(orientation) {
			case "ISOMETRIC":
			case "isometric":
			    return Orientation.ISOMETRIC;
			case "HEXAGONAL":
			case "hexagonal":
			    return Orientation.HEXAGONAL;
			case "ORTHOGONAL":
			case "orthogonal":
			    return Orientation.ORTHOGONAL;
			case "STAGGERED":
			case "staggered":
			    return Orientation.STAGGERED;
			default:
				throw new IllegalArgumentException(String.format("No Orientation by name %s", orientation));
			}
		}
	}
}
