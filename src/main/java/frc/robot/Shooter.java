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
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.controller.PIDController;

public class Shooter {
	CANSparkMax shooter;
	CANSparkMax shooterSlave;

	CANPIDController shooterPID;
	CANEncoder	shooterEncoder;
	int count = 0;

	boolean shooterRunning;
	double setpoint = 0;

	SimpleMotorFeedforward shooterFeedforward;
	PIDController pid;

	public static final int GEARING = 1;
	double encoderConstant = (1 / GEARING) * 1;


	
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


		double kP = SmartDashboard.getNumber("Shooter P", 0.3);
		double kI = SmartDashboard.getNumber("Shooter I", 0.0);
		double kD = SmartDashboard.getNumber("Shooter D", 1.0);

		setpoint = 0;
		shooterRunning = false;

		shooterFeedforward = new SimpleMotorFeedforward(0,0,0);
		pid = new PIDController(kP, kI, kD);

		/*double kIz = 0;
		double kFF = 0.02475;
		double kMaxOutput = 1.0;
		double kMinOutput = -1.0;

		shooterPID.setP(kP);
		shooterPID.setI(kI);
		shooterPID.setD(kD);
		shooterPID.setFF(kFF);
		shooterPID.setOutputRange(kMinOutput, kMaxOutput);*/

	}
	
	/**
	 * Runs continually to regulate the behavior of the shooter.
	 */
	public void control() {

		/*
		if (OI.lBtn[5]) {
			useVisionSetpoint();
		} else if (OI.xBtnA){
			setpoint = 0;
		}*/


		//runShooter();
	}

	public void runShooter() {
		shooterPIDControl();

		double velocityThreshold  = 10000;
		
		if (shooterEncoder.getVelocity() >= velocityThreshold) {
			shooterRunning = true;
			Robot.intake.setIndexer(0.3);
		} else {
			if (shooterRunning)  {
				Robot.intake.indexer.set(0);
				shooterRunning = false;
			}
		}
	}

	public void shooterPIDControl() {
		double feedforward = shooterFeedforward.calculate(setpoint);
		double voltage = feedforward + pid.calculate(getVelocity(), setpoint);
		shooter.set(voltage / 12);
	}


	public void useVisionSetpoint() {
		setSetpoint(getSetpointFromDistance());
	}

	public void setSetpoint(double set) {
		this.setpoint = set;
	}

	public double getVelocity() {
		return shooterEncoder.getVelocity() * encoderConstant / 60.;
	}
		
	public double getSpeed() {
		return shooterEncoder.getVelocity();
	}

	public double getSetpointFromDistance() {
		double intercept = 0;
		double slope = 0;
		return intercept + (slope * Robot.limelight.get2DDistance());
	}
	
	/*public void resetI() {
		shooter.clearIAccum();
	}*/
	
}
