package no.flatsum.view.screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;

public class Window implements GraphicPanel, KeyboardSource, MouseSource {
	private String title;
	private int frameWidthPixels;
	private int frameHeightPixels;
	private JFrame frame;
	private Canvas canvas;
	private KeyListener keyboardListener;
	private MouseAdapter mouseAdapter;
	
	public Window(String title, int widthPixels, int heightPixels) {
		this.title = title;
		this.frameWidthPixels = widthPixels;
		this.frameHeightPixels = heightPixels;
		
		init();
	}
	
	private void init() {
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(frameWidthPixels, frameHeightPixels);
		
		canvas = new Canvas();
		canvas.setBackground(Color.BLACK);
		
		frame.add(canvas);
		
		frame.setMinimumSize(size);
		frame.setMaximumSize(size);
		frame.setPreferredSize(size);
		frame.setResizable(false);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public Canvas getCanvas() {
		return canvas;
	}
	
	@Override
	public MouseAdapter getMouse() {
		return mouseAdapter;
	}
	
	@Override
	public KeyListener getKeyboard() {
		return keyboardListener;
	}
	
	public static void main(String[] args) throws InterruptedException {
		GraphicPanel w = new Window("Test", 1000, 1000);
		GraphicWorkload gw = new WindowRunnableTest(w.getCanvas(), 300);

		gw.run();
		
		System.out.println("DONE");
		
		
		
		System.out.println("Shutting down");
	}
	
}
