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

public class ActionTurnToTarget {
   
    public void run() {
        //Actual method is run from isFinished method
    }

    public boolean isFinished() {
        return OldRobot.camera.turnToTarget(2);
    }

}
