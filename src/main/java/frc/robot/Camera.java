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

    public static double[] driveStraight(double powSetpoint, double angleDifference, double tuningConstant) {                                                                                                                      //memes
		return new double[] {(powSetpoint + (angleDifference*tuningConstant)), (powSetpoint - (angleDifference*tuningConstant))};
	}


    public boolean turnToTarget(double threshold){
        double tX = OldRobot.limelight.getXOffset();
		return (Math.abs(OldRobot.drivebase.turnToAngle(0, -tX, threshold)) <= threshold);
	}


	public static final class ShooterPresets {
		public static final int TRENCH_RUN = 10000;
		public static final int INITIATION_LINE = 10000;

	}

}
