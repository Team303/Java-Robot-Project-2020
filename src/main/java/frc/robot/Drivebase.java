/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Add your docs here.
 */
public class Drivebase {

  WPI_TalonSRX leftFront;
  WPI_TalonSRX leftMidFront;
  WPI_TalonSRX leftMidBack;
  WPI_TalonSRX leftBack;

  WPI_TalonSRX rightFront;
  WPI_TalonSRX rightMidFront;
  WPI_TalonSRX rightMidBack;
  WPI_TalonSRX rightBack;

  SpeedControllerGroup leftMotors;
  SpeedControllerGroup leftFrontMotors;
  SpeedControllerGroup leftBackMotors;
  SpeedControllerGroup rightFrontMotors;
  SpeedControllerGroup rightBackMotors;
  SpeedControllerGroup rightMotors;

  DifferentialDrive drive;


  
 


  

    public Drivebase(){
      leftFront = new WPI_TalonSRX(RobotMap.LEFT_FRONT_MOTOR);
      leftMidFront = new WPI_TalonSRX(RobotMap.LEFT_MIDFRONT_MOTOR);
      leftMidFront = new WPI_TalonSRX(RobotMap.LEFT_MIDBACK_MOTOR);
      leftBack = new WPI_TalonSRX(RobotMap.LEFT_BACK_MOTOR);

      rightFront = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_MOTOR);
      rightMidFront = new WPI_TalonSRX(RobotMap.RIGHT_MIDFRONT_MOTOR);
      rightMidFront = new WPI_TalonSRX(RobotMap.RIGHT_MIDBACK_MOTOR);
      rightBack = new WPI_TalonSRX(RobotMap.RIGHT_BACK_MOTOR);
     
      leftFrontMotors = new SpeedControllerGroup(leftFront, leftMidFront, leftMidBack, leftBack);
      leftBackMotors = new SpeedControllerGroup(leftFront, leftMidFront, leftMidBack, leftBack);

      drive = new DifferentialDrive(leftMotors, rightMotors);

     
      
      
    }
    public void drive(double left, double right){

      drive.tankDrive(left, right);
    }    

    public double getLeftSpeed() {
      return leftFront.get();
    }

    public double getRightSpeed() {
      return rightFront.get();
    }

  

  
}
