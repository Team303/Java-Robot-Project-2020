/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Camera {
	public Camera() {

	}

	public static final class VelocitySetpoint {
		public static final int TRENCH_RUN = 4700;
		public static final int INITIATION_LINE_PP = 2000;
		public static final int INITIATION_LINE_RIGHT = 2000;
		public static final int INITIATION_LINE_LEFT = 2000;
	}

	public boolean turnToTarget(double threshold){
        double tX = Robot.limelight.getXOffset();
		return (Math.abs(Robot.drivebase.turnToAngle(0, -tX, threshold)) <= threshold);
	}

	public void scoreWithVision(boolean seekRight) {
		if (!Robot.limelight.hasValidContours()) {
			//SEEKING BEHAVIOR - WHEN VALID CONTOUR IS NOT FOUND
			if (seekRight) Robot.drivebase.drive(0.3, -0.3);
			else Robot.drivebase.drive(-0.3, 0.3);
		} else {
			//MAIN SCORING CODE!!!!!
			if (turnToTarget(2)) {
				Robot.shooter.useVisionSetpoint();
				Robot.shooter.runShooter();
			}
		}
	}

	public void seekBall(boolean seekRight) {
		if (Robot.axis.hasValidContours()) {
			double[] pow = driveStraight(0.3, getCameraDegreeOffset(), 0.05);
			Robot.drivebase.drive(pow[0], pow[1]);
		} else {
			if (seekRight) Robot.drivebase.drive(-0.3, 0.3);
			else Robot.drivebase.drive(0.3, -0.3);
		}
	}

	static final double pixelPerDegreeConstant = 0.146875;

	public static double getCameraDegreeOffset() {
		double centerXIdeal = Axis.cameraResX / 2;
		double centerXCurrent = Robot.axis.getCenterX();
		double centerXOffset = centerXIdeal-centerXCurrent;
		
		return centerXOffset*pixelPerDegreeConstant;
	}

    public static double[] driveStraight(double powSetpoint, double angleDifference, double tuningConstant) {                                                                                                                      //memes
		return new double[] {(powSetpoint + (angleDifference*tuningConstant)), (powSetpoint - (angleDifference*tuningConstant))};
	}



	

}
