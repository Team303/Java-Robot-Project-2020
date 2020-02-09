/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;




import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	// instance variables

	 CANSparkMax shooter;
	 CANEncoder encoder;
	 CANPIDController shooterPIDController;
	
	
	 double setPoint;
	
	 static final double maxFeedError = 0.15; //.15
	
	 /**
	  * Constructor establishes the necessary shooting instructions.
	  */
	 public Shooter() {
		shooter = new CANSparkMax(RobotMap.SHOOTER, CANSparkMaxLowLevel.MotorType.kBrushless);

		encoder = new CANEncoder(shooter);

		shooterPIDController = new CANPIDController(shooter);
		setPIDF(0.3, 0.000003, 1, 0.02475); //  
		//use only if needed
		//shooter.getInverted();
		shooter.set(0);
	
	}
	
	/**
	 * Runs continually to regulate the behavior of the shooter.
	 */
	public void control() {
		
		
	
		if(OI.xBtnY) { //set setpoint
			setPoint = 0;
			
		} else if(OI.xBtnX) {
			
			setPoint = 20250; // was -26150
		}
		//getVelocity(setpoint);
		
		
		shooterPIDController.setReference (setPoint, ControlType.kVelocity);	
	}
	
	/**
	 * Sets the setpoint of the shooter.
	 */
	public void setSetPoint(double target){
		setPoint = target;
	}

		/**
		 * Returns the velocity of the encoder.
		 */
	public double getVelocity() {
		return encoder.getVelocity() * RobotMap.kEncoderConstant * 10;
	}
		

	
	
	
	/*public double getSpeed() {
		return shooter.get();
	}
	
	/*public void resetI() {
		shooter.clearIAccum();
	}*/
	
	/**
	 * Sets the PID values for the robot encoder.
	 */
	public void setPIDF( double P, double I, double D, double F) {
		shooterPIDController.setP(P);
		shooterPIDController.setI(I);
		shooterPIDController.setD(D);
		shooterPIDController.setFF(F);
	}
	
}
