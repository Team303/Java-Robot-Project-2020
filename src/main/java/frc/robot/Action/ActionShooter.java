/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.OldRobot;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ActionShooter implements Action{
    //makes varibles that will be for time stuff
    double timeout; 
    Timer t;

    public ActionShooter(boolean limelight, double setpoint, double timeout){
        t.start();
        this.timeout = timeout;
        OldRobot.shooter.setSetpoint(setpoint);

    }

    @Override
    public void run() {
        OldRobot.shooter.runPID();
    }


    @Override
    public boolean isFinished() {
        boolean end = t.get() >= timeout;

        if(end){
            OldRobot.shooter.setSetpoint(0);
            t.stop();
            t.reset();
        }

        return end;
    }
}

