/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.playingwithfusion.CANVenom.*;
import com.playingwithfusion.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.Solenoid;


/**
 * Add your docs here.
 */
public class Intake {

    public CANSparkMax intake;
    public CANSparkMax indexer;
    public TimeOfFlight motionSensor;
    public Solenoid deploy;



    public Intake(){
        deploy = new Solenoid(RobotMap.INTAKE_PISTON);
        intake = new CANSparkMax(RobotMap.INTAKE, CANSparkMaxLowLevel.MotorType.kBrushless);
        indexer = new CANSparkMax(RobotMap.INDEXER, CANSparkMaxLowLevel.MotorType.kBrushless);
        motionSensor = new TimeOfFlight(RobotMap.MOTION_SENSOR);


    }


    public void control() {
        if (OI.lBtn[2]) {
            intakeControl(0.5);
        } else {
            intakeControl(0);
        }

    }

    public void intakeControl(double power) {

        intake.set(power);


        if (ballDetected()) {
            setIndexer(0.3);
        } else {
            setIndexer(0);
        }
        
    }

    public void setIndexer(double power) {

        if (power == 0 && Robot.shooter.shooterRunning) {
            //Do Nothing
        } else if (power == 0 && !Robot.shooter.shooterRunning){
            indexer.set(0);
        } else {
            indexer.set(power);
        }

    }

    public boolean ballDetected() {            
        double distance = motionSensor.getRange();
        double range = 100;
        return (distance <= range);
    }

	public void deploy(boolean state) {
        deploy.set(state);
	}

}
