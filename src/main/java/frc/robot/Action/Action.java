/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;
//this class's methodes will be forced to be used by all other Action classes
public interface Action {
	public void run();
	public boolean isFinished();

	public static double[] driveStraight(double powSetpoint, double angleDifference, double tuningConstant) {             
		//just math to drive straight                                                                                                         //memes
		return new double[] {(powSetpoint + (angleDifference*tuningConstant)), (powSetpoint - (angleDifference*tuningConstant))};
	}
}