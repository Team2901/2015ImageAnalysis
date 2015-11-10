package com.example.mtoebes.cameraopencv;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * ImageDisplayActivity uses EXTRA_FILE_PATH to open the Mat stored at that location
 * In order to allow the use to perform a variety of image processsing on it
 * info on spinners: http://developer.android.com/guide/topics/ui/controls/spinner.html
 * info on hashmaps: http://beginnersbook.com/2013/12/hashmap-in-java-with-example/
 */
public class ImageDisplayActivity extends Activity implements OnItemSelectedListener {
    private static final String TAG = "ViewActivity";

    // extra passed with intent so we know which file to open
    public static final String EXTRA_FILE_PATH = "extraFilePath";

    // List of tags we are expected to handle (should include all from @array/transforms)
    private static final String tag_default = "default", tag_red = "red", tag_green = "green", tag_blue = "blue",
            tag_gray = "gray", tag_sobel = "sobel", tag_sobel_x = "sobel_x", tag_sobel_y = "sobel_y",
            tag_laplacian = "laplacian", tag_gaussian = "gaussian", tag_canny = "canny", tag_hough = "hough";

    private File mFile; // File to get mSrcMat from
    private Mat mSrcMat; // Unaltered Mat to use as base
    private Bitmap mBitmap; // Bitmap use hold Mat's in View friendly form
    private ImageView mImage; // View to display mBitmap
    private Spinner mSpinner; // View of menu options
    private Map<String,Mat> mMats = new HashMap<>(); // mapping of tags to Mats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        // grab the extra denoting the filepath and use it to create a File
        Bundle extras = getIntent().getExtras();
        String filePath = extras.getString(EXTRA_FILE_PATH);
        //TODO grab filePath from intent Extra, create mFile with it (see https://androidcookbook.com/Recipe.seam?recipeId=809)

        mFile = new File(filePath);

        //TODO set mImage to the ImageView in the layout
        mImage = (ImageView) this.findViewById(R.id.imgage);
        // set up the spinner to listen for when an item is selected
        // spinner's items are set from @array/transforms
        //TODO set mSpinner to the Spinner in the layout
        mSpinner = (Spinner) findViewById(R.id.filter_spinner);
        mSpinner.setOnItemSelectedListener(this);
        //TODO set mSpinner's setOnItemSelectedListener to this class

        // get the Mat at mFile, create a bitmap from it, and set it as the display image
        //TODO open mFile and set mSrcMat to the Mat stored there
        mSrcMat = FileHelper.getMat(mFile);
        mBitmap = Bitmap.createBitmap(mSrcMat.cols(), mSrcMat.rows(), Bitmap.Config.ARGB_8888);
        //TODO create mBitmap as a Bitmap large enough to hold mSrcMat (use Bitmap.Config.ARGB_8888)
        setImage(mSrcMat);
    }

    /**
     * Sets mImage to display the given mat (by converting it to a bitmap first)
     * @param mat mat to display
     */
    protected void setImage(Mat mat) {
        //TODO convert mat into a bitmap (see Utils.matToBitmap)
        //TODO set mImage to show the bitmap (see ImageView.setImageBitmap())
    }

    /**
     * returns the Mat corresponding to the given tag
     * @param tag tag to use to find desired mat
     * @return mat corresponding to the tag
     */
    public Mat getMat(String tag) {
        // If we already created the mat, return it
        //TODO check to see if mMat's contains tag as a key, if so, return the value for that key

        // Else create it
        Mat resMat;
        switch(tag) {
            case tag_hough:
                resMat = MatFilter.getHoughMat(getMat(tag_canny)); break;
            case tag_canny:
                resMat = MatFilter.getCanny(getMat(tag_gaussian)); break;
            case tag_gaussian:
                resMat = MatFilter.getGaussianBlur(getMat(tag_gray)); break;
            case tag_laplacian:
                resMat = MatFilter.getLaplacian(getMat(tag_gaussian)); break;
            case tag_sobel:
                resMat = MatFilter.getSobel(getMat(tag_gaussian),1,1); break;
            case tag_sobel_x:
                resMat = MatFilter.getSobel(getMat(tag_gaussian),1,0); break;
            case tag_sobel_y:
                resMat = MatFilter.getSobel(getMat(tag_gaussian),0,1); break;
            case tag_red:
                resMat = MatFilter.getChannel(mSrcMat, 0); break;
            case tag_green:
                resMat = MatFilter.getChannel(mSrcMat, 1); break;
            case tag_blue:
                resMat = MatFilter.getChannel(mSrcMat, 2); break;
            case tag_gray:
                resMat = MatFilter.getGrayScale(mSrcMat); break;
            default:
                resMat = mSrcMat; break;
        }

        // add the created mat to the map so we remember it next time
        //TODO add key/value pair tag/resMat to mMats
        return resMat;
    }

    /**
     * Invoked when an item in the spinner has been selected, set the image to the corresponding mat
     * @param parent the spinner view
     * @param view the view that was selected
     * @param position the position of the view in the spinner
     * @param id the id of the view
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tag = (String) ((TextView) view).getText(); // get the text of the view
        Mat mat = getMat(tag); // get the corresponding mat
        setImage(mat);  // set the image to show mat
    }

    /**
     * Invoked when the selection disappears from the spinner (will not happen)
     * @param parent the spinner view
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) { /* do nothing */ }

}
