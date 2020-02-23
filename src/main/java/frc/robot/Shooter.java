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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	CANSparkMax shooter;
	CANSparkMax shooterSlave;

	CANPIDController shooterPID;
	CANEncoder	shooterEncoder;
	int count = 0;

	boolean shooterOverride;
	double setpoint = 0;

	public boolean shooterStop = false;

	public static final int GEARING = 1;
	double encoderConstant = (1 / GEARING) * 1;
	PIDController pidController;
	
	public Shooter() {
	 	shooter = new CANSparkMax(RobotMap.SHOOTER, MotorType.kBrushless);
		shooterSlave = new CANSparkMax(RobotMap.SHOOTER_SLAVE, MotorType.kBrushless);
		shooter.setInverted(RobotMap.SHOOTER_INV);
		shooterSlave.setInverted(RobotMap.SHOOTER_SLAVE_INV);

		shooterEncoder = shooter.getEncoder();
		setpoint = 0;
		shooterOverride = false;

		pidController = new PIDController(0,0,0);
	}
	

	public void control() {
		setSetpoint(SmartDashboard.getNumber("Shooter Setpoint", 0));
		shooterPIDControl();
	}

	public void shooterPIDControl() {
		double feedforward = SmartDashboard.getNumber("Shooter FF", 0.0001754);
		double kP = SmartDashboard.getNumber("Shooter P", 0.00000);
		double kI = SmartDashboard.getNumber("Shooter I", 0.00000);

		double error = setpoint - getVelocity();
		//double power = (feedforward * setpoint) + (kP * error);

		
		pidController.setP(kP);
		pidController.setI(kI);
		pidController.setD(0);
		

		double power = feedforward * setpoint + pidController.calculate(getVelocity(), setpoint);

		SmartDashboard.putNumber("PID Power", power);
		SmartDashboard.putNumber("PID Error", power);
		SmartDashboard.putNumber("PID P Addn", kP * error);
		SmartDashboard.putNumber("PID FF Addn", feedforward * setpoint);


		shooter.set(power);
		shooterSlave.set(power);
	}

	public void runShooter() {
		shooterPIDControl();
		
		double velocityThreshold  = 0.95 * setpoint;
		
		if (getVelocity() >= velocityThreshold) {
			shooterOverride = true;
			Robot.intake.setIndexer(SmartDashboard.getNumber("Indexer Shooter Power", 0.3));
		} else {
			if (shooterOverride) {
				Robot.intake.setIndexer(0);
				shooterOverride = false;
			}
		}
	}

	public void useVisionSetpoint() {
		setSetpoint(getSetpointFromDistance());
	}

	public double getSetpointFromDistance() {
		double intercept = 0;
		double slope = 0;
		return intercept + (slope * Robot.limelight.get2DDistance());
	}

	public void setSetpoint(double set) {
		this.setpoint = set;
	}

	public double getVelocity() {
		return shooterEncoder.getVelocity();
	}

	public void stopShooter() {
		if (setpoint > 0 && shooterStop) {
			setpoint -= 100;
		} else if (setpoint <= 0) {
			setpoint = 0;
			shooterStop = false;
		}
	}

	public double getSetpoint() {
		return setpoint;
	}
		

}
