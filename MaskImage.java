package ftc;

import java.awt.image.BufferedImage;

/**
 * Child of BufferedImage for applying a mask (a matrix function) over a image
 */
public class MaskImage extends BufferedImage {
	private BufferedImage srcImage;
	private int[][] mask;
	private int width = 0;
	private int height = 0;
	public static int color = 0;
	private static int red = 0;
	private static int blue = 0;

	/**
	 * creates a MaskImage from a srcImage
	 * 
	 * @param srcImage
	 *            BufferedImage to apply mask over
	 */
	public MaskImage(BufferedImage srcImage) {
		super(srcImage.getWidth(), srcImage.getHeight(), srcImage.getType());
		this.srcImage = srcImage;
		this.height = srcImage.getHeight();
		this.width = srcImage.getWidth();
	}

	/**
	 * creates a MaskImage from a srcImage and applies a mask over it
	 * 
	 * @param srcImage
	 *            BufferedImage to apply mask over
	 * @param mask
	 *            function to perform over the pixels. masks do not need to be
	 *            normalized
	 */
	public static void getColor() {
		if (color == 2) {
			System.out.print("the image is mostly red with  " + red + " red pixels to " + blue + "blue pixels");

		}
		if (color == 1) {
			System.out.print("the image is mostly blue with  " + blue + " blue pixels to " + red + "red pixels");

		}
	}

	public MaskImage(BufferedImage srcImage, int[][] mask) {
		this(srcImage);
		setMask(mask);

	}

	/**
	 * applies a mask over the srcImage
	 * 
	 * @param mask
	 *            function to perform over the pixels. masks do not need to be
	 *            normalized
	 */
	public void setMask(int[][] mask) {
		this.mask = mask;
		// performMask();
		performAnalyses();
	}

