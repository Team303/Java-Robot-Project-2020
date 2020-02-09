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
public class ActionShooter implements Action{
    //makes varibles that will be for time stuff
    double timeout; 
    Timer t;

    public ActionShooter(double givenSetpoint, double timeout){
         
        //sets the amount of time that we want to run
        this.timeout = timeout;
        //starts the timer
        t.start();
        // sets the setpoint to wherer we want to be shooting
        Robot.shooter.setSetPoint(givenSetpoint);

    }

    @Override
    public void run() {
        //shoots
        Robot.shooter.control();
    }


    @Override
    public boolean isFinished() {
         // checks if the timer has ran past our timeout   
        boolean end = t.get() >= timeout;

        // resets our setpoint and timer
        if(end){

            Robot.shooter.setSetPoint(0);
            t.stop();
            t.reset();
        }
        return end;
    }
}

