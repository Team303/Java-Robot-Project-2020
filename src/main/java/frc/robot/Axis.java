package frc.robot;

import java.util.ArrayList;
import java.util.Collections;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.AxisCamera;

public class Axis {

	public Object imgLock = new Object();
	private Thread visionThread;
	private CellPipeline pipeline;
	private boolean runProcessing = false;
	private double centerX = 0.0;
	private double centerY = 0.0;
	private double rectangleArea=0.0;
	public static final int cameraResX = 320;
	public static final int cameraResY = 240;
	public int numContours = 0;
	
	public Axis() {
		enableVisionThread(); 
	}

	public void enableVisionThread() {
		pipeline = new CellPipeline();
		
		AxisCamera camera = CameraServer.getInstance().addAxisCamera("10.3.3.8");
		camera.setResolution(cameraResX, cameraResY);

		CvSink cvSink = CameraServer.getInstance().getVideo(); //capture mats from camera\
		CvSource outputStream = CameraServer.getInstance().putVideo("Stream", cameraResX, cameraResY); //send steam to CameraServer
		Mat mat = new Mat(); //define mat in order to reuse it

		runProcessing = true;

		visionThread = new Thread(() -> {

			while(!Thread.interrupted()) { //this should only be false when thread is disabled

				if(cvSink.grabFrame(mat)==0) { //fill mat with image from camera)
					outputStream.notifyError(cvSink.getError()); //send an error instead of the mat
					SmartDashboard.putString("Vision State", "Acquisition Error");
					continue; //skip to the next iteration of the thread
				}

				if(runProcessing) {		

					pipeline.process(mat); //process the mat (this does not change the mat, and has an internal output to pipeline)
					numContours = pipeline.filterContoursOutput().size();
					SmartDashboard.putNumber("Ball Contours Found", numContours);

					if (numContours >= 1) {

						Rect rect = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0)); 
					
						if(numContours > 1) {
							Rect rectTwo = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1)); //saw three+ contours, get the third contour
							Rect rectThree = Imgproc.boundingRect(pipeline.filterContoursOutput().get(2)); //saw three+ contours, get the third contour
						
							//initialize rectangle sorting ArrayList
							ArrayList<Rect> orderedRectangles= new ArrayList<Rect>();
							ContourAreaComparator areaComparator = new ContourAreaComparator();
							orderedRectangles.add(rect);
							orderedRectangles.add(rectTwo);
							orderedRectangles.add(rectThree);

							//sort the rectangles by area
							Collections.sort(orderedRectangles, areaComparator);
							
							rect = orderedRectangles.get(0);

						}

					
						//calculate center X and center Y pixels
						centerX = rect.x + (rect.width/2); //returns the center of the bounding rectangle
						centerY = rect.y + (rect.height/2); //returns the center of the bounding rectangle

						
						double width = rect.width;
						double height = rect.height;

						rectangleArea = width*height;
		
						//draws the rectangles onto the camera image sent to the dashboard
						Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 2); 

						SmartDashboard.putString("Vision State", "Executed overlay!");
						SmartDashboard.putNumber("Center X", centerX);
						outputStream.putFrame(mat); //give stream (and CameraServer) a new frame

				}

				} else {
					outputStream.putFrame(mat); //give stream (and CameraServer) a new frame
				}
			}
		});	


		visionThread.setDaemon(true);
		visionThread.start();
	}
    
    
    public boolean hasValidContours() {
        return (numContours >= 1);
	}
	
	public int getNumContours() {
		return numContours;
	}

	public double getArea(){
		return rectangleArea;
	}

	public double getCenterY() {
		return centerY;
	}

	public double getCenterX() {
		return centerY;
	}

	public void disableProcessing() {
		runProcessing = false;
	}

	public void enableProcessing() {
		runProcessing = true;
    }

}