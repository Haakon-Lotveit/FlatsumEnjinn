package no.flatsum.exploratory.convenience;

import java.io.IOException;
import java.util.Properties;

import no.flatsum.loader.ImageLoader;

public class MakeEmptyProperties {
	public static void main(String[] args) throws IOException {
		Properties p = new Properties();
		p.setProperty(ImageLoader.SPRITESHEET_NAME, "map-tiles");
		p.setProperty(ImageLoader.IMAGE_FILE, "~/mods/SuperGame/jason's map tile pack.png");
		p.setProperty(ImageLoader.SPRITE_HEIGHT, "64");
		p.setProperty(ImageLoader.SPRITE_WIDTH, "64");
		
		p.store(System.out, "Example properties defining a spritesheet");
		System.out.println();
		p.storeToXML(System.out, "Example properties defining a spritesheet in XML");
	}
}
