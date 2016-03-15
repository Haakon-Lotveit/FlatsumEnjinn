package no.flatsum.exploratory.run;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import no.flatsum.exploratory.impl.OrthographicTopDownSquareMap;
import no.flatsum.exploratory.impl.Tiler64x64;
import no.flatsum.exploratory.inter.GameMap;
import no.flatsum.exploratory.inter.Tile;
import no.flatsum.loader.ImageLoader;
import no.flatsum.view.screen.GraphicWorkload;
import no.flatsum.view.screen.Window;
import no.flatsum.view.screen.WindowRunnableTest;

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
		 * And while this shouldn't be here, let's just make a map too:
		 */
		
		GameMap map;
		{ // We put this bit in a block to make the variables go away afterwards. Kind of like a let block in python/lisp/haskell
			int size = 64;
			Tile[][] tiles = new Tile[size][size];
			for(int x = 0; x < size; ++x) {
				tiler.setColumn(x);
				for (int y = 0; y < size; ++y) {
					tiler.setRow(y);
					tiles[x][y] = tiler.tile();
				}
			}
			map = new OrthographicTopDownSquareMap(tiles, size, size);
		}
		
		
		/*
		 * Now that we're done loading things from disk, we create a new window to run our game in.
		 */
		Window w = new Window("Game", 1280, 720);
				
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
