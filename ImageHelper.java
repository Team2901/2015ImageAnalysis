package ftc;

/* Helper methods for encoding/decoding RGB values */
public class ImageHelper {

    /**
     * take a RBG int (ie 0xFFRRGGBB) and returns an array of the red, green,and blue values
     * @param rbg 32 bit int, split into 4 sections of 8 bits each (0-255) representing alpha,red,green,blue channels of a color
     * @return array of the red, green, and blue values of rbg
     */
    public static int[] fromRGB(int rgb) {
        /*
         * TODO Task 1
         * learn about HEX values http://www.binaryhexconverter.com/hex-to-decimal-converter
         * learn about RGB representation http://www.rapidtables.com/convert/color/how-rgb-to-hex.htm
         * learn about bitwise operations http://www.leepoint.net/data/expressions/bitops.html
         */
        int red = (rgb & 0x00FF0000) >> 16; // TODO fill in
        int green = (rgb & 0x0000FF00) >> 8; // isolate the green value and shift it over to the one's place
        int blue = (rgb & 0x000000FF); // TODO fill in

        int[] colors = {red,green,blue};
        return colors;
    }

    /**
     * takes a int for red, green, and blue values and returns a RBG with a 0xFF alpha channel
     * @param red value from 0-255
     * @param green value from 0-255
     * @param blue value from 0-255
     * @return a RBG int composed of the given values and a 0xFF alpha channel
     */
    public static int toRGB(int red, int green, int blue) {
        /* TODO Task 2
        * hash given red,green,blue values into a color. remember it needs to be of the form 0xFFRRGGBB
        */

        return 0xFF000000 |(red << 16) | (green << 8) | blue;
    }

    /**
     * takes a int for red, green, and blue values and returns the grayscale RGB
     * @param red value from 0-255
     * @param green value from 0-255
     * @param blue value from 0-255
     * @return a RBG int composed of the corresponding grayscale given values and a 0xFF alpha channel
     */
    public static int toGrayScale(int red, int green, int blue) {
        int gray = (int) ((0.299*red)  + (0.587 * green) + (0.114 * blue));
        return toRGB(gray, gray, gray);
    }
    
    public static int toGrayScale(int rgb) {
    	int[] colors = fromRGB(rgb);
    	return toGrayScale(colors[0], colors[1], colors[2]);
    }
    
    public static int getGrayValue(int rgb) {
    	int grayscale = toGrayScale(rgb);
    	return fromRGB(grayscale)[0];
    }
}
