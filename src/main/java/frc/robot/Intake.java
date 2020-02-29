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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class Intake {

    public CANSparkMax intake;
    public CANSparkMax indexer;
    public TimeOfFlight motionSensor;
    public Solenoid deploy;
    public boolean intakeDown = false;


    public static final double INDEXER_INTAKE_SPEED = 0.3;
    public static final double INDEXER_SHOOTER_SPEED = 0.6;



    public Intake(){
        deploy = new Solenoid(RobotMap.INTAKE_SOLENOID);
        intake = new CANSparkMax(RobotMap.INTAKE, MotorType.kBrushless);
        intake.setInverted(RobotMap.INTAKE_INV);  
        motionSensor = new TimeOfFlight(RobotMap.MOTION_SENSOR);

        indexer = new CANSparkMax(RobotMap.INDEXER, MotorType.kBrushless);
        indexer.setInverted(RobotMap.INDEXER_INV);

        deploy(false);
        intakeDown = false;

    }


    public void control() {

        double power = SmartDashboard.getNumber("Indexer Shooter Power", 0.3);
        double intakePower = SmartDashboard.getNumber("Intake Power", 0.3);

        if (OI.lBtn[1]) {
            intake.set(intakePower);
        } else if (OI.rBtn[1]){
            intake.set(-intakePower);
        } else {
            intake.set(0);
            
        }

        if (OI.xLeftTrigger >= 0.75) {
            setIndexer(-power);
        } else if (OI.xRightTrigger >= 0.75) {
            setIndexer(power);
        } else {

            if (ballDetected() && intakeDown) {
                setIndexer(0.3);
            } else {
                setIndexer(0);
            }
        }

        if (OI.lBtn[4]) {
            deploy(false);
        } else if (OI.lBtn[6]) {
            deploy(true);

        }
    }

    public void setIndexer(double power) {
        indexer.set(power);
    }



    public boolean ballDetected() { 
        double minRange = SmartDashboard.getNumber("Indexer Min Range", 50)  ; 
        double maxRange = SmartDashboard.getNumber("Indexer Max Range", 200)  ; 

        boolean detected = (minRange <= motionSensor.getRange() && motionSensor.getRange() <= maxRange);
        SmartDashboard.putBoolean("Ball Detected", detected);
        return detected;
    }

	public void deploy(boolean state) {
        deploy.set(state);
        intakeDown = state;
	}

    /*
        if (power == 0 && Robot.shooter.shooterOverride) {
            //Do Nothing
        } else if (power == 0 && !Robot.shooter.shooterOverride){
            indexer.set(0);
        } else {
            indexer.set(power);
        }*/

}
