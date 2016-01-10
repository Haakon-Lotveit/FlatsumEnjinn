package no.haakon.flatsum.exploratory;

import java.io.File;

import no.haakon.flatsum.auxilliary.resource.FileImageLoader;
import no.haakon.flatsum.graphic.graphic.window.GraphicWorkload;
import no.haakon.flatsum.graphic.graphic.window.Window;
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
		FileImageLoader imageLoader = new FileImageLoader(new File("conf/images/basic-game-images.irc"));
		imageLoader.getNames().forEach(System.out::println);
		SpriteSheet spriteSheet = new SpriteSheet(imageLoader.getImage("static-tiles"), 128, 128);
		Window gameWindow = new Window("Test", 1024, 768);
		Level level = FlatLevel.builder()
				.addNames(new File("conf/spritesheets/level-tile-descriptions"))
				.setSpriteSheet(spriteSheet)
				.buildLevel(new File("lvl/testlvl/1.csv"));

//		Level level = Level2Layer.builder()
//					.addNames(new File("conf/spritesheets/level-tile-descriptions"))
//					.setSpriteSheet(spriteSheet)
//					.setLayer1(new File("lvl/testlvl/1.csv"))
//					.setLayer2(new File("lvl/testlvl/2.csv"))
//					.build();
		GraphicWorkload workload = new PaintLevel(gameWindow.getCanvas(), level);
		
		workload.run();
		
		System.out.println("DONE");

	}
}
