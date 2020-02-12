/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ActionIntakeDeploy implements Action {

    boolean state;

    public ActionIntakeDeploy(){
        state = true;
    }

    public ActionIntakeDeploy(boolean state){
        this.state = state;
    }

    @Override
    public void run() {
        //Robot.intake.deploy(state);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
