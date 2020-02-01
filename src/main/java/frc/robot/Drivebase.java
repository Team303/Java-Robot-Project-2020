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


		rightMotors = new SpeedControllerGroup(rightBack, rightFront, rightMiddle);
		leftMotors = new SpeedControllerGroup(leftBack, leftFront, leftMiddle);
		drive = new DifferentialDrive(leftMotors, rightMotors);
		
		rightBack.setInverted(RobotMap.BACK_RIGHT_INV);
		rightMiddle.setInverted(RobotMap.MIDDLE_RIGHT_INV);
		rightFront.setInverted(RobotMap.FRONT_RIGHT_INV); 
		leftBack.setInverted(RobotMap.BACK_LEFT_INV);
		leftMiddle.setInverted(RobotMap.MIDDLE_LEFT_INV);
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


	public int getLeftEncoder() {
		return leftFront.getSelectedSensorPosition(0);
	}

	public int getRightEncoder() {
		return -rightBack.getSelectedSensorPosition(0);
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
