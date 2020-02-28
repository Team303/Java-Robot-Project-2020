/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ActionTurnToAngle implements Action {

    double angle = 0;
    double tolerance = 5;
    double error = 0;

    public ActionTurnToAngle(double angle, double tolerance) {
        this.angle = angle;
        this.tolerance = angle;
    }


    public void run() {
        error = Robot.drivebase.turnToAngle(Robot.navX.getYaw(), 0, 2);
    }


    public boolean isFinished() {
        //boolean end = (Math.abs(error) <= tolerance);
        boolean end = Math.abs(Robot.navX.getYaw()) < 2;
        

        if (end) {
            Robot.drivebase.drive(0,0);
        }
        return end;
    }



}
