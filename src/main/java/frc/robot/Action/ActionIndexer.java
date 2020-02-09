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
public class ActionIndexer implements Action {

    Timer timer = new Timer();
    double power = 0.0;
    double timeout;

    public ActionIndexer(double timeout){
        this(timeout, 0.5);
    }

    public ActionIndexer(double timeout, double power) {
        this.timeout = timeout;
        this.power = power;
        timer.start();
    }

    @Override
    public void run() {
        OldRobot.intake.setIndexer(power);

    }

    public boolean isFinished() {
        boolean end = timer.get() >= timeout;
        
        if(end){
            OldRobot.intake.setIndexer(0);
            timer.stop();
            timer.reset();
        }

        return end; 
    }
}
