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

 //implement means that it need to use the methods that are in the Action class
public class ActionIntake implements Action {

    // make the varibles
    double timeout;
    Timer timer = new Timer();

    //construtor setting the amout of time the action will run and stating timer
    public ActionIntake(double timeout){
        
        this.timeout = timeout;

        timer.start();

        
    }

    @Override
    //running the intake method
    public void run() {       
        Robot.intake.runIntake();
    }

    @Override
    
    public boolean isFinished() {

            //checking if the action has been runing for the designated amount of time
           boolean end = timer.get() >= timeout;
        
           //stoping Action class
           if(end){

            timer.stop();
            timer.reset();
        }
           
        
        return end;
    }
}
