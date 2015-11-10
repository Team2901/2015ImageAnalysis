package com.example.mtoebes.cameraopencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class used to perform various feature detections
 * You will be replying heavily on openCV class ImgProc
 * see http://docs.opencv.org/java/org/opencv/imgproc/Imgproc.html
 */
public class MatFilter {
    private static final String TAG = "MatHelper";

    private static int DEPTH = CvType.CV_8U;

    static double D_RHO = 1;
    static double D_THETA = Math.PI/180;

    static int gaussian_ksize = 5;

    static int canny_lowerThreshold = 100;
    static int canny_upperThreshold = 200;

    static int hough_threshold = 80;
    static int hough_minLinLength = 30;
    static int hough_maxLineGap = 10;

    /**
     * gets the grayscale mat resulting from the given mat
     * see http://docs.opencv.org/java/org/opencv/imgproc/Imgproc.html#cvtColor%28org.opencv.core.Mat,%20org.opencv.core.Mat,%20int%29
     * @param srcMat a RGB Mat
     * @return grayscale of srcMat
     */
    public static Mat getGrayScale(Mat srcMat) {
        Mat resMat = new Mat();
        Imgproc.cvtColor(srcMat, resMat, Imgproc.COLOR_BGRA2GRAY);
        //TODO convert SrcMat to be grayscale (see Imgproc.cvtColor and Imgproc.COLOR_BGRA2GRAY)
        return resMat;
    }

    /**
     * get the rbg mat result from the given mat
     * @param srcMat a grayscale mat
     * @return rbg of srcMat
     */
    public static Mat getRBG(Mat srcMat) {
        Mat resMat = new Mat();
        Imgproc.cvtColor(srcMat, resMat, Imgproc.COLOR_GRAY2BGRA);
        //TODO convert SrcMat to be rbg (see Imgproc.cvtColor and Imgproc.COLOR_GRAY2BGRA)
        return resMat;
    }

    /**
     * Performs a Gaussian blur
     * see http://docs.opencv.org/java/org/opencv/imgproc/Imgproc.html#GaussianBlur%28org.opencv.core.Mat,%20org.opencv.core.Mat,%20org.opencv.core.Size,%20double%29
     * @param srcMat a grayscale Mat
     * @param ksize blurring kernel size
     * @return GaussianBlur's output
     */
    public static Mat getGaussianBlur(Mat srcMat, int ksize) {
        Mat resMat = new Mat();
        Imgproc.GaussianBlur(srcMat, resMat, new Size(gaussian_ksize, gaussian_ksize), 0,0);
        return resMat;
    }

    /**
     * Performs a Canny edge detection
     * see http://docs.opencv.org/java/org/opencv/imgproc/Imgproc.html#Canny%28org.opencv.core.Mat,%20org.opencv.core.Mat,%20double,%20double%29
     * @param srcMat a grayscale Mat, it is recommened that it has been blurred to eleminate noise
     * @param lowerThreshold lower gradient cutoff
     * @param upperTheshold upper gradient cutoff
     * @return Canny's output
     */
   public static Mat getCanny(Mat srcMat, int lowerThreshold, int upperTheshold) {
        Mat resMat = new Mat();
        Imgproc.Canny(srcMat, resMat, lowerThreshold, upperTheshold, 0, false);
       //TODO perform filter
        return resMat;
    }

    /**
     * Performs a Sobel image filter
     * see http://docs.opencv.org/java/org/opencv/imgproc/Imgproc.html#Sobel%28org.opencv.core.Mat,%20org.opencv.core.Mat,%20int,%20int,%20int%29
     * @param srcMat a grayscale Mat, it is recommened that it has been blurred to eleminate noise
     * @param dx derivative order for x, recommend to use 0 or 1
     * @param dy derivative order for y, recommend to use 0 or 1
     * @return Sobel's output
     */
    public static Mat getSobel(Mat srcMat, int dx, int dy) {
        Mat resMat = new Mat();
        Imgproc.Sobel(srcMat, resMat, dx, dy, );
        return resMat;
    }

