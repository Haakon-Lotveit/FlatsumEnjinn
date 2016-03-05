package no.haakon.flatsum.exploratory;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import no.flatsum.exploratory.impl.IntPair;
import no.flatsum.view.screen.GraphicWorkload;
import no.flatsum.view.screen.Window;
import no.haakon.flatsum.auxilliary.resource.FileImageLoader;
import no.haakon.flatsum.level.Layer;
import no.haakon.flatsum.level.LevelGraphics;

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
		int numProcessors = Runtime.getRuntime().availableProcessors();
		System.out.printf("Number of hardware threads available: %d%n", numProcessors);
		
		FileImageLoader imageLoader = new FileImageLoader(new File("conf/images/basic-game-images.irc"), 128, 128);
		imageLoader.getNames().forEach(System.out::println);
		
		SpriteSheet spriteSheet = imageLoader.makeSpriteSheet("static-tiles");		
		SpriteSheetLookup backgroundLookup = new SpriteSheetLookup(new File("conf/spritesheets/level-tile.desc"));

		SpriteSheet foregroundSpriteSheet = imageLoader.makeSpriteSheet("foreground-features");
		SpriteSheetLookup foregroundLookup = new SpriteSheetLookup(new File("conf/spritesheets/features.desc"));
		
		
		Location town = new Town(new IntPair(128, 128), new IntPair(1, 1), spriteSheet.getSprite(new IntPair(1,0)));
		TreasureChest chest = new TreasureChest(imageLoader.getImage("treasure-chest"), new IntPair(4,4), 128, 128);
		Unit fighter = new FighterDude(imageLoader.makeSpriteSheet("fighter"), new IntPair(4, 2), 128, 128);
		
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

		LevelGraphics level = Level2Layer.builder()
				.setBackgroundLayer(background)
				.setForegroundLayer(foreground)
				.build();
		
		GameMap firstLevel = new GameMap(level);
		firstLevel.putLocation(town);
		firstLevel.putGameWorldObject(chest);
		firstLevel.addUnit(fighter);
		
		Window gameWindow = new Window("Test", 1024, 768);
		
		GraphicWorkload workload = new PaintLevel(gameWindow.getCanvas(), firstLevel);
		
		TickerWorker ticker = new TickerWorker(1000_000_000L);
		ticker.addActor(fighter);
		
		// We need some threads to do our bidding.
		// We'll grab one for each of audio, video, I/O and AI.
		// Even if we're probably not going to use them all.
		// On the Wii U, we'd have 3 threads, IIRC. On the xbone and ps4 we'd have 6 or so available. (OS takes some too).
		// On a PC it could be everything from two to a gajillion.
		// In other words this will not end well. :)
		ExecutorService pool = Executors.newFixedThreadPool(4);		
		pool.submit(workload);
		pool.submit(ticker);
		
		Thread.sleep(6_000L);
		System.out.println("DONE");
		pool.shutdownNow();

	}
}
