/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import frc.robot.OldRobot;

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
        error = OldRobot.drivebase.turnToAngle(OldRobot.navX.getYaw(), angle, tolerance);
    }


    public boolean isFinished() {
        boolean end = (Math.abs(error) <= tolerance);
        return end;
    }



}
