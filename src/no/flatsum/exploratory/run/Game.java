package no.flatsum.exploratory.run;

import java.io.File;
import java.io.IOException;

import no.flatsum.exploratory.inter.GameMap;
import no.flatsum.exploratory.level.MapBuilder;
import no.flatsum.exploratory.level.tmx.TMXLevel;
import no.flatsum.exploratory.level.tmx.TMXLevelParser;
import no.flatsum.view.screen.GraphicWorkload;
import no.flatsum.view.screen.Window;

public class Game {
	public static void main(String[] args) throws IOException {
		
		/*
		 * Reads in a map from a TMX-file
		 */
		TMXLevelParser levelParser = new TMXLevelParser();
		File levelFile = new File("two-layers.tmx");
		TMXLevel tmxLevel = levelParser.readLevel(levelFile);
		
		/*
	     * builds the map. will probably be refactored into the relevant implementation's class as a static method
	     * OrthographicTopDownSquareMap.build(tmxLevel); ← like so
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
