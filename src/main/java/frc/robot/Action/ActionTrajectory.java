/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.Command;


public class ActionTrajectory implements Action {

    String trajectoryName = "";
    int timeout = 15;
    Command commandToRun;
    boolean firstRun = true;

    public ActionTrajectory(String trajectoryName, int timeout) {
        this.trajectoryName = trajectoryName;
        this.timeout = timeout;
        
        firstRun = true;

    }


    public void run() {

        if (firstRun) {
                commandToRun = Robot.commands.trajectoryMap.get(trajectoryName);
                commandToRun.schedule();
            firstRun = false;
        }
        CommandScheduler.getInstance().run();        
    }

    public boolean isFinished() {

        if (commandToRun.isFinished()) {
            return true;
        } else {
            return false;
        }
    }


}
