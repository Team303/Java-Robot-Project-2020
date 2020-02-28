/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class ActionDriveStraightByPose implements Action {


    double intendedX = 0;
    double indendedY = 0;
    double power;
    boolean firstRun = false;
    double initalYaw;
    Timer t;
    double timeout;


    public ActionDriveStraightByPose(double intendedX, double indendedY, double power, double timeout) {
        t = new Timer();
        this.power = power;
        this.intendedX = intendedX;
        this.indendedY = indendedY;
        this.timeout = timeout;
    }

    public void run() {
        if(!firstRun){
            initalYaw = Robot.navX.getYaw();
			t.start();
			firstRun = true;
        }

        double yError = Robot.drivebase.getPoseY() - indendedY;


        double pow[] = new double[10];

        if (Math.abs(yError) >= 0.3) {
            pow = Action.driveStraight(power, Math.toDegrees(Math.atan(yError / 1.5)), -0.005);
        } else {
            pow = Action.driveStraight(power, Robot.navX.getYaw(), 0.005);
        }

        Robot.drivebase.drive(pow[0], pow[1]);



    }

    public static double[] driveStraight(double powSetpoint, double angleDifference, double tuningConstant) {             
		//just math to drive straight                                                                                                         //memes
		return new double[] {(powSetpoint - (angleDifference*tuningConstant)), (powSetpoint + (angleDifference*tuningConstant))};
	}

    public boolean isFinished() {
        return Robot.drivebase.getPoseX() >= intendedX;
    }


}
