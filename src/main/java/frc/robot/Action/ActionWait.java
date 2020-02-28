/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class ActionWait implements Action {
    double timeout;
    Timer t;
    boolean firstRun;

    public ActionWait(double timeout){
        t = new Timer();
        this.timeout = timeout;
        firstRun = true;
    }

    @Override
    public void run() {
        if (firstRun) {
            t.start();
            firstRun = false;
        }


        
    }

    @Override
    //checks if we ran long enough
    public boolean isFinished() {

        boolean end = t.get() >= timeout;
        if (end) {
            firstRun = true;
            t.stop();
            t.reset();
        }


        return end;
        

    }

}
