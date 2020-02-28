/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.Robot;

/**
 * Add your docs here.
 */

 //implement means that it need to use the methods that are in the Action class
public class ActionIntake implements Action {

    double timeout;
    double power;
    Timer timer = new Timer();

    public ActionIntake(double timeout){
        this(timeout, 0.5);   
    }

    public ActionIntake(double timeout, double power) {
        this.timeout = timeout;
        this.power = power;
        timer.start();
        
    }

    @Override
    //running the intake method
    public void run() {       
        Robot.intake.intake.set(power);
    }

    @Override
    
    public boolean isFinished() {

        //checking if the action has been running for the designated amount of time
        boolean end = timer.get() >= timeout;
        
        if(end){
            timer.stop();
            timer.reset();
        }
           
        
        return end;
    }
}
