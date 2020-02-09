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

/**
 * Add your docs here.
 */
public class Intake {
    //making the varibles
   public CANSparkMax intake;
   public CANSparkMax indexer;
   public TimeOfFlight motionSensor;



    public Intake(){
        // setting all the varibles
        intake = new CANSparkMax(RobotMap.INTAKE, CANSparkMaxLowLevel.MotorType.kBrushless);
        indexer = new CANSparkMax(RobotMap.INDEXER, CANSparkMaxLowLevel.MotorType.kBrushless);
        motionSensor = new TimeOfFlight(RobotMap.MOTION_SENSOR);
    }

    public  void control() {
        if (OI.lBtn[2]) {
            setIntake(0.7);
        } else {
            setIntake(0);
        }

        if (ballDetected()) {
            setIndexer(0.5);
        } else {
            setIndexer(0);
        }

    }


    public void setIntake(double power) {
        intake.set(power);
    }

    public void setIndexer(double power) {
        indexer.set(power);
    }

    public boolean ballDetected() {            
        double distance = motionSensor.getRange();
        double range = 100;
        return (distance <= range);
    }

}
