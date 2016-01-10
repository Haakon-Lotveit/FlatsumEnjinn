package no.haakon.flatsum.exploratory;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import no.haakon.flatsum.graphic.graphic.window.GraphicWorkload;
import no.haakon.flatsum.level.Level;

public class PaintLevel implements GraphicWorkload {
	private Canvas canvas;
	private Level level;

	public PaintLevel(Canvas canvas, Level level) {
		this.canvas = canvas;
		this.level = level;
	}

	@Override
	public void run()  {
		canvas.createBufferStrategy(2);
		BufferStrategy bs = canvas.getBufferStrategy();

		while(true){
			Graphics gfx = bs.getDrawGraphics();
						
			level.paint(gfx);

			gfx.dispose();
			bs.show();
			
			try {
				Thread.sleep(750L);
			} catch (InterruptedException e) {
				System.out.println("Interruption found");
				return;
			}
		}
	}
	
	@Override
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
}