	/**
	 * Apply the Mask over whole image
	 */
	private void performAnalyses() {
		int[] currentcolor;
		int currentcolortotal;
		int red = 1;
		int blue = 2;
		int not = 0;
		int color = 0;
		int lastcolor = 0;
		int blacktobluetot = 0;
		int bluetoblacktot = 0;
		int blacktoredtot = 0;
		int redtoblacktot = 0;
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		int num4 = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// int maskRGB = getMaskColor(x,y);
				int maskRGB = getColorFilter(x, y);

				this.setRGB(x, y, maskRGB);
				currentcolor = ImageHelper.fromRGB(maskRGB);

			}
		}
		FindxStart();
		
	}

	int apex = 0;
	int redxAverage;
	int bluexAverage;
	int redyAverage;
	int blueyAverage;
	int ysearchRange = 0;
	int xsearchRange = 0;

	

	int lastred = 0;

	private void FindxStart() {
		int[] currentColor = new int[3];
		int[] lastColor = new int[3];
		int highestRed = 0;
		int highestBlue = 0;
		int highestRedx = 0;
		int highestBluex = 0;
		int bstreak = 0;
		int rstreak = 0;
		int lastBlue = 0;
		int lastRed = 0;
		int blueLine = 0;
		int redLine = 0;
		int firstblueTemp = 0;
		int firstblue = 0;
		int firstredTemp = 0;
		int firstred = 0;
		int lastbluetemp = 0;
		int lastblue = 0;
		int lastredtemp = 0;
		boolean firstBluePicked = false;
		boolean firstRedPicked = false;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int maskRGB = getColorFilter(x, y);
				currentColor = ImageHelper.fromRGB(maskRGB);
				int currentBlue = currentColor[2];
				int currentRed = currentColor[0];
				if (currentBlue == 255) {
					if (bstreak > 20) {
						lastbluetemp = y;

					}
					if (!firstBluePicked) {
						firstblueTemp = y;
						firstBluePicked = true;
					}
					if (lastBlue == 255) {
						bstreak++;
					}
					blueLine++;
					blueLine += bstreak;
				} else {
					bstreak = 0;
				}
				if (currentRed == 255) {
					if (rstreak > 20) {
						lastredtemp = y;

					}
					if (!firstRedPicked) {
						firstredTemp = y;
						firstRedPicked = true;
					}
					if (lastRed == 255) {
						rstreak++;
					}
					redLine++;
					redLine += rstreak;
				} else {
					rstreak = 0;
				}
				lastBlue = currentBlue;
				lastRed = currentRed;
			}
			if (blueLine > highestBlue) {
				highestBluex = x;
				highestBlue = blueLine;
				firstblue = firstblueTemp;
				lastblue = lastbluetemp;

			}
			if (redLine > highestRed) {
				highestRedx = x;
				highestRed = redLine;
				firstred = firstredTemp;
				lastred = lastredtemp;

			}
			firstBluePicked = false;
			firstRedPicked = false;
			firstblueTemp = 0;
			lastbluetemp = 0;
			firstredTemp = 0;
			lastredtemp = 0;
			blueLine = 0;
			redLine = 0;
		}

		//printHLine(firstred, 0);
		//printHLine(lastred, 0);
		// printHLine(firstblue, 0);
		// printHLine(lastblue, 0);
		 findEdge(highestBluex, firstblue, lastred, "BLUE");
		findEdge(highestRedx, firstred, lastred, "RED");

	}

	private void findEdge(int x, int y1, int y2, String color) {
		int[] currentColor;
		int colorVal = 0;
		int startx = 0;
		int endx = 0;
		for (int x1 = x; x1 > 0; x1 += -1) {
			for (int y = y1; y < y2; y++) {

				int maskRGB = getColorFilter(x1, y);
				currentColor = ImageHelper.fromRGB(maskRGB);
				int colortotal = currentColor[0] + currentColor[2];
				if (colortotal > 0) {
					colorVal++;
				}

			}
			if (colorVal < ((y2 - y1) / 2)) {
				startx = x1;
				//printVLine(startx, 0);
				break;
			}
			colorVal = 0;
		}
		colorVal = 0;

		for (int x1 = x; x1 < width; x1 += 1) {
			for (int y = y1; y < y2; y++) {

				int maskRGB = getColorFilter(x1, y);
				currentColor = ImageHelper.fromRGB(maskRGB);
				int colortotal = currentColor[0] + currentColor[2];
				if (colortotal > 0) {
					colorVal++;
				}

			}
			if (colorVal < ((y2 - y1) / 4)) {
				endx = x1;
				//printVLine(endx, 0);
				break;
			}
			colorVal = 0;
		}
		findButton(startx, endx, y1, y2, color);
	}

	private void findButton(int x1, int x2, int y1, int y2, String Color) {
		int blackVal = 0;
		int offset = Math.abs(x1 - x2) / 4;
		int yOffset = 2 * Math.abs(y1 - y2) / 3;
		int[] currentColor = new int[3];
		int blackxVal = 0;
		int blackyVal = 0;

		for (int x = (x1 + offset); x < (x2 - offset); x++) {
			for (int y = (y1 + yOffset); y < y2; y++) {
				int maskRGB = getColorFilter(x, y);
				currentColor = ImageHelper.fromRGB(maskRGB);
				int colortotal = currentColor[0] + currentColor[2];
				if (colortotal == 0) {
					blackxVal += x;
					blackyVal += y;
					blackVal++;
				}
			}
		}
		int buttonx = blackxVal / blackVal;
		int buttony = blackyVal / blackVal;
		System.out.println("The " + Color + " button  is at (" + buttonx + ", " + buttony);

		printVLine(buttonx, 1);
		printHLine(buttony, 1);
	}

	private void printVLine(int xVal, int color) {
		int greenval= 0;
		int redval = 0;
		int blueval = 0;
		if (color == 0) {

			greenval = 255;
			redval = 255;
			blueval = 255;
		}
		if (color == 1) {

			greenval = 255;
			redval = 0;
			blueval = 0;
		}
		for (int z = 0; z < height; z++) {
			this.setRGB(xVal, z, ImageHelper.toRGB(redval, greenval, blueval));
		}
	}

	private void printHLine(int yVal, int color) {
		int greenval= 0;
		int redval = 0;
		int blueval = 0;
		if (color == 0) {

			greenval = 255;
			redval = 255;
			blueval = 255;
		}
		if (color == 1) {

			greenval = 255;
			redval = 0;
			blueval = 0;
		}
		for (int z = 0; z < width; z++) {
			this.setRGB(z, yVal, ImageHelper.toRGB(redval, greenval, blueval));

		}
	}

	
	
	private void performMask() {
		int cConcentration = 0;
		int[] coloryValues = new int[height];
		int largest = 0;
		int largestY = 0;
		int average1st = 0;
		int average2nd = 0;
		int counter1 = 0;
		int counter2 = 0;
		int lastcolor[] = new int[3];
		int lastcolortotal = 0;
		int currentcolortotal = 0;
		int currentcolor[] = new int[3];
		int xAvg = 0;
		int firstx = 0;
		int firsty = 0;
		int secondy = 0;
		boolean detected = false;
		boolean firstTime = true;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int maskRGB = getMaskColor(x, y);
				// int maskRGB = getColorFilter(x,y);
				this.setRGB(x, y, maskRGB);
				currentcolor = ImageHelper.fromRGB(maskRGB);
				currentcolortotal = currentcolor[0] - currentcolor[2];
				if (Math.abs(lastcolortotal - currentcolortotal) > 60) {
					if (firstTime == true) {

						firsty = y;
						firstTime = false;
					}
					if ((Math.abs(y - firsty) > 60)) {

						firstx = x;

						if (Math.abs(x - firstx) < 10) {

							detected = true;
							xAvg = xAvg + x;

						}
					}

				}

				lastcolortotal = currentcolortotal;
				int[] cConcentrationV = ImageHelper.fromRGB(maskRGB);
				cConcentration = cConcentrationV[1] + cConcentrationV[0] + cConcentrationV[2] + cConcentration;

			}
			coloryValues[y] = cConcentration;
			cConcentration = 0;
			if (detected) {
				counter1++;
			}
		}
		for (int z = 0; z < counter1; z++) {
			// this.setRGB((xAvg/counter1), (secondy + z),
			// ImageHelper.toRGB(0,255,0));
		}
		System.out.println(firsty);
		System.out.println(xAvg);
		System.out.println(counter1);

		// findBeacon();

	}

	

	/**
	 * Apply the Mask over a pixel with top-left corner at (x,y)
	 * 
	 * @param x
	 *            x coordinate of srcImage to start with
	 * @param y
	 *            y coordinate of srcImage to start with
	 * @return resulting RBG value of applying the mask
	 */
	private int getColorFilter(int x, int y) {
		int oldcolors[] = ImageHelper.fromRGB(srcImage.getRGB(x, y));
		int[] newColors = { 0, 0, 0 };
		for (int x1 = 0; x1 <= 2; x1++) {
			if ((oldcolors[0] + oldcolors[1] + oldcolors[2] > 400) && (oldcolors[0] + oldcolors[1] + oldcolors[2] < 600)
					&& x1 == 0) {
				// newColors[1] = 100;
				// newColors[2] = 120;
				// newColors[0] = 100;
			}
			if (x1 == 1) {
				continue;
			}
			if (x1 == 0) {

			}
			if ((oldcolors[0] + oldcolors[1] + oldcolors[2] > 440) && oldcolors[x1] > 200
					&& oldcolors[x1] > 50 + .5 * ((oldcolors[1] + oldcolors[0] + oldcolors[2]) - (oldcolors[x1]))) {
				newColors[1] = 0;
				newColors[2] = 0;
				newColors[0] = 0;
				newColors[x1] = 255;
			} else {
				// newColors[x1] = 0;
			}

			if (x1 == 0 && oldcolors[1] > oldcolors[2] && oldcolors[0] < (oldcolors[1] + 60)) {
				newColors[1] = 0;
				newColors[2] = 0;
				newColors[0] = 0;

			}

		}
		if ((oldcolors[0] + oldcolors[1] + oldcolors[2]) > 700) {
			newColors[1] = 0;
			newColors[2] = 0;
			newColors[0] = 0;
		}

		if (newColors[0] > newColors[2]) {
			red += 1;
		}
		if (newColors[0] < newColors[2]) {
			blue += 1;
		}
		return ImageHelper.toRGB(newColors[0], newColors[1], newColors[2]);
	}

	private int getMaskColor(int x, int y) {
		int averageColor = 0;
		int weight = 0;
		int[] newColors = new int[3];

		/*
		 * TODO 4 starting loop through the mask matrix, for index [i,j],
		 * multiple the value by the color in [x+i,y+j] and total up the values
		 * http://dasl.mem.drexel.edu/alumni/bGreen/www.pages.drexel.edu/_weg22/
		 * maskdes.jpg be sure to account for edge cases the resulting mask
		 * value will not be always be in the range 0-255, you need to normalize
		 * it. normalize it by dividing the value by the total of the values in
		 * the matrix (add all the numbers together) ex [[1,2,3]] would have a
		 * total value of 1+2+3 = 6. use the imageHelper class to convert colors
		 * to/from RBG value
		 */
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[0].length; j++) {
				int maskValue = mask[i][j];
				int picturePosx = x + i;
				int picturePosy = y + j;
				if (picturePosx >= width || picturePosy >= height) {
					continue;
				}
				int oldColor = srcImage.getRGB(picturePosx, picturePosy);
				int oldColors[] = ImageHelper.fromRGB(oldColor);

				int grayColor = ImageHelper.getGrayValue(oldColor);
				newColors[0] += oldColors[0] * maskValue;
				newColors[1] += oldColors[1] * maskValue;
				newColors[2] += oldColors[2] * maskValue;

				weight += Math.abs(maskValue);
			}
		}
		int newGray = Math.abs(averageColor / weight);
		// return newGray;
		return ImageHelper.toRGB(newColors[0] / weight, newColors[1] / weight, newColors[2] / weight);
	}
}
