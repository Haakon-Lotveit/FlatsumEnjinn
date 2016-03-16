package no.flatsum.exploratory.level.tmx;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class TMXLevelParser {
	private final DocumentBuilder documentBuilder;
	private final XPath xpath;
	private final XPathExpression version;
	private final XPathExpression orientation;
	private final XPathExpression renderorder;
	private final XPathExpression width;
	private final XPathExpression height;
	private final XPathExpression tilewidth;
	private final XPathExpression tileheight;
	private final XPathExpression nextobjectid;
	private final XPathExpression countLayers;
	private final XPathExpression countTilesets;

	/**
	 * This class parses out a representation of a TMX level file (used by Tiled) by way of XPath Queries.
	 * Not because using XPath Queries is the best way, because that would 99% likely be using SAX, but because I wanted to see how it would be done.
	 * 
	 * It can be done, and with only medium amounts of pain.
	 * 
	 * Huh. I wonder how much better a SAX-approach would be. Not if mind you, but just by how much.
	 * 
	 * @author haakon
	 */
	public TMXLevelParser() {
		// The XPath Expressions are known to work, so they shouldn't ever throw,
		// and if we cannot make the default DocumentBuilderFactory, then there's nothing we can do about it anyway.
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			documentBuilder = dbf.newDocumentBuilder();

			XPathFactory xpfact = XPathFactory.newInstance();
			xpath = xpfact.newXPath();
			version = xpath.compile("/map/@version");
			orientation = xpath.compile("/map/@orientation");
			renderorder = xpath.compile("/map/@renderorder");
			width = xpath.compile("/map/@width");
			height = xpath.compile("/map/@height");
			tilewidth = xpath.compile("/map/@tilewidth");
			tileheight = xpath.compile("/map/@tileheight");
			nextobjectid = xpath.compile("/map/@nextobjectid");
			countTilesets = xpath.compile("count(/map/tileset)");
			countLayers = xpath.compile("count(/map/layer)");
		}
		catch(XPathExpressionException xpee) {
			throw new RuntimeException("XPath Expression is malformed", xpee);
		}
		catch(ParserConfigurationException pce) {
			throw new RuntimeException("Couldn't create an XPathFactory", pce);
		}
	}

	public TMXLevel readLevel(File levelFile) throws IOException {
		TMXLevel out = new TMXLevel();

		Document level = null;

		// We're not actually all that interested in handling an IOException (the file is not readable, for example) 
		// differently than a SAXException (The XML is borked). Creating a decent error message and then crashing is the best option.
		try {
			level = documentBuilder.parse(levelFile);
		}
		catch(SAXException saxe) {
			throw new IOException("Error parsing level", saxe);
		}

		level.normalize();
		try {
			out.setVersion(version.evaluate(level));
			out.setOrientation(orientation.evaluate(level));
			out.setRenderorder(renderorder.evaluate(level));
			out.setWidth(width.evaluate(level));
			out.setHeight(height.evaluate(level));
			out.setTilewidth(tilewidth.evaluate(level));
			out.setTileheight(tileheight.evaluate(level));
			out.setNextobjectid(nextobjectid.evaluate(level));		
			
			int numTilesets = Integer.valueOf(countTilesets.evaluate(level));
			for(int i = 1; i <= numTilesets; ++i) {
				out.addTileset(makeTileset(level, i));
			}
			
			int numLayers = Integer.parseInt(countLayers.evaluate(level)); 
			for(int i = 1; i <= numLayers; ++i) {
				out.addLayer(makeLayer(level, i));
			}
		}
		catch(XPathExpressionException xpee) {
			throw new RuntimeException("Error running XPath query", xpee);
		}
		return out;
	}
	private TMXTileset makeTileset(Document level, int tilesetIndex) {
		TMXTileset tileset = new TMXTileset();
		// Not interested in the possibilities of XPath errors. They do not exist - Queen Victoria or something.
		try {
			tileset.setFirstGid(xpath.evaluate(String.format("/map/tileset[%d]/@firstgid", tilesetIndex), level));
			tileset.setName(xpath.evaluate(String.format("/map/tileset[%d]/@name", tilesetIndex), level));
			tileset.setTileWidth(xpath.evaluate(String.format("/map/tileset[%d]/@tilewidth", tilesetIndex), level));
			tileset.setTileHeight(xpath.evaluate(String.format("/map/tileset[%d]/@tileheight", tilesetIndex), level));
			tileset.setSource(xpath.evaluate(String.format("/map/tileset[%d]/image/@source", tilesetIndex), level));
			tileset.setImageWidth(xpath.evaluate(String.format("/map/tileset[%d]/image/@width", tilesetIndex), level));
			tileset.setImageHeight(xpath.evaluate(String.format("/map/tileset[%d]/image/@height", tilesetIndex), level));
		} catch (XPathExpressionException e) {
			throw new RuntimeException("Error running XPath query", e);
		}		
		return tileset;
	}
	private TMXLevelLayer makeLayer(Document level, int layerNumber) {
		TMXLevelLayer layer = new TMXLevelLayer();
		layer.setLayerNumber(layerNumber);
		// Again, we know that these expressions won't be malformed, so we're not throwing XPathExpressionExceptions.
		try {
			layer.setName(xpath.evaluate(String.format("/map/layer[%d]/@name", layerNumber), level));
			layer.setWidth(xpath.evaluate(String.format("/map/layer[%d]/@width", layerNumber), level));
			layer.setHeight(xpath.evaluate(String.format("/map/layer[%d]/@height", layerNumber), level));
			layer.setData(xpath.evaluate(String.format("/map/layer[%d]/data", layerNumber), level)); 
			layer.setEncoding(xpath.evaluate(String.format("/map/layer[%d]/data/@encoding", layerNumber), level)); 
			layer.setCompression(xpath.evaluate(String.format("/map/layer[%d]/@compression", layerNumber), level));
		}
		catch(XPathExpressionException xpee) {
			throw new RuntimeException("Error querying out layer information", xpee);
		}
		return layer;
	}

	public static void main(String[] args) throws Throwable {
		File xmlFile = new File("one-layer.tmx");

		TMXLevelParser levelReader = new TMXLevelParser();
		TMXLevel level = levelReader.readLevel(xmlFile);

		System.out.println(level);
	}


}
