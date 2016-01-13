package no.haakon.flatsum.exploratory;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import no.haakon.flatsum.graphic.graphic.window.GraphicWorkload;

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
			try {
				Thread.sleep(750L);
				Graphics gfx = bs.getDrawGraphics();

				level.paint(gfx);

				gfx.dispose();
				bs.show();

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
