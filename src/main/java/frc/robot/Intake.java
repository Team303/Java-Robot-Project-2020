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

/**
 * Add your docs here.
 */
public class Intake {
    //making the varibles
   static WPI_TalonFX intakeMotor;
   static WPI_TalonFX conveyorBeltMotor;
   static TimeOfFlight motionSensor;



    public Intake(){
        // setting all the varibles
        intakeMotor = new WPI_TalonFX(RobotMap.INTAKE_ID);
        conveyorBeltMotor = new WPI_TalonFX(RobotMap.CONVEYOR_ID);
        motionSensor = new TimeOfFlight(RobotMap.MOTION_SENSOR_ID);

    }

    // takes all the metods and puts them in one for easy use
    public  void intakeControl() {
        
        runIntakeMotor();
        conveyorControl();

    }

    // checks to see if there is any objects in the way of the sensors 
    public boolean checkForObject() {
       double range = motionSensor.getRange();
       double distance = 100;
       return (range <= distance);

    }

    //using the checkForMotion method and if there is an object moving the conveyorbelt.
    public void conveyorControl() {

        if(checkForObject()){
            conveyorBeltMotor.set(ControlMode.PercentOutput, 0.6);
        }
        else {
            conveyorBeltMotor.set(ControlMode.PercentOutput, 0.0);
        }

    }    

    // runs the intake motor
    public void runIntakeMotor() {
        intakeMotor.set(ControlMode.PercentOutput, 0.25);

    }

}
