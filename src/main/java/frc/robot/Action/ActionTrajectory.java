/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

/**
 * Add your docs here.
 */
public class ActionTrajectory {

    String trajectoryName = "";
    int timeout = 15;

    public ActionTrajectory(String trajectoryName, int timeout) {
        this.trajectoryName = trajectoryName;
        this.timeout = timeout;
    }

    public void run() {
        
    }

    public boolean isFinished() {

        return true;
    }


}