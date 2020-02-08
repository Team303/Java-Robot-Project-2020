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
	
	/*public WPI_TalonFX rightBack;
	public WPI_TalonFX rightMiddle;
	public WPI_TalonFX rightFront;
	public WPI_TalonFX leftFront;
	public WPI_TalonFX leftMiddle;
	*/
	WPI_TalonFX leftMaster;
	WPI_TalonFX rightMaster;

	//SpeedControllerGroup rightMotors;
	//SpeedControllerGroup leftMotors;
	DifferentialDrive drive;

	DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));
	Pose2d pose = new Pose2d();

	public Drivebase()  {
		/*rightBack = new WPI_TalonFX(RobotMap.BACK_RIGHT);
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

		
		leftBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 1, 10);
		leftMiddle.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 2, 10);
		rightBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		rightMiddle.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 1, 10);
		rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 2, 10); 
		*/

		leftMaster = new WPI_TalonFX(2);
   		leftMaster.setInverted(false);
    	leftMaster.setSensorPhase(false);
    	leftMaster.setNeutralMode(NeutralMode.Brake);

    	rightMaster = new WPI_TalonFX(5);
    	rightMaster.setInverted(false);
    	rightMaster.setSensorPhase(false);
    	rightMaster.setNeutralMode(NeutralMode.Brake);

    	WPI_TalonFX leftSlave0 = new WPI_TalonFX(3);
    	leftSlave0.setInverted(false);
    	leftSlave0.follow(leftMaster);
    	leftSlave0.setNeutralMode(NeutralMode.Brake);
    	WPI_TalonFX leftSlave1 = new WPI_TalonFX(4);
    	leftSlave1.setInverted(false);
    	leftSlave1.follow(leftMaster);
    	leftSlave1.setNeutralMode(NeutralMode.Brake);

    	WPI_TalonFX rightSlave0 = new WPI_TalonFX(6);
    	rightSlave0.setInverted(false);
    	rightSlave0.follow(rightMaster);
    	rightSlave0.setNeutralMode(NeutralMode.Brake);
    	WPI_TalonFX rightSlave1 = new WPI_TalonFX(7);
    	rightSlave1.setInverted(false);
    	rightSlave1.follow(rightMaster);
		rightSlave1.setNeutralMode(NeutralMode.Brake);
		
		drive = new DifferentialDrive(leftMaster, rightMaster);


		drive.setDeadband(0);
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,0, 10);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,0, 10);

		leftMaster.setSelectedSensorPosition(0);
		rightMaster.setSelectedSensorPosition(0);

	
	}
	
	public void drive(double left, double right) {
		drive.tankDrive(left, right);

		/*rightBack.set(ControlMode.PercentOutput, right);
		rightMiddle.set(ControlMode.PercentOutput, right);
		rightFront.set(ControlMode.PercentOutput, right);
		leftFront.set(ControlMode.PercentOutput, left);
		leftMiddle.set(ControlMode.PercentOutput, left);
		leftBack.set(ControlMode.PercentOutput, left);*/
	}

	/*public void setOutputVolts(double left, double right) {

		double multiplier = SmartDashboard.getNumber("Trajectory Multiplier", 1.0);

		leftMaster.setVoltage(left * multiplier);
		rightMaster.setVoltage(-right *  multiplier);
	}*/

	public int getLeftEncoders() {
		return leftMaster.getSelectedSensorPosition(0);
	}

	public int getRightEncoders() {
		return rightMaster.getSelectedSensorPosition(0);
	
	}

	public void zeroEncoders() {
		leftMaster.setSelectedSensorPosition(0, 0, 1000);
		rightMaster.setSelectedSensorPosition(0, 0, 1000);

	}

	//--------------- UPDATE POSE METHODS---------------------

	public void periodic() {
		//pose = odometry.update(Rotation2d.fromDegrees(-Robot.navX.getYaw()), getLeftDistanceMeters(), getRightDistanceMeters());
	}

	public void reset() {
		//odometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(-Robot.navX.getYaw()));
	}

	public DifferentialDriveWheelSpeeds getWheelSpeeds() {
		return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
	}


	//--------------- POSE HELPER METHODS---------------------


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
