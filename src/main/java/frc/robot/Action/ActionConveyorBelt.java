/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ActionConveyorBelt implements Action {

    //makes a variable for timer and how long we want our code to run
    Timer timer = new Timer();

    double timeout;

    //sets how long we want our code to run and starts the timer
    public ActionConveyorBelt(double timeout){
    
    this.timeout = timeout;
       
    timer.start();
        
         
       
    }

    @Override
    public void run() {
        //sets the conveyorbelt to move at a curten speed (0.6)
        Robot.intake.moveConveyor();

    }
/***

    @Override
    /**
     * Tests to see if the conveyor belt is finished moving by comparing to the time run
     */ 
    public boolean isFinished() {
        // checks if the timer has ran past our timeout 
        boolean end = timer.get() >= timeout;

        // resets our setpoint and timer
        if(end){
            Robot.intake.stopConveyor();

            timer.stop();
            timer.reset();
        }

        return end; 
    }
}
