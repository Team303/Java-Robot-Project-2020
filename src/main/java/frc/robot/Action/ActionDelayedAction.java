/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class ActionDelayedAction implements Action {


    Timer timer = new Timer();
    double time;
    boolean firstRun = true;
    Action action;

    public ActionDelayedAction(double time, Action action) {
        this.time = time;
        this.action = action;
    }

    public void run() {
        if (firstRun) {
            timer.start();
            firstRun = false;
        }

        if (timer.get() >= time) {
            action.run();
        }
        
    }


    public boolean isFinished() {
        return action.isFinished();
    }


}
