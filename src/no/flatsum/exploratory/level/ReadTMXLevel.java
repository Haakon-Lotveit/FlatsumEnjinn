package no.flatsum.exploratory.level;
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

public class ReadTMXLevel {
	private XPathExpression version;
	private DocumentBuilder documentBuilder;
	private XPathExpression orientation;
	private XPathExpression renderorder;
	private XPathExpression width;
	private XPathExpression height;
	private XPathExpression tilewidth;
	private XPathExpression tileheight;
	private XPathExpression nextobjectid;
	private XPathExpression numLayers;
	private XPath xpath;

	public ReadTMXLevel() throws ParserConfigurationException, XPathExpressionException {
		XPathFactory xpfact = XPathFactory.newInstance();
		xpath = xpfact.newXPath();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		documentBuilder = dbf.newDocumentBuilder();
		version = xpath.compile("/map/@version");
		orientation = xpath.compile("/map/@orientation");
		renderorder = xpath.compile("/map/@renderorder");
		width = xpath.compile("/map/@width");
		height = xpath.compile("/map/@height");
		tilewidth = xpath.compile("/map/@tilewidth");
		tileheight = xpath.compile("/map/@tileheight");
		nextobjectid = xpath.compile("/map/@nextobjectid");
		numLayers = xpath.compile("count(/map/layer)");
		
	}
	
	public TMXLevel readLevel(File levelFile) throws SAXException, IOException, XPathExpressionException {
		TMXLevel out = new TMXLevel();
		Document level = documentBuilder.parse(levelFile);
		level.normalize();
		
		out.setVersion(version.evaluate(level));
		out.setOrientation(orientation.evaluate(level));
		out.setRenderorder(renderorder.evaluate(level));
		out.setWidth(width.evaluate(level));
		out.setHeight(height.evaluate(level));
		out.setTilewidth(tilewidth.evaluate(level));
		out.setTileheight(tileheight.evaluate(level));
		out.setNextobjectid(nextobjectid.evaluate(level));

		int numLayers = Integer.parseInt(this.numLayers.evaluate(level)); 
		for(int i = 1; i <= numLayers; ++i) {
			out.addLayer(makeLayer(level, i));
		}

		return out;
	}

	private TMXLevelLayer makeLayer(Document level, int layerNumber) throws XPathExpressionException {
		
		TMXLevelLayer layer = new TMXLevelLayer();
		layer.setLayerNumber(layerNumber);
		layer.setName(xpath.evaluate(String.format("/map/layer[%d]/@name", layerNumber), level));
		layer.setWidth(xpath.evaluate(String.format("/map/layer[%d]/@width", layerNumber), level));
		layer.setHeight(xpath.evaluate(String.format("/map/layer[%d]/@height", layerNumber), level));
		layer.setData(xpath.evaluate(String.format("/map/layer[%d]/data", layerNumber), level)); 
		layer.setEncoding(xpath.evaluate(String.format("/map/layer[%d]/data/@encoding", layerNumber), level)); 
		layer.setCompression(xpath.evaluate(String.format("/map/layer[%d]/@compression", layerNumber), level));

		return layer;
	}
	
	public static void main(String[] args) throws Throwable {
		File xmlFile = new File("sample.tmx");
		
		ReadTMXLevel levelReader = new ReadTMXLevel();
		TMXLevel level = levelReader.readLevel(xmlFile);
		
		System.out.println(level);
	}
	
	
}
