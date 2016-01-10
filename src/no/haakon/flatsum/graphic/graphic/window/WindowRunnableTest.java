package no.haakon.flatsum.graphic.graphic.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class WindowRunnableTest implements GraphicWorkload {
	private Canvas canvas;
	private final int squareSize;

	public WindowRunnableTest(Canvas canvas, int squareSize) {
		this.canvas = canvas;
		this.squareSize = squareSize;
	}

	@Override
	public void run()  {
		canvas.createBufferStrategy(2);
		BufferStrategy bs = canvas.getBufferStrategy();

		while(true){
			Graphics gfx = bs.getDrawGraphics();
						
			int[] rectArgs = createRectArgs();
			Color col = new Color(rectArgs[2], rectArgs[3], rectArgs[4]);
			gfx.setColor(col);
			gfx.drawRect(rectArgs[0], rectArgs[1], squareSize, squareSize);				

			gfx.dispose();
			bs.show();
			
			System.out.printf("%3d,%3d,%3d%n", col.getRed(), col.getGreen(), col.getRed());
			try {
				Thread.sleep(750L);
			} catch (InterruptedException e) {
				System.out.println("Interruption found");
				return;
			}
		}
	}
	
	private int[] createRectArgs() {
		Random random = new Random();
		int[] out = new int[5];
		
		out[0] = random.nextInt(canvas.getWidth() - squareSize);
		out[1] = random.nextInt(canvas.getHeight() - squareSize);
		out[2] = random.nextInt(256);
		out[3] = random.nextInt(256);
		out[4] = random.nextInt(256);
		
		return out;
	}
	
	@Override
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
}
