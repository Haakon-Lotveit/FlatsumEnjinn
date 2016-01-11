package no.haakon.flatsum.exploratory;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class SpriteMaker {
	List<Image> images;
	int width;
	int height;

	/**
	 * Creates a new SpriteSheetMaker.
	 * It assumes every image you load into it will have the same width and height.
	 * If the image does not adhere to spec, it will crash.
	 * 
	 * @param width The width of the subimages in pixels
	 * @param height The height of the subimages in pixels
	 */
	public SpriteMaker(int width, int height) {
		this.images = new ArrayList<Image>();		
		this.width = width;
		this.height = height;
	}

	public void addImage(File imageFile) throws IOException {
		Image loadedImage = ImageIO.read(imageFile);

		//TODO: Better testing without assertions and a custom exception of some kind
		assert(width == loadedImage.getWidth(null));
		assert(height == loadedImage.getHeight(null));

		images.add(loadedImage);		
	}

	private List<Integer> divisorsOf(int number) {
		List<Integer> divisors = new ArrayList<>();
		while(number > 1) {
			for(int i = 2; i <= number; ++i) {
				if(number % i == 0) {
					number /= i;
					divisors.add(i);
					break;
				}
			}
		}
		
		divisors.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer arg0, Integer arg1) {
				return Integer.compare(arg1, arg0);
			}
		});
		
		return divisors;
	}
	/*
	 * Algorithm:
	 * 1: Get a list of prime divisors, remove 0.
	 * 2: Set left = 1 and right = 1
	 * 3: while there are at least 4 divisors left:
	 *   multiply left with the smallest and largest and remove them
	 *   multiply right with the smallest and largest and remove them
	 * 4: while there are at least 2 divisors left:
	 *   multiply left with the largest and right with the smallest
	 * 5: While there is 1 divisor left:
	 *   multiply the smallest one with the divisor
	 *   
	 * 6 return new int[] {left, right};
	 */
	private int[] getDimensions(int number) {
		int left = 1;
		int right = 1;
		List<Integer> divisors = divisorsOf(number);
		
		
		while(divisors.size() >= 1) {
			if(right < left) {
				right *= divisors.get(0);
			}
			else {
				left *= divisors.get(0);
			}
			divisors.remove(0);
		}
		
		return new int[] {left, right};
	}
	
	public void save(File file) throws IOException {		
		int num = images.size();		
		int dims[] = getDimensions(num);
		int numColumns = dims[0];
		int numRows = dims[1];
		
		BufferedImage sheet = new BufferedImage(width * numColumns, height * numRows, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D gfx = sheet.createGraphics();
		for(int i = 0; i < num; ++i) {
			int curCol = (i % numColumns); // This is a bit weird but the math checks out.
			int curRow = (i / numColumns);
			int currentLeftPixel =  curCol * width;
			int currentTopPixel = curRow * height;
			Image currentImage = images.get(i);		
			gfx.drawImage(currentImage, currentLeftPixel, currentTopPixel, width, height, null);
		}

		ImageIO.write(sheet, "png", file);
	}

	public static void usage() {
		System.out.println("USAGE:");
		System.out.println("This is not even remotely finished, sorry.");		
	}
	public static void main(String[] args) throws Throwable {		
		if(args.length != 1) {
			usage();
			System.exit(0);
		}
		
		File inputFile = new File(args[0]);
						 
		try(Scanner freader = new Scanner(inputFile)) {
			String destinationPath = freader.nextLine();
			int xSize = Integer.parseInt(freader.nextLine());
			int ySize = Integer.parseInt(freader.nextLine());
			
			SpriteMaker sm = new SpriteMaker(xSize, ySize);
			
			while(freader.hasNextLine()) {
				File image = new File(freader.nextLine());
				sm.addImage(image);
			}
			
			File output = new File(destinationPath);

			sm.save(output);
			System.out.printf("Successfully made new spritesheet file \"%s\"%n", output.getAbsolutePath());
		}
		catch(Throwable t) {
				System.err.printf("Error reading file \"%s\"%n", inputFile);
				throw t;
		}			

		
	}
}
