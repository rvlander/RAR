/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.tracking;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import rar.utils.Options;

/**
 *
 * @author rvlander
 */
public class ColorTracker implements Tracker {
    
    static {
        System.loadLibrary("opencv_java248");
    }
    
    private int HMin;
    private int HMax;
    private int SMin;
    private int SMax;
    private int VMin;
    private int VMax;
//default capture width and height
    private int FRAME_WIDTH;
    private int FRAME_HEIGHT;
//max number of objects to be detected in frame
    private int MAX_NUM_OBJECTS = 50;
//minimum and maximum object area
    private int MIN_OBJECT_AREA = 20 * 20;
    private int MAX_OBJECT_AREA;
    
    private Mat cameraFeed;
    private Mat HSV;
    //matrix storage for binary threshold image
    private Mat threshold;

    public ColorTracker() {
        HMin = Options.getHMin();
        HMax = Options.getHMax();
        SMin = Options.getSMin();
        SMax = Options.getSMax();
        VMin = Options.getVMin();
        VMax = Options.getVMax();
        
        FRAME_WIDTH = Options.getImageWidth();
        FRAME_HEIGHT = Options.getImageHeight();
        
        MAX_OBJECT_AREA = (int) (FRAME_HEIGHT * FRAME_WIDTH / 1.5);
        
    }

    void morphOps(Mat thresh) {

        //create structuring element that will be used to "dilate" and "erode" image.
        //the element chosen here is a 3px by 3px rectangle
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        //dilate with larger element so make sure object is nicely visible
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));

        Imgproc.erode(thresh, thresh, erodeElement);
        Imgproc.erode(thresh, thresh, erodeElement);

        Imgproc.dilate(thresh, thresh, dilateElement);
        Imgproc.dilate(thresh, thresh, dilateElement);

    }

    void trackFilteredObject(ArrayList<TrackedObject> objList, Mat threshold, Mat cameraFeed) {
              
        double x,y;
        double size=-1;
        Mat temp = new Mat();
        threshold.copyTo(temp);
        //these two vectors needed for output of findContours
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        //find contours of filtered image using openCV findContours function
        Imgproc.findContours(temp, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
        //use moments method to find our filtered object
        double refArea = 0;
        if (hierarchy.width() > 0) {
            int numObjects = (int) hierarchy.width();
            //if number of objects greater than MAX_NUM_OBJECTS we have a noisy filter
            if (numObjects < MAX_NUM_OBJECTS) {
                for (int index = 0; index >= 0; index = (int) hierarchy.get(0, index)[0]) {

                    Moments moment = Imgproc.moments(contours.get(index));

                    double area = moment.get_m00();

                    //if the area is less than 20 px by 20px then it is probably just noise
                    //if the area is the same as the 3/2 of the image size, probably just a bad filter
                    //we only want the object with the largest area so we safe a reference area each
                    //iteration and compare it to the area in the next iteration.
                    if (area > MIN_OBJECT_AREA && area < MAX_OBJECT_AREA && area > refArea) {
                        x = (moment.get_m10() / area);
                        y = (moment.get_m01() / area);
                        refArea = area;
                        objList.add(new TrackedObject(x,y,size));
                            //System.out.println("Object found!");
                    }
                }

            }
        }
    }

    @Override
    public ArrayList<TrackedObject> trackObjects(BufferedImage im) {
        
        ArrayList<TrackedObject> objList = new ArrayList<>();

        //Matrix to store each frame of the webcam feed
        cameraFeed = new Mat();
        //matrix storage for HSV image
        HSV = new Mat();
        //matrix storage for binary threshold image
        threshold = new Mat();
        //x and y values for the location of the object

        //store image to matrix
        cameraFeed = TrackerUtils.BufferedImage2Mat(im);
        //convert frame from BGR to HSV colorspace
        Imgproc.cvtColor(cameraFeed, HSV, Imgproc.COLOR_BGR2HSV);
            //filter HSV image between values and store filtered image to
        //threshold matrix
        Core.inRange(HSV, new Scalar(HMin, SMin, VMin),
                new Scalar(HMax, SMax, VMax), threshold);
            //perform morphological operations on thresholded image to eliminate noise
        //and emphasize the filtered object(s)

        morphOps(threshold);

        //pass in thresholded frame to our object tracking function
        //this function will return the x and y coordinates of the
        //filtered object
        trackFilteredObject(objList, threshold, cameraFeed);
        
        return objList;

    }

    public int getHMin() {
        return HMin;
    }

    public void setHMin(int HMin) {
        this.HMin = HMin;
    }

    public int getHMax() {
        return HMax;
    }

    public void setHMax(int HMax) {
        this.HMax = HMax;
    }

    public int getSMin() {
        return SMin;
    }

    public void setSMin(int SMin) {
        this.SMin = SMin;
    }

    public int getSMax() {
        return SMax;
    }

    public void setSMax(int SMax) {
        this.SMax = SMax;
    }

    public int getVMin() {
        return VMin;
    }

    public void setVMin(int VMin) {
        this.VMin = VMin;
    }

    public int getVMax() {
        return VMax;
    }

    public void setVMax(int VMax) {
        this.VMax = VMax;
    }

    public BufferedImage getCameraFeed() {
        return TrackerUtils.toBufferedImage(cameraFeed);
    }

    public BufferedImage getHSV() {
        return TrackerUtils.toBufferedImage(HSV);
    }

    public BufferedImage getThreshold() {
        return TrackerUtils.toBufferedImage(threshold);
    }
    
    public void  saveOptions(){
        Options.setHMin(HMin);
        Options.setHMax(HMax);
        Options.setSMin(SMin);
        Options.setSMax(SMax);
        Options.setVMin(VMin);
        Options.setVMax(VMax);
    }

    
}
