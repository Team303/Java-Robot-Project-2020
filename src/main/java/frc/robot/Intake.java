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
   public CANSparkMax intakeMotor;
   public CANSparkMax conveyorBeltMotor;
   public TimeOfFlight motionSensor;



    public Intake(){
        // setting all the varibles
        intakeMotor = new CANSparkMax(RobotMap.INTAKE, CANSparkMaxLowLevel.MotorType.kBrushless);
        conveyorBeltMotor = new CANSparkMax(RobotMap.CONVEYOR_BELT, CANSparkMaxLowLevel.MotorType.kBrushless);
        motionSensor = new TimeOfFlight(RobotMap.MOTION_SENSOR);

    }

    // takes all the metods and puts them in one for easy use
    public  void intakeControl() {
        
        if(OI.xRightTrigger > 0.7){
        runIntake();
        }
       

    }

    // checks to see if there is any objects in the way of the sensors 
    public boolean checkForObject() {
       double range = motionSensor.getRange();
       double distance = 100;
       return (range <= distance);

    }

    //set the conveyor speed to 0.6
    public void moveConveyor(){
        conveyorBeltMotor.set(0.6);
    }
    // sets the conveyor speed to 0.0
    public void stopConveyor(){
        conveyorBeltMotor.set(0.0);
    }


    //using the checkForMotion method and if there is an object moving the conveyorbelt.
    public void conveyorControl() {
        if(checkForObject()){
            moveConveyor();
        }
        else {
            stopConveyor();
        }
    }    
    

    // runs the intake motor while checking for objects at same time
    public void runIntake() {
        intakeMotor.set(0.25);
        conveyorControl();
    }

}
