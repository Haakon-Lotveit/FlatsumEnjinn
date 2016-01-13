package no.haakon.flatsum.exploratory;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import no.haakon.flatsum.graphic.graphic.window.GraphicWorkload;
import no.haakon.flatsum.level.LevelGraphics;

public class JustPaintLevel implements GraphicWorkload {

	private volatile Canvas canvas;
	private volatile LevelGraphics level;
	
	public JustPaintLevel(LevelGraphics level, Canvas canvas) {
		this.level = level;
		this.canvas = canvas;
	}
	
	@Override
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void run() {
		canvas.createBufferStrategy(2);
		BufferStrategy bs = canvas.getBufferStrategy();

		Graphics gfx = null;
		while(!Thread.interrupted()){
			gfx = bs.getDrawGraphics();
			Thread.currentThread().interrupt();

			level.paint(gfx);

			gfx.dispose();
			bs.show();
		}
	}

}
