package ftc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class main {
    private static final String FILE_NAME_1 = "res/example1.jpg";
    private static final String FILE_NAME_2 = "res/example2.jpg";
    private static final String FILE_NAME_3= "res/example3.jpg";
    private static final String FILE_NAME_4= "res/Example4.jpg";
    private static final String FILE_NAME_5= "res/example5.jpg";
    private static final String FILE_NAME_6= "res/example6.large";
    private static final String FILE_NAME_7= "res/example7.large";
    private static final String FILE_NAME_8= "res/example8.jpg";
    private static final String FILE_NAME_9= "res/example9.jpg";
    private static final String FILE_NAME_11= "res/example11.jpg";
    private static final String FILE_NAME_12= "res/example12.jpg";
    private static final String FILE_NAME_13= "res/example13.jpg";
    private static final String FILE_NAME_14= "res/example14.jpg";
    private static final String FILE_NAME_15= "res/example15.jpg";
    private static final String FILE_NAME_16= "res/example16.jpeg";
    private static final String FILE_NAME_17= "res/example161._BLUR";
    private static final String FILE_NAME_18= "res/example18_BLUR.png";
    private static final String FILE_NAME_19= "res/example19.jpg";
    private static final String FILE_NAME_20= "res/example20_BLUR1.png";
    private static final String FILE_NAME_21= "res/example21.png";
    private static final String FILE_NAME_22= "res/example22.png";
    private static final String FILE_NAME_23= "res/example23_BLUR";
    private static final String FILE_NAME_24= "res/example24.png";
    private static final String FILE_NAME_25= "res/example25.jpg";
    private static final String FILE_NAME_26= "res/example26.png";
    private static final String FILE_NAME_27= "res/example27.png";
    private static final String FILE_NAME_28= "res/example28.png";
    private static final String FILE_NAME_29= "res/example29.png";



    private static String FILE_NAME = FILE_NAME_16;

    public static void main(String[] args) {
        /*
         * TODO
         * there are 4 tasks that need to be completed to make this work
         * start in ImageHelper, then RBGImage, then MaskImage
         */
        BufferedImage img = null;
        try {
            File file = new File(FILE_NAME);
            img = ImageIO.read(file);

            // create frame
            ImageFrame myFrame = new ImageFrame();

            // create Imagess
            BufferedImage redImage = new RGBImage(img, RGBImage.RED_MASK, true);
            BufferedImage greenImage = new RGBImage(img, RGBImage.GREEN_MASK, false);
            BufferedImage blueImage = new RGBImage(img, RGBImage.BLUE_MASK, false);
            BufferedImage original = new RGBImage(img, RGBImage.NO_MASK, false);
            BufferedImage grayImage = new RGBImage(img, RGBImage.NO_MASK, true);

            //BufferedImage sobelV = new SobelImage(grayImage, false);
            //BufferedImage sobelH = new SobelImage(grayImage, true);
            int[][] myMask = {{9,8,7,6,5,4,3,2,1},{9,8,7,6,5,4,3,2,1},{9,8,7,6,5,4,3,2,1}} ;
            BufferedImage blurImage = new MaskImage( original, myMask);
            // add images to frame
            myFrame.addImage(0, 0, original);
           myFrame.addImage(0, 1, blurImage);
            //myFrame.addImage(0, 1, sobelH);
           //myFrame.addImage(1, 1, sobelV);

            // save resulting images to file
            String base = FILE_NAME.substring(0, FILE_NAME.length()-4);
            ImageIO.write(blurImage, "png", new File(base+"_BLUR"));
            ImageIO.write(greenImage, "png", new File(base+"_green"));
            ImageIO.write(blueImage, "png", new File(base+"_blue"));
            //ImageIO.write(sobelH, "png", new File(base+"_sobelH"));
            //ImageIO.write(sobelV, "png", new File(base+"_sobelV"));
         
            	MaskImage.getColor();
            
        } catch (IOException e) {
            System.out.println("failed to open");
        }
    }
}
