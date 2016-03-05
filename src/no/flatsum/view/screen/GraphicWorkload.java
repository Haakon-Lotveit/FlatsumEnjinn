package no.flatsum.view.screen;

import java.awt.Canvas;

public interface GraphicWorkload extends Runnable {

	public void setCanvas(Canvas canvas);
	
	@Override
	public void run();
}
