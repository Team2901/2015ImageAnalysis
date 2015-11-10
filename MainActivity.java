package com.example.mtoebes.cameraopencv;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import android.content.Intent;
/**
 * Activity that is started when the app is first created
 * display 2 buttons, one to start the camera, one to open the gallery
 */
public class MainActivity extends Activity {

    static { System.loadLibrary("opencv_java3"); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
                mLoaderCallback);
    }

    /**
     * onClick event that is called when the button with id "camera_button" is pressed
     * starts CameraActivity
     * @param view
     */
    public void openCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);

        //TODO: start the CameraActivity

    }

    /**
     * onClick event that is called when the button with id "gallery_button" is pressed
     * starts GalleryActivity
     * @param view
     */
    public void openGallery(View view) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

        private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if(LoaderCallbackInterface.SUCCESS != status) {
                super.onManagerConnected(status);
            }
        }
    };

}

