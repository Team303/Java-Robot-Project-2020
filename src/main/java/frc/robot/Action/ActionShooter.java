/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Limelight;
import frc.robot.Robot;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ActionShooter implements Action{

    double timeout; 
    Timer t;
    boolean limelight = false;

    public ActionShooter(boolean limelight, double setpoint, double timeout){
        t = new Timer();
        t.start();
        this.timeout = timeout;
        this.limelight = limelight;
        Robot.shooter.setSetpoint(setpoint);
    }

    

    @Override
    public void run() {
        if (limelight) Robot.shooter.useVisionSetpoint();
        Robot.shooter.shooterPIDControl();
    }


    @Override
    public boolean isFinished() {
        boolean end = t.get() >= timeout;

        if(end){
            t.stop();
            t.reset();
        }

        return end;
    }
}

