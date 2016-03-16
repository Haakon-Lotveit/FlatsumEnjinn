package no.flatsum.exploratory.run;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import no.flatsum.exploratory.inter.GameMap;
import no.flatsum.view.screen.GraphicWorkload;

public class PaintLevel implements GraphicWorkload {
	private Canvas canvas;
	private GameMap level;
	public PaintLevel(Canvas canvas, GameMap level) {
		this.canvas = canvas;
		this.level = level;
	}

	@Override
	public void run()  {
		canvas.createBufferStrategy(2);
		BufferStrategy bs = canvas.getBufferStrategy();

		while(true){
			Graphics gfx = bs.getDrawGraphics();

			BufferedImage lvl = level.getGraphics();
			
			gfx.drawImage(lvl, 0, 0, null);
			
			gfx.dispose();
			bs.show();	
		}
	}

	@Override
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
}
