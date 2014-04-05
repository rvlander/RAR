/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.tracking;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import rar.imageinput.ImageGetter;
import rar.imageinput.ImageGetterFactory;
import rar.utils.ImagePanel;
import rar.utils.Options;

/**
 *
 * @author rvlander
 */
public class ColorTrackerCalibrator extends JFrame implements ActionListener {

    private ImageGetter ig;
    private ColorTracker tracker;

    private ImagePanel cameraPanel;
    private ImagePanel treshPanel;
    private ImagePanel HSVPanel;

    private ArrayList<TrackedObject> objects;

    private int imageWidth;
    private int imageHeight;

    public ColorTrackerCalibrator() {

        imageWidth = Options.getImageWidth();
        imageHeight = Options.getImageHeight();

        tracker = new ColorTracker();
        ig = ImageGetterFactory.createImageGetter();
        objects = new ArrayList<>();

        Container p = this.getContentPane();
        p.setLayout(new GridLayout(0, 2));
        p.add(new ColorTrackerBars(tracker));

        cameraPanel = new ImagePanel() {

            @Override
            public void drawGraphics(Graphics2D g) {
                for (TrackedObject o : objects) {
                    g.setColor(Color.GREEN);
                    g.fillOval((int) Math.round(o.getX() / imageWidth * this.getWidth()) - 5,
                            (int) Math.round(o.getY() / imageHeight * this.getHeight()) - 5, 10, 10);
                }
            }
        };

        treshPanel = new ImagePanel();

        HSVPanel = new ImagePanel();

        p.add(cameraPanel);
        p.add(treshPanel);
        p.add(HSVPanel);

        this.setSize(1280, 720);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Timer(40, this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        objects = tracker.trackObjects(ig.getImage());

        cameraPanel.setImage(tracker.getCameraFeed());
        treshPanel.setImage(tracker.getThreshold());
        HSVPanel.setImage(tracker.getHSV());
    }

    public static void main(String args[]) {
        new ColorTrackerCalibrator();
    }

}
