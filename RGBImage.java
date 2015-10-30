package ftc;

import java.awt.image.BufferedImage;

public class RGBImage extends BufferedImage{
    static final int RED_MASK =   0xFFFF0000;
    static final int GREEN_MASK = 0xFF00FF00;
    static final int BLUE_MASK =  0xFF0000FF;
    static final int NO_MASK = 0xFFFFFFFF;

    private BufferedImage srcImage;
    int height;
    int width;
    int maskRGB;
    int[] maskColor;
    boolean isGrayScale;

    public RGBImage(BufferedImage srcImage) {
        super(srcImage.getWidth(), srcImage.getHeight(), srcImage.getType());
        this.srcImage = srcImage;
        this.height = srcImage.getHeight();
        this.width = srcImage.getWidth();
    }

    /**
     *
     * @param srcImage BufferedImage to detect colors of
     * @param mask RBG value to filler (FF for keep, 00 to ignore)
     * @param isGrayScale if image should be converted to grayscale after mask is applied
     */
    public RGBImage(BufferedImage srcImage, int mask, boolean isGrayScale) {
        this(srcImage);
        setMask(mask, isGrayScale);
    }

    public void setMask(int mask, boolean isGrayScale) {
        this.maskRGB = mask;
        this.maskColor = ImageHelper.fromRGB(mask);
        this.isGrayScale = isGrayScale;
        performMask();
    }

    private void performMask() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int maskRGB = getMaskColor(x,y);
                this.setRGB(x, y, maskRGB);
            }
        }
    }

    private int getMaskColor(int x, int y) {
        /*
         * TODO 3
         * get the color at [x,y] and use bitwise operations to filter out the unwanted colors
         * for example if maskRBG is RED_MASK (0xFFFF0000) should remove the green and blue values of the color
         * if using grayscale, convert the color to grayscale prior to returning it.
         */
        int sourceColor = srcImage.getRGB(x, y);
        int resultColor = sourceColor & maskRGB;
        if (isGrayScale)
        {  
        	return ImageHelper.toGrayScale(resultColor);
        	//int grayColors[] = ImageHelper.fromRGB(resultColor);
        	//int color = grayColors[1] + grayColors[0] + grayColors[2];
        	//return ImageHelper.toRGB(color, color, color);
        }
        return resultColor;
    }
}