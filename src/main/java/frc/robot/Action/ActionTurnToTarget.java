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

public class ActionTurnToTarget implements Action {

    double threshold = 1;

    public ActionTurnToTarget(double threshold) {
        this.threshold = threshold;
    }
   
    public void run() {
    }

    public boolean isFinished() {
        return Robot.camera.turnToTarget(1);
    }

}
