package com.example.mtoebes.cameraopencv;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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
 * FilterMat is a object that performs different filters on an mat depending on the provided tag
 * the majority of the work will be done using functions within http://docs.opencv.org/java/3.0-rc1/org/opencv/imgproc/Imgproc.html
 * That api does not contain descriptions so you will need to reference these:
 * http://docs.opencv.org/2.4.9/modules/imgproc/doc/imgproc.html and http://docs.opencv.org/2.4.9/modules/imgproc/doc/feature_detection.html
 * You should read up on the filter prior to implementing them
 */
public class FilteredMat {
    private static final String TAG = "FilterMat";

    private static final String
            orignal_tag = "Original", gray_tag = "Gray Mask", color_tag = "Color Mask", blur_tag = "Gaussian",
            sobel_tag = "Sobel", laplacian_tag = "Laplacian", canny_tag = "Canny", hough_tag = "Hough";

    Mat mSrcMat; // Mat to use as the base to run filters on
    String mTag = orignal_tag; // string denoting desired filter to run

    public FilteredMat(Context context) {
        loadPreferences(context);
        mSrcMat = new Mat();
    }

    public Mat update(Mat srcMat) {
        mSrcMat = srcMat;
        return get();
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public Mat get() {
        return get(mTag);
    }

    public Mat get(String tag) {
        Mat resMat = new Mat();
        switch (tag) {
            case orignal_tag:
                return mSrcMat;
            case gray_tag:
                return getGrayScale(mSrcMat, resMat);
            case color_tag:
                return getChannel(mSrcMat, resMat);
            case blur_tag:
                return getGaussianBlur(get(gray_tag), resMat);
            case sobel_tag:
                return getSobel(get(blur_tag), resMat);
            case laplacian_tag:
                return getLaplacian(get(blur_tag), resMat);
            case canny_tag:
                return getCanny(get(blur_tag), resMat);
            case hough_tag:
                Mat houghSrcMat = get(hough_mode);
                return getHoughMat(houghSrcMat, resMat);
            default:
                return mSrcMat;
        }
    }

    // *** COLOR FILTERS *** //
     // gets the grayscale mat resulting from the given mat
    public Mat getGrayScale(Mat srcMat, Mat resMat) {
        Log.v(TAG, "getGrayScale");
        //TODO convert SrcMat to be grayscale (see Imgproc.cvtColor and Imgproc.COLOR_BGRA2GRAY)
        return resMat;
    }

    // get the rbg mat result from the given mat
    public static Mat getRBG(Mat srcMat, Mat resMat) {
        //TODO convert SrcMat to be rbg (see Imgproc.cvtColor and Imgproc.COLOR_GRAY2BGRA)
        return resMat;
    }

    private static int channel_num; // 0=red, 1=green, 2=blue, all else=gray

    // gets a single color channel out of the given 3-channel rgb mat
    public Mat getChannel(Mat srcMat, Mat resMat) {
        Log.v(TAG, "getChannel channel_num " + channel_num);
        if(0<= channel_num && channel_num < 3) {
            List<Mat> channels = new ArrayList<>(3);
            Core.split(srcMat, channels);
            resMat = channels.get(channel_num);
        } else {
            getGrayScale(srcMat, resMat);
        }
        return resMat;
    }

    // *** GAUSSIAN BLUR *** //
    private static int gaussian_ksize; // Gaussian kernel size

    public Mat getGaussianBlur(Mat srcMat, Mat resMat) {
        Log.v(TAG, "getGaussianBlur gaussian_ksize " + gaussian_ksize);
        //TODO perform gaussian blur
        return resMat;
    }

    // *** SOBEL FILTER *** //
    private static final int SOBEL_DEPTH = CvType.CV_8U; // the output image depth
    private static int sobel_ksize; // kernel size; it must be 1, 3, 5, or 7.
    private static int sobel_dx; // order of the derivative x. it must be 0 or 1
    private static int sobel_dy; // order of the derivative y. it must be 0 or 1

    public Mat getSobel(Mat srcMat, Mat resMat) {
        Log.v(TAG, "getSobel sobel_dx " + sobel_dx + " sobel_dy " + sobel_dy + " sobel_ksize " + sobel_ksize);
        //TODO perform sobel
        return resMat;
    }

    // *** LAPLACIAN FILTER *** //
    private static final int LAPLACIAN_DEPTH = CvType.CV_8U; // the output image depth
    private static int laplacian_ksize; // kernal size used to compute the second-derivative filters; it must be positive and odd

    public Mat getLaplacian(Mat srcMat, Mat resMat) {
        Log.v(TAG, "getLaplacian laplacian_ksize " + laplacian_ksize);
        //TODO perfrom lapliacian
        return resMat;
    }


    // *** CANNY FILTER *** //
    private static int canny_lowerThreshold; // lower gradient cutoff
    private static int canny_upperThreshold; // upper gradient cutoff

    public Mat getCanny(Mat srcMat, Mat resMat) {
        Log.v(TAG, "getCanny  canny_lowerThreshold " + canny_lowerThreshold + " canny_upperThreshold " + canny_upperThreshold);
        //TODO perform canny
        return resMat;
    }

    // *** HOUGH LINES *** //
    private static final double D_RHO = 1;
    private static final double D_THETA = Math.PI/90;

    private static String hough_mode; // which image filter to run this on (sobel, laplacian, or canny)
    private static int hough_threshold;    // minumum points needed to form a line
    private static int hough_minLinLength; // minimum length of a line
    private static int hough_maxLineGap;   // maximum gap allowed between points in a line

    public Mat getHoughMat(Mat srcMat, Mat resMat) {
        Log.v(TAG, "getHoughMat hough_threshold " + hough_threshold + " hough_minLinLength " + hough_minLinLength + " hough_maxLineGap " + hough_maxLineGap);
        getRBG(srcMat, resMat);
        Mat lines = getHoughLines(srcMat);
        drawHoughLines(resMat, lines);
        return resMat;
    }

    /**
     * Performs HoughLinesP to find all lines in srcMat
     * @return Mat of lines of form [x1,y1,x2,y2]
     */
    private static Mat getHoughLines(Mat srcMat) {
        Mat lines = new Mat();
        // TODO perform HoughLinesP
        return lines;
    }

    // Draws lines of form [x1,y1,x2,y2] on srcMat
    private static Mat drawHoughLines(Mat srcMat, Mat lines) {
        Mat resMat = null; // TODO set resMAt to be rbg of srcMat
        for(int index = 0; index < lines.rows(); index++) {
            double[] line = lines.get(index, 0);
            // TODO draw line on resMat (see Imgproc.line)
        }
        return resMat;
    }


    // Loads all the preferences for the filter options, should be called everytime a preference changes
    public static void loadPreferences(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        channel_num = Integer.parseInt(SP.getString("color_channel_pref", "0"));
        gaussian_ksize = Integer.parseInt(SP.getString("gaussian_ksize_pref", "5"));
        sobel_ksize = Integer.parseInt(SP.getString("sobel_ksize_pref", "5"));
        int sobel_dir = Integer.parseInt(SP.getString("sobel_dir_pref", "3"));
        sobel_dx = sobel_dir%2;
        sobel_dy = sobel_dir/2;
        laplacian_ksize = Integer.parseInt(SP.getString("laplacian_ksize_pref", "5"));
        canny_lowerThreshold = Integer.parseInt(SP.getString("canny_lower_thresh_pref", "100"));
        canny_upperThreshold = Integer.parseInt(SP.getString("canny_upper_thresh_pref", "200"));
        hough_threshold = Integer.parseInt(SP.getString("hough_threshold_pref", "80"));
        hough_minLinLength = Integer.parseInt(SP.getString("hough_min_len_pref", "10"));
        hough_maxLineGap = Integer.parseInt(SP.getString("hough_max_gap_pref", "30"));
        hough_mode = SP.getString("hough_detection_mode_pref", canny_tag);
    }
}
