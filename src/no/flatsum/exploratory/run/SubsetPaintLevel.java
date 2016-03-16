package no.flatsum.exploratory.run;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import no.flatsum.exploratory.inter.GameMap;
import no.flatsum.view.screen.GraphicWorkload;

public class SubsetPaintLevel implements GraphicWorkload{
	private GameMap level;
	private Canvas canvas;
	private volatile int cameraLeft;
	private volatile int cameraTop;
	
	public SubsetPaintLevel(Canvas canvas, GameMap level) {
		this.level = level;
		this.canvas = canvas;
		this.cameraLeft = canvas.getWidth() / 2;
		this.cameraTop = canvas.getHeight() / 2;
	}
	
	@Override
	public void setCanvas(Canvas canvas) {
		synchronized (this.canvas) {
			this.canvas = canvas;	
		}
	}

	public void moveCameraTop(int delta) {
		this.cameraTop += delta;
	}
	
	public void moveCameraLeft(int delta) {
		int newLeft = cameraLeft + delta;
		this.cameraLeft = newLeft;
	}
	
	public void setCameraTop(int y) {
		this.cameraTop = y;
	}
	
	public void setCameraLeft(int x) {
		this.cameraLeft = x;
	}
	@Override
	public void run() {
		// Currently we're just centering on the map...
		canvas.createBufferStrategy(2);
		BufferStrategy bs = canvas.getBufferStrategy();
		
		while(true){
			Graphics gfx = bs.getDrawGraphics();

			BufferedImage lvl = level.subsection(cameraLeft, cameraTop, canvas.getWidth(), canvas.getHeight());
			
			gfx.drawImage(lvl, 0, 0, null);
			
			gfx.dispose();
			bs.show();	
		}
	}

}
