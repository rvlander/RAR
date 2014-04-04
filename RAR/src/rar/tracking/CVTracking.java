/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.tracking;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import rar.imageinput.ImageGetter;
import rar.imageinput.ImageGetterFactory;

/**
 *
 * @author rvlander
 */
public class CVTracking extends JPanel {

    static {
        System.loadLibrary("opencv_java248");
    }

    int H_MIN = 0;
    int H_MAX = 256;
    int S_MIN = 0;
    int S_MAX = 256;
    int V_MIN = 0;
    int V_MAX = 256;
//default capture width and height
    int FRAME_WIDTH = 1280;
    int FRAME_HEIGHT = 720;
//max number of objects to be detected in frame
    int MAX_NUM_OBJECTS = 50;
//minimum and maximum object area
    int MIN_OBJECT_AREA = 20 * 20;
    int MAX_OBJECT_AREA = (int) (FRAME_HEIGHT * FRAME_WIDTH / 1.5);
//names that will appear at the top of each window
    String windowName = "Original Image";
    String windowName1 = "HSV Image";
    String windowName2 = "Thresholded Image";
    String windowName3 = "After Morphological Operations";
    String trackbarWindowName = "Trackbars";

    void createTrackbars() {
        //create window for trackbars

        JFrame f = new JFrame(trackbarWindowName);
        Trackbars tbars = new Trackbars(this);
        f.getContentPane().add(tbars);
        f.pack();
        f.setVisible(true);

    }

    void drawObject(int x, int y, Mat frame) {
	//use some of the openCV drawing functions to draw crosshairs
        //on your tracked image!

        //UPDATE:JUNE 18TH, 2013
        //added 'if' and 'else' statements to prevent
        //memory errors from writing off the screen (ie. (-25,-25) is not within the window!)
        Core.circle(frame, new Point(x, y), 20, new Scalar(0, 255, 0), 2);
        if (y - 25 > 0) {
            Core.line(frame, new Point(x, y), new Point(x, y - 25), new Scalar(0, 255, 0), 2);
        } else {
            Core.line(frame, new Point(x, y), new Point(x, 0), new Scalar(0, 255, 0), 2);
        }
        if (y + 25 < FRAME_HEIGHT) {
            Core.line(frame, new Point(x, y), new Point(x, y + 25), new Scalar(0, 255, 0), 2);
        } else {
            Core.line(frame, new Point(x, y), new Point(x, FRAME_HEIGHT), new Scalar(0, 255, 0), 2);
        }
        if (x - 25 > 0) {
            Core.line(frame, new Point(x, y), new Point(x - 25, y), new Scalar(0, 255, 0), 2);
        } else {
            Core.line(frame, new Point(x, y), new Point(0, y), new Scalar(0, 255, 0), 2);
        }
        if (x + 25 < FRAME_WIDTH) {
            Core.line(frame, new Point(x, y), new Point(x + 25, y), new Scalar(0, 255, 0), 2);
        } else {
            Core.line(frame, new Point(x, y), new Point(FRAME_WIDTH, y), new Scalar(0, 255, 0), 2);
        }

        Core.putText(frame, x + "," + y, new Point(x, y + 30), 1, 1, new Scalar(0, 255, 0), 2);

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

    void trackFilteredObject(int x, int y, Mat threshold, Mat cameraFeed) {

        Mat temp = new Mat();
        threshold.copyTo(temp);
        //these two vectors needed for output of findContours
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        //find contours of filtered image using openCV findContours function
        Imgproc.findContours(temp, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
        //use moments method to find our filtered object
        double refArea = 0;
        boolean objectFound = false;
        if (hierarchy.width() > 0) {
            int numObjects = (int) hierarchy.width();
            //if number of objects greater than MAX_NUM_OBJECTS we have a noisy filter
            if (numObjects < MAX_NUM_OBJECTS) {
                for (int index = 0; index >= 0; index = (int)hierarchy.get(0, index)[0]) {

                    Moments moment = Imgproc.moments(contours.get(index));

                    double area = moment.get_m00();

                    //if the area is less than 20 px by 20px then it is probably just noise
                    //if the area is the same as the 3/2 of the image size, probably just a bad filter
                    //we only want the object with the largest area so we safe a reference area each
                    //iteration and compare it to the area in the next iteration.
                    if (area > MIN_OBJECT_AREA && area < MAX_OBJECT_AREA && area > refArea) {
                        x = (int) (moment.get_m10() / area);
                        y = (int) (moment.get_m01() / area);
                        objectFound = true;
                        refArea = area;
                    } else {
                        objectFound = false; 
                   }

                }
                //let user know you found an object
                if (objectFound == true) {
                    Core.putText(cameraFeed, "Tracking Object", new Point(0, 50), 2, 1, new Scalar(0, 255, 0), 2);
                    //draw object location on screen
                    drawObject(x, y, cameraFeed);
                }

            } else {
                Core.putText(cameraFeed, "TOO MUCH NOISE! ADJUST FILTER", new Point(0, 50), 1, 2, new Scalar(0, 0, 255), 2);
            }
        }
    }

    public static void main(String args[]) {
        //some boolean variables for different functionality within this
        //program

        CVTracking cvt = new CVTracking();
        ImageGetter imget = ImageGetterFactory.createImageGetter();

        boolean trackObjects =true;
        boolean useMorphOps = true;
        //Matrix to store each frame of the webcam feed
        Mat cameraFeed = new Mat();
        //matrix storage for HSV image
        Mat HSV = new Mat();
        //matrix storage for binary threshold image
        Mat threshold = new Mat();
        //x and y values for the location of the object
        int x = 0, y = 0;
        //create slider bars for HSV filtering
        cvt.createTrackbars();
        //video capture object to acquire webcam feed

        Imshow im1 = new Imshow(cvt.windowName2);
        Imshow im2 = new Imshow(cvt.windowName);
        Imshow im3 = new Imshow(cvt.windowName1);

        while (true) {
            //store image to matrix
            cameraFeed = BufferedImage2Mat(imget.getImage());
            //convert frame from BGR to HSV colorspace
            Imgproc.cvtColor(cameraFeed, HSV, Imgproc.COLOR_BGR2HSV);
            //filter HSV image between values and store filtered image to
            //threshold matrix
            Core.inRange(HSV, new Scalar(cvt.H_MIN, cvt.S_MIN, cvt.V_MIN),
                    new Scalar(cvt.H_MAX, cvt.S_MAX, cvt.V_MAX), threshold);
            //perform morphological operations on thresholded image to eliminate noise
            //and emphasize the filtered object(s)
            if (useMorphOps) {
                cvt.morphOps(threshold);
            }
            //pass in thresholded frame to our object tracking function
            //this function will return the x and y coordinates of the
            //filtered object
            if (trackObjects) {
                cvt.trackFilteredObject(x, y, threshold, cameraFeed);
            }

            //show frames 
            im1.showImage(threshold);
            im2.showImage(cameraFeed);
            im3.showImage(HSV);

            try {
                //delay 30ms so that screen can refresh.
                //image will not appear without this waitKey() command
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(CVTracking.class.getName()).log(Level.SEVERE, null, ex);
            }
            cvt.repaint();
        }

    }

    public static Mat BufferedImage2Mat(BufferedImage image) {
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }

    public static BufferedImage toBufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;

    }
}
