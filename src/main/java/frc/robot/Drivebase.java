/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.Talon;

public class Drivebase extends SubsystemBase {
	
	WPI_TalonFX leftMaster;
	WPI_TalonFX rightMaster;
	DifferentialDrive drive;

	static private int PIDIDX = 0;


	DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));
	Pose2d pose = new Pose2d();

	public Drivebase()  {
		
		leftMaster = new WPI_TalonFX(2);
		leftMaster.setInverted(false);
		leftMaster.setSensorPhase(false);
		leftMaster.setNeutralMode(NeutralMode.Brake);
	
		rightMaster = new WPI_TalonFX(5);
		rightMaster.setInverted(false);
		rightMaster.setSensorPhase(false);
		rightMaster.setNeutralMode(NeutralMode.Brake);
	
		/* WPI_TalonFX leftSlave0 = new WPI_TalonFX(3);
		leftSlave0.setInverted(false);
		leftSlave0.follow(leftMaster);
		leftSlave0.setNeutralMode(NeutralMode.Brake); */
		WPI_TalonFX leftSlave1 = new WPI_TalonFX(4);
		leftSlave1.setInverted(false);
		leftSlave1.follow(leftMaster);
		leftSlave1.setNeutralMode(NeutralMode.Brake);
	
		/* WPI_TalonFX rightSlave0 = new WPI_TalonFX(6);
		rightSlave0.setInverted(false);
		rightSlave0.follow(rightMaster);
		rightSlave0.setNeutralMode(NeutralMode.Brake); */
		WPI_TalonFX rightSlave1 = new WPI_TalonFX(7);
		rightSlave1.setInverted(false);
		rightSlave1.follow(rightMaster);
		rightSlave1.setNeutralMode(NeutralMode.Brake);
	
		drive = new DifferentialDrive(leftMaster, rightMaster);
	
		drive.setDeadband(0);
	
		leftMaster.configSelectedFeedbackSensor(
			FeedbackDevice.IntegratedSensor,
			PIDIDX, 10
		);
		
		rightMaster.configSelectedFeedbackSensor(
			FeedbackDevice.IntegratedSensor,
			PIDIDX, 10
		);
		
		leftMaster.setSelectedSensorPosition(0);
		rightMaster.setSelectedSensorPosition(0);

	
	}
	
	public void drive(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void setOutputVolts(double left, double right) {
		System.out.println("Local:" + Math.abs(left) + Math.abs(right));
		
		drive(left / 12.0, right / 12.0);

		//double multiplier = SmartDashboard.getNumber("Trajectory Multiplier", 1.0);
		//leftMaster.setVoltage(left * multiplier);
		//rightMaster.setVoltage(-right *  multiplier);
	}

	//-------------- TURN TO ANGLE ---------------------------


	public double turnToAngle(double current, double intended, double tolerance) {

		double slope45 = 0.00628212;  //0.0105
		double intercept45 = 0.3	; // 0.25
		double slope90 = 0;
		double intercept90 = 0;
		double slope180 = 0;
		double intercept180 = 0;

		double distanceError = distanceBetweenAngles(current, intended);
		double power = 0;
		SmartDashboard.putNumber("Distance Error", distanceError);

		if (Math.abs(distanceError) <= 90) {
			power = intercept45 + (slope45 * Math.abs(distanceError));
		} else if (Math.abs(distanceError) <= 90) {
			power = intercept90 + (slope90 * Math.abs(distanceError));
		} else if (Math.abs(distanceError) <= 180) {
			power = intercept180 + (slope180 * Math.abs(distanceError));
		}

		if (Math.abs(distanceError) <= tolerance) {
			power = 0;
		}

		SmartDashboard.putNumber("TURNING", power);

		drive(Math.copySign(power, distanceError), -Math.copySign(power, distanceError));

		return distanceError;

	}

	public static double distanceBetweenAngles(double current, double intended) {

		double currentAct = (current >= 0) ? current: 360 - Math.abs(current);
		double intendedAct = (intended >= 0) ? intended: 360 - Math.abs(intended);	

		double rightDistance = 0;

		if (intendedAct >= currentAct) {	
			rightDistance = intendedAct - currentAct;
		} else {
			rightDistance = Math.abs(current) + Math.abs(intendedAct);
		}
		
		double leftDistance = Math.abs(360 - rightDistance);
		
		return (leftDistance <= rightDistance) ? -leftDistance : rightDistance;
		
	}

	//--------------- UPDATE POSE METHODS---------------------

	public void periodic() {
		pose = odometry.update(Rotation2d.fromDegrees(-Robot.navX.getYaw()), getLeftDistanceMeters(), getRightDistanceMeters());
	}

	public void reset() {
		odometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(-Robot.navX.getYaw()));
	}

	public DifferentialDriveWheelSpeeds getWheelSpeeds() {
		return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
	}


	//--------------- POSE HELPER METHODS---------------------

	public void zeroEncoders() {
		leftMaster.setSelectedSensorPosition(0, 0, 1000);
		rightMaster.setSelectedSensorPosition(0, 0, 1000);
	}
	
	public int getLeftEncoder() {
		return leftMaster.getSelectedSensorPosition(0);
	}

	public int getRightEncoder() {
		return -rightMaster.getSelectedSensorPosition(0);
	
	}

	public Pose2d getPose(){
		return odometry.getPoseMeters();
	}

	public double getLeftDistanceMeters() {
		return leftMaster.getSelectedSensorPosition(0) * RobotMap.kEncoderConstant;
	}

	public double getRightDistanceMeters() {
		return -rightMaster.getSelectedSensorPosition(0) * RobotMap.kEncoderConstant;
	}

	public double getLeftVelocity() {
		return leftMaster.getSelectedSensorVelocity(0) * RobotMap.kEncoderConstant * 10;
	}

	public double getRightVelocity() {
		return -rightMaster.getSelectedSensorVelocity(0) * RobotMap.kEncoderConstant * 10;
	}
	
	public double getPoseX() {
		return pose.getTranslation().getX();	
	}
	
	public double getPoseY(){
		return pose.getTranslation().getY();
	}
	

}
