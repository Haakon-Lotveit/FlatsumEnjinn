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
	private long waitingTime;
	public PaintLevel(Canvas canvas, GameMap level, int maxFPS) {
		this.canvas = canvas;
		this.level = level;
		waitingTime =  1000L / maxFPS;
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

			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				System.out.println("The thread responsible for painting graphics have received an interruption and will shut down");
				return;
			}
		}
	}

	@Override
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
}
