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
    
    // makes all the variables to have values later
    double distance;
    double power;
    double timeout;
	Timer timer = new Timer();
	public double initalYaw = 0.0;
	public int initial = 0;

    boolean firstRun = false;

    Timer t = new Timer();


    public ActionDriveStraightByEncoders(double distance, double power, double timeout){
        //sets the distance we want to go
        this.distance = distance;
        //sets the power that we will travel at
        this.power = power;
        //sets the amount of time that we are willing to spend on this Action
        this.timeout = timeout;

    }

    @Override
    /**
     * Runs the driving straight by encoders using math.
     */
    public void run() {
        if(!firstRun){
            initalYaw = Robot.navX.getYaw();
			t.start();
			initial = Robot.drivebase.getLeftEncoder();
			firstRun = true;
        }
        //encoder math
        double[] pow = Action.driveStraight(power, Robot.navX.getYaw()-initalYaw, 0.005);
		Robot.drivebase.drive(pow[0], pow[1]);		
    }

    @Override
/**
 * Tests to see if the robot is finished with this task by doing math with the encoders.
 * If done, the robot will reset to not moving.
 */                
    public boolean isFinished() {
        
        if(t.get() > timeout) t.stop();

        boolean end = Math.abs(Robot.drivebase.getLeftEncoder()-initial)>=Math.abs(distance) || timer.get() >=timeout;
		if (end){
			Robot.drivebase.drive(0, 0);
		}

        return end;

    }
}
