/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ActionDriveStraightByEncoders implements Action {
    
    double distance;
    double power;
    double timeout;

	Timer timer = new Timer();
	public double initalYaw = 0.0;
	public int initial = 0;
    boolean firstRun = true;

    double tuningConstant = 0.005;



    public ActionDriveStraightByEncoders(double distance, double power, double timeout){
        this.distance = distance;
        this.power = power;
        this.timeout = timeout;

    }

    @Override
    public void run() {
        if(firstRun){
            initalYaw = 0;
			timer.start();
			initial = Robot.drivebase.getLeftEncoder();
			firstRun = false;
        }

        if (power > 0) {
            tuningConstant = -0.009;
        } else {
            tuningConstant = -0.009;
        }
        double[] pow = Action.driveStraight(power, Robot.navX.getYaw(), tuningConstant);
		Robot.drivebase.drive(pow[0], pow[1]);		
    }

    @Override
    /**
    * Tests to see if the robot is finished with this task by doing math with the encoders.
    * If done, the robot will reset to not moving.
    */                
    public boolean isFinished() {
        
        if(timer.get()>=timeout) timer.stop();
		return Math.abs(Robot.drivebase.getLeftEncoder()-initial) >= Math.abs(distance) || timer.get() >=timeout;

    }
}
