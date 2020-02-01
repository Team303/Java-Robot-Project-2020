/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivebase extends SubsystemBase {

	public WPI_TalonSRX rightBack;
	public WPI_TalonSRX rightFront;
	public WPI_TalonSRX leftFront;
	public WPI_TalonSRX leftBack;

	SpeedControllerGroup rightMotors;
	SpeedControllerGroup leftMotors;
	DifferentialDrive drive;

	DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));
	Pose2d pose = new Pose2d();

	public Drivebase()  {
		rightBack = new WPI_TalonSRX(RobotMap.REAR_RIGHT);
		rightFront = new WPI_TalonSRX(RobotMap.FRONT_RIGHT);
		leftBack = new WPI_TalonSRX(RobotMap.REAR_LEFT);
		leftFront = new WPI_TalonSRX(RobotMap.FRONT_LEFT);
		rightMotors = new SpeedControllerGroup(rightBack, rightFront);
		leftMotors = new SpeedControllerGroup(leftBack, leftFront);
		drive = new DifferentialDrive(leftMotors, rightMotors);
		
		rightBack.setInverted(RobotMap.REAR_RIGHT_INV);
		rightFront.setInverted(RobotMap.FRONT_RIGHT_INV); 
		leftBack.setInverted(RobotMap.REAR_LEFT_INV);
		leftFront.setInverted(RobotMap.FRONT_LEFT_INV);
		
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
		rightBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
	}
	
	public void drive(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void setOutputVolts(double left, double right) {
		leftMotors.setVoltage(-left * SmartDashboard.getNumber("Constant", 0.8));
		rightMotors.setVoltage(right *  SmartDashboard.getNumber("Constant", 0.8));
	}

	//--------------- POSE METHODS---------------------

	public void periodic() {
		pose = odometry.update(Rotation2d.fromDegrees(-Robot.navX.getYaw()), getLeftMeters(), getRightMeters());
	}
	public void zeroEncoder() {
		leftFront.setSelectedSensorPosition(0, 0, 1000);
		rightBack.setSelectedSensorPosition(0, 0, 1000);
	}

	public DifferentialDriveWheelSpeeds getWheelSpeeds() {
		return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
	}

	public void reset() {
		odometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(Robot.navX.getYaw()));
	}

	public Pose2d getPose(){
		return odometry.getPoseMeters();
	}

	public double getPoseX() {
		return pose.getTranslation().getX();
		
	}

	public double getPoseY(){
		return pose.getTranslation().getY();
	}

	//------------------HELPER METHODS------------------------------


	public int getLeftEncoder() {
		return leftFront.getSelectedSensorPosition(0);
	}

	public int getRightEncoder() {
		return -rightBack.getSelectedSensorPosition(0);
	}

	public double getLeftMeters() {
		return leftFront.getSelectedSensorPosition(0) * RobotMap.kEncoderConstant;
	}

	public double getRightMeters() {
		return -rightBack.getSelectedSensorPosition(0) * RobotMap.kEncoderConstant;
	}

	public double getLeftVelocity() {
		return leftFront.getSelectedSensorVelocity(0) * RobotMap.kEncoderConstant * 10;
	}

	public double getRightVelocity() {
		return -rightBack.getSelectedSensorVelocity(0) * RobotMap.kEncoderConstant * 10;
	}
	


}
