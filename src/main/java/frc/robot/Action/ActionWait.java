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
    double time;
    boolean firstRun;

    Timer t = new Timer();

    public ActionWait(double time){
        this.time = time;
        firstRun = false;
   
    }

    @Override
    public void run() {
        if(!firstRun){
            t.start();
            firstRun = true;
        }
        
    }

    @Override
    //checks if we ran long enough
    public boolean isFinished() {
        boolean end = t.get() >= time;

        if(end){
            t.stop();
            t.reset();
        }

        return end;
        

    }

}
