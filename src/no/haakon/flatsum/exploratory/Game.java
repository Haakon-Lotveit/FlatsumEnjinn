package no.haakon.flatsum.exploratory;

import java.io.File;

import no.haakon.flatsum.auxilliary.resource.FileImageLoader;
import no.haakon.flatsum.graphic.graphic.window.GraphicWorkload;
import no.haakon.flatsum.graphic.graphic.window.Window;
import no.haakon.flatsum.level.Layer;
import no.haakon.flatsum.level.Level;

public class Game {
	// step 1: grab resources
	// step 2: set up graphics
	// step 3: set up sound
	// step 4: make a window
	// step 5: start!

	/*
	 * NamedSpriteSheet levelTiles = NamedSpriteSheetFactory.make(new File("conf/spritesheets/static-level-tiles"), imageLoader);
	 * FlatLevel level = new FlatLevel(new File("lvl/testlvl/1.csv"), levelTiles);		
	 */

	public static void main(String[] args) throws Throwable {
		Window gameWindow = new Window("Test", 1024, 768);
		
		FileImageLoader imageLoader = new FileImageLoader(new File("conf/images/basic-game-images.irc"));
		imageLoader.getNames().forEach(System.out::println);
		
		SpriteSheet spriteSheet = new SpriteSheet(imageLoader.getImage("static-tiles"), 128, 128);		
		SpriteSheetLookup backgroundLookup = new SpriteSheetLookup(new File("conf/spritesheets/level-tile.desc"));

		SpriteSheet foregroundSpriteSheet = imageLoader.makeSpriteSheet("foreground-features", 128, 128);
		SpriteSheetLookup foregroundLookup = new SpriteSheetLookup(new File("conf/spritesheets/features.desc"));
		
		Layer background = SimpleLayer128x128.builder()
				.setCsvFile(new File("lvl/testlvl/1.csv"))
				.setSpriteSheet(spriteSheet)
				.setLookup(backgroundLookup)
				.build();
		
		Layer foreground = SimpleLayer128x128.builder()
				.setCsvFile(new File("lvl/testlvl/2.csv"))
				.setSpriteSheet(foregroundSpriteSheet)
				.setLookup(foregroundLookup)
				.build();

		Level level = Level2Layer.builder()
				.setBackgroundLayer(background)
				.setForegroundLayer(foreground)
				.build();
		GraphicWorkload workload = new PaintLevel(gameWindow.getCanvas(), level);

		workload.run();

		System.out.println("DONE");

	}
}
