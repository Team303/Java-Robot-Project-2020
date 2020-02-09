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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANEncoder;
import com.revrobotics.ControlType;

public class Shooter {
	// instance variables

	CANSparkMax shooter;
	CANSparkMax shooterSlave;

	CANPIDController shooterPID;
	CANEncoder	shooterEncoder;
	int count = 0;

	double setpoint = 0;
	
	static final double maxFeedError = 0.15; //.15
	
	public Shooter() {
	 	shooter = new CANSparkMax(RobotMap.SHOOTER, MotorType.kBrushless);
		shooterSlave = new CANSparkMax(RobotMap.SHOOTER_SLAVE, MotorType.kBrushless);

		shooter.setInverted(RobotMap.SHOOTER_INV);
		shooterSlave.setInverted(RobotMap.SHOOTER_INV);

		shooterSlave.follow(shooter);

		shooter.restoreFactoryDefaults();
		shooterSlave.restoreFactoryDefaults();

		shooterPID = shooter.getPIDController();
		shooterEncoder = shooter.getEncoder();

		double kP = 0.3;
		double kI = 0.000003;
		double kD = 1;
		double kIz = 0;
		double kFF = 0.02475;
		double kMaxOutput = 1.0;
		double kMinOutput = -1.0;

		shooterPID.setP(kP);
		shooterPID.setI(kI);
		shooterPID.setD(kD);
		shooterPID.setFF(kFF);
		shooterPID.setOutputRange(kMinOutput, kMaxOutput);

		setpoint = 0;
	
	}
	
	/**
	 * Runs continually to regulate the behavior of the shooter.
	 */
	public void control() {

		if (OI.lBtn[5]) {
			setpoint = getSetpointFromDistance();
		} else if (OI.xBtnA){
			setpoint = 0;
		}

		shooterPID.setReference(setpoint, ControlType.kVelocity);

		double velocityThreshold  = 10000;
		
		if (shooterEncoder.getVelocity() >= velocityThreshold) {
			OldRobot.intake.setIndexer(0.3);
		} else {
			OldRobot.intake.setIndexer(0);
		}

	}
	
	public void setSetpoint(double set) {
		this.setpoint = set;
	}

		/**
		 * Returns the velocity of the encoder.
		 */
	public double getVelocity() {
		return encoder.getVelocity() * RobotMap.kEncoderConstant * 10;
	}
		

	
	

	public double getSpeed() {
		return shooterEncoder.getVelocity();
	}

	public double getSetpointFromDistance() {
		double intercept = 0;
		double slope = 0;
		return intercept + (slope * OldRobot.limelight.get2DDistance());
	}
	
	/*public void resetI() {
		shooter.clearIAccum();
	}*/
	
}
