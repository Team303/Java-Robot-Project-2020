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
	
	public WPI_TalonFX rightBack;
	public WPI_TalonFX rightMiddle;
	public WPI_TalonFX rightFront;
	public WPI_TalonFX leftFront;
	public WPI_TalonFX leftMiddle;
	public WPI_TalonFX leftBack;

	SpeedControllerGroup rightMotors;
	SpeedControllerGroup leftMotors;
	DifferentialDrive drive;

	DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));
	Pose2d pose = new Pose2d();

	public Drivebase()  {
		rightBack = new WPI_TalonFX(RobotMap.BACK_RIGHT);
		rightMiddle = new WPI_TalonFX(RobotMap.MIDDLE_RIGHT);
		rightFront = new WPI_TalonFX(RobotMap.FRONT_RIGHT);
		leftBack = new WPI_TalonFX(RobotMap.BACK_LEFT);
		leftMiddle = new WPI_TalonFX(RobotMap.MIDDLE_LEFT);
		leftFront = new WPI_TalonFX(RobotMap.FRONT_LEFT);	
		
		//Reset to factory default settings
		rightBack.configFactoryDefault();
		rightMiddle.configFactoryDefault();
		rightFront.configFactoryDefault();
		leftBack.configFactoryDefault();
		leftMiddle.configFactoryDefault();
		leftFront.configFactoryDefault();

		//Reset to coast mode
		rightBack.setNeutralMode(NeutralMode.Coast);
		rightMiddle.setNeutralMode(NeutralMode.Coast);
		rightFront.setNeutralMode(NeutralMode.Coast);
		leftBack.setNeutralMode(NeutralMode.Coast);
		leftMiddle.setNeutralMode(NeutralMode.Coast);
		leftFront.setNeutralMode(NeutralMode.Coast);
				
		rightMotors = new SpeedControllerGroup(rightBack, rightFront, rightMiddle);
		leftMotors = new SpeedControllerGroup(leftFront, leftMiddle, leftBack);
		drive = new DifferentialDrive(leftMotors, rightMotors);
		
		rightBack.setInverted(RobotMap.BACK_RIGHT_INV);
		rightMiddle.setInverted(RobotMap.MIDDLE_RIGHT_INV);
		rightFront.setInverted(RobotMap.FRONT_RIGHT_INV); 
		leftBack.setInverted(RobotMap.BACK_LEFT_INV);
		leftMiddle.setInverted(RobotMap.MIDDLE_LEFT_INV);
		leftFront.setInverted(RobotMap.FRONT_LEFT_INV);

		 leftBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100000);
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100000);
		leftMiddle.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100000);
		rightBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100000);
		rightMiddle.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100000);
		rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100000); 
	}
	
	public void drive(double left, double right) {
		drive.tankDrive(left, right);

	}

	public void setOutputVolts(double left, double right) {

		double multiplier = SmartDashboard.getNumber("Trajectory Multiplier", 1.0);

		leftMotors.setVoltage(-left * multiplier);
		rightMotors.setVoltage(right *  multiplier);
	}

	public int[] getLeftEncoders() {
		return new int[] { leftFront.getSelectedSensorPosition(0),
			leftMiddle.getSelectedSensorPosition(0),
			leftBack.getSelectedSensorPosition(0)};
	}

	public int[] getRightEncoders() {
		return new int[] { rightFront.getSelectedSensorPosition(0),
			rightMiddle.getSelectedSensorPosition(0),
			rightBack.getSelectedSensorPosition(0)};	
	}

	public void zeroEncoders() {
		leftFront.setSelectedSensorPosition(0, 0, 1000);
		leftMiddle.setSelectedSensorPosition(0, 0, 1000);
		leftBack.setSelectedSensorPosition(0, 0, 1000);
		
		rightFront.setSelectedSensorPosition(0, 0, 1000);
		rightMiddle.setSelectedSensorPosition(0, 0, 1000);
		rightBack.setSelectedSensorPosition(0, 0, 1000);
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


	public Pose2d getPose(){
		return odometry.getPoseMeters();
	}

	public double getLeftDistanceMeters() {
		return leftFront.getSelectedSensorPosition(0) * RobotMap.kEncoderConstant;
	}

	public double getRightDistanceMeters() {
		return -rightFront.getSelectedSensorPosition(0) * RobotMap.kEncoderConstant;
	}

	public double getLeftVelocity() {
		return leftFront.getSelectedSensorVelocity(0) * RobotMap.kEncoderConstant * 10;
	}

	public double getRightVelocity() {
		return -rightFront.getSelectedSensorVelocity(0) * RobotMap.kEncoderConstant * 10;
	}
	
	public double getPoseX() {
		return pose.getTranslation().getX();	
	}
	
	public double getPoseY(){
		return pose.getTranslation().getY();
	}


	/* rightBack.set(ControlMode.PercentOutput, right);
		rightMiddle.set(ControlMode.PercentOutput, right);
		rightFront.set(ControlMode.PercentOutput, right);
		leftFront.set(ControlMode.PercentOutput, left);
		leftMiddle.set(ControlMode.PercentOutput, left);
		leftBack.set(ControlMode.PercentOutput, left); */


	
	
	


}