    /**
     * Performs a Laplacian image filter
     * see http://docs.opencv.org/java/org/opencv/imgproc/Imgproc.html#Laplacian%28org.opencv.core.Mat,%20org.opencv.core.Mat,%20int%29
     * @param srcMat a grayscale Mat, it is recommened that it has been blurred to eleminate noise
     * @return Laplacian's output
     */
    public static Mat getLaplacian(Mat srcMat) {
        Mat resMat = new Mat();
        Imgproc.Laplacian(srcMat, resMat, );
        //TODO perform filter
        return resMat;
    }

    /**
     * Performs a color mask on the given Mat to show only one channel(red,green,or blue) in grayscale
     * see http://docs.opencv.org/java/org/opencv/core/Core.html#split%28org.opencv.core.Mat,%20java.util.List%29
     * @param srcMat a RBG Mat
     * @param channel channel to mask to (0=red,1=green,2=blue)
     * @return mat of a single color channel taken from srcMat
     */
    public static Mat getChannel(Mat srcMat, int channel) {

        //TODO split srcMat into its 3 channels, return desired one
        return null;
    }

    /**
     * Performs a HoughLines transform and draws result on srcMat
     * see http://docs.opencv.org/doc/tutorials/imgproc/imgtrans/hough_lines/hough_lines.html?highlight=hough
     * @param srcMat a Mat of type CvType.CV_8U (single 8 bit channel) that is a result for a edge detection filter
     * @param threshold minumum points needed to form a line
     * @param minLinLength minimum length of a line
     * @param maxLineGap maximum gap allowed between points in a line
     * @return Mat with lines drawn on the input Mat srcMat
     */
    public static Mat getHoughMat(Mat srcMat, int threshold, int minLinLength, int maxLineGap) {
        Mat lines = getHoughLines(srcMat, threshold, minLinLength, maxLineGap);
        Mat resMat = drawHoughLines(srcMat, lines);
        return resMat;
    }

    /**
     * Performs HoughLinesP to find all lines in srcMat
     * see http://docs.opencv.org/java/org/opencv/imgproc/Imgproc.html#HoughLinesP%28org.opencv.core.Mat,%20org.opencv.core.Mat,%20double,%20double,%20int,%20double,%20double%29
     * @param srcMat a Mat of type CvType.CV_8U (single 8 bit channel) that is a result for a edge detection filter
     * @param threshold minumum points needed to form a line
     * @param minLinLength minimum length of a line
     * @param maxLineGap lines in srcMat
     * @return Mat of lines of form [x1,y1,x2,y2]
     */
    private static Mat getHoughLines(Mat srcMat, int threshold, int minLinLength, int maxLineGap) {
        Mat lines = new Mat();
        // TODO perform HoughLinesP
        return lines;
    }

    /**
     * Draws lines on srcMat
     * see http://docs.opencv.org/java/org/opencv/core/Core.html#line%28org.opencv.core.Mat,%20org.opencv.core.Point,%20org.opencv.core.Point,%20org.opencv.core.Scalar%29
     * @param srcMat a Mat of type CvType.CV_8U (single 8 bit channel)
     * @param lines a Mat of lines of the form [x1,y1,x2,y2]
     * @return Mat that is srcMat with the given lines drawn on
     */
    private static Mat drawHoughLines(Mat srcMat, Mat lines) {
        Mat resMat = null; // TODO set resMAt to be rbg of srcMat
        for(int index = 0; index < lines.rows(); index++) {
            double[] line = lines.get(index, 0);
            // TODO draw line on resMat (see Imgproc.line)
        }
        return resMat;
    }

    // methods using pre-set paramters
    public static Mat getGaussianBlur(Mat srcMat) {
        return getGaussianBlur(srcMat, gaussian_ksize);
    }

    public static Mat getCanny(Mat srcMat) {
        return getCanny(srcMat, canny_lowerThreshold, canny_upperThreshold);
    }

    public static Mat getHoughMat(Mat srcMat) {
        return getHoughMat(srcMat, hough_threshold, hough_minLinLength, hough_maxLineGap);
    }
}
