package no.flatsum.exploratory.level.tmx;

/**
 * This represents a layer in a TMX-level file, the format used by the Tiled map editor.
 * We are <i>only</i> interested in supporting orthogonal maps, saved using CSV, with no compression, that goes left-down.
 * While the LevelFromTMX-class and this TMXLevelLayer class both accept these types of input, the builders that make levels from them will throw Exceptions
 * if you violate this very simple contract.
 * The reason why these two classes does not throw anything are twofold:
 * <ol>
 * <li>First of all, the data is valid, and these classes care solely about holding valid data. They do not know about parsing.</li>
 * <li>Secondly, more support might be added in the future, and thus this way will require less rewriting.</i>
 * </ol>
 * 
 * @author haakon
 *
 */
public class TMXLevelLayer {
	int width;
	int height;
	int layerNumber;
	String name;
	String data;
	Encoding encoding;
	Compression compression;

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
	public int getLayerNumber() {
		return layerNumber;
	}
	public void setLayerNumber(int layerNumber) {
		this.layerNumber = layerNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Encoding getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = Encoding.fromString(encoding);
	}
	
	public Compression getCompression() {
		return compression;
	}
	public void setCompression(String compression) {
		this.compression = Compression.fromString(compression);
	}

	@Override
	public String toString() {
		return "TMXLevelLayer [width=" + width + ", height=" + height + ", layerNumber=" + layerNumber + ", name="
				+ name + ", encoding=" + encoding + ", compression=" + compression + "]";
	}

	public static enum Compression {
		NONE, ZLIB, GZIP;

		public static Compression fromString(final String compression) {
			switch(compression) {
			// When the data is not compressed, there is no compression attribute
			case "":
			case "NONE":
			case "none":
				return Compression.NONE;

			case "ZLIB":
			case "zlib":
				return Compression.ZLIB;

			case "GZIP":
			case "gzip":
				return Compression.GZIP;

			default:
				throw new IllegalArgumentException(String.format("No compression with the name '%s'", compression));
			}
		}
	}

	public static enum Encoding {
		XML, CSV, BASE64;

		public static Encoding fromString(final String encoding) {
			switch(encoding) {
			// When the data is stored as XML, there is no encoding attribute. YAY.
			case "": 
			case "XML":
			case "xml":
				return Encoding.XML;

			case "CSV":
			case "csv":
				return Encoding.CSV;

			case "BASE64":
			case "base64":
				return Encoding.BASE64;

			default:
				throw new IllegalArgumentException(String.format("No encoding with the name '%s'", encoding));
			}
		}
	}
}
