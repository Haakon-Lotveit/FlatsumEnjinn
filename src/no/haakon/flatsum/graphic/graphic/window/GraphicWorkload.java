package no.haakon.flatsum.graphic.graphic.window;

import java.awt.Canvas;

public interface GraphicWorkload extends Runnable {

	public void setCanvas(Canvas canvas);
	
	@Override
	public void run();
}
