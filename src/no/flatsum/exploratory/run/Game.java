package no.flatsum.exploratory.run;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import no.flatsum.exploratory.impl.Tiler64x64;
import no.flatsum.exploratory.inter.GameMap;
import no.flatsum.exploratory.inter.Tile;
import no.flatsum.exploratory.level.MapBuilder;
import no.flatsum.exploratory.level.OrthographicTopDownSquareMap;
import no.flatsum.exploratory.level.TMXLevel;
import no.flatsum.exploratory.level.TMXLevelParser;
import no.flatsum.loader.ImageLoader;
import no.flatsum.view.screen.GraphicWorkload;
import no.flatsum.view.screen.Window;

public class Game {
	public static void main(String[] args) throws IOException {
		
		// Step 1: We find a file containing a list of all the spritesheets we want to import, and read it.
		File spritesheetsList = new File ("conf/spritesheets/spritesheets.list");		
		List<String> foo = Files.readAllLines(spritesheetsList.toPath(), StandardCharsets.UTF_8);
		
		/*
		 * Step 2: Now that we have the list of spritesheets (or really property files describing them)
		 * we load them into memory using the ImageLoader.
		 * After that we have all the graphical stuff loaded into memory.
		 * (Although we do not yet have maps and such.)
		 */
		ImageLoader loader = new ImageLoader();
		for(String line : foo) {
			loader.addSpriteSheet(new File(line));
		}
		
		System.out.println(loader.toString());
		
		/*
		 * Then we set up a Tiler that can spit out tiles for us.
		 */
		
		Tiler64x64 tiler = new Tiler64x64(0, 0, loader.getSprite("static-tiles", 0, 1));
		
		/*
		 * We need to read in a map at some point, so might as well do it here:
		 */
		TMXLevelParser levelParser = new TMXLevelParser();
		File levelFile = new File("one-layer.tmx");
		TMXLevel tmxLevel = levelParser.readLevel(levelFile);
		System.out.println(tmxLevel);
		
		/*
		 * Code that turns it into an actual GameMap for some sort is not yet complete.
		 * But it will go here when it is.
		 */
		MapBuilder mapBuilder = new MapBuilder(tmxLevel);
		GameMap map = mapBuilder.build();
		
		/*
		 * Now that we're done loading things from disk, we create a new window to run our game in.
		 */
		Window w = new Window("Game", 1280, 1280);
				
		/*
		 * And we need to set up a thread to draw our level
		 */
		GraphicWorkload drawLevel = new PaintLevel(w.getCanvas(), map, 60);
		Thread graphicThread = new Thread(drawLevel, "GFX-LEVEL");
		graphicThread.start();
		
		/*
		 * Normally we'd now set up things like input and so on, but for now, we're just going to render the level for 40 seconds and then exit.
		 */
		try {			
			Thread.sleep(40_000L);
		} catch (InterruptedException e) {
			System.out.println("Couldn't sleep, killing everything");
		}
		graphicThread.interrupt();
		System.exit(0);
	}
}
