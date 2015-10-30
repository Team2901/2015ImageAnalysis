package ftc;

import java.awt.image.BufferedImage;

public class SobelImage extends BufferedImage {
    // Mask for detecting vertical lines
    int[][] GX = {{-1,0,1},{-2,0,2},{-1,0,1}};

    // Mask for detecting horizontal lines
    int[][] GY = {{-1,-2,-1},{0,0,0},{1,2,1}};

    // Mask for simple 3pix blurring
    int[][] BLUR = {{1,1,1},{1,1,1},{1,1,1}};

    private BufferedImage grayImage;
    private BufferedImage sobelImage;
    private BufferedImage srcImage;

    /**
     *
     * @param srcImage BufferedImage to detect lines of
     * @param isVertical true if detecting vertical lines, else false
     */
    public SobelImage(BufferedImage srcImage, boolean isVertical) {
        super(srcImage.getWidth(), srcImage.getHeight(), srcImage.getType());
        this.srcImage = srcImage;
        grayImage = new RGBImage(srcImage, RGBImage.NO_MASK, true);
        if(isVertical)
            sobelImage = new MaskImage(grayImage, GX);
        else
            sobelImage = new MaskImage(grayImage, GY);
        this.setData(sobelImage.getRaster());
    }
 }

