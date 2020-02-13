/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

public class RobotMap {

	//================= CAN IDs =================
	
	//these don't do anything, but are here so we don't accidentally use their IDs
	public static final int PDP = 0;
	

	public static final int FRONT_LEFT = 2; //encoder on this talon
	public static final int MIDDLE_LEFT = 3; //encoder on this talon
	public static final int BACK_LEFT = 4; //encoder on this talon
	public static final int FRONT_RIGHT = 5; 
	public static final int MIDDLE_RIGHT = 6; 
	public static final int BACK_RIGHT = 7;
	
	public static final boolean FRONT_LEFT_INV = false;
	public static final boolean MIDDLE_LEFT_INV = false;
	public static final boolean BACK_LEFT_INV = false;
	public static final boolean FRONT_RIGHT_INV = false;
	public static final boolean MIDDLE_RIGHT_INV = false;
	public static final boolean BACK_RIGHT_INV = false;

	public static final int INDEXER = -1;
	public static final int INTAKE = -1;
	public static final int MOTION_SENSOR = -1;

	public static final int SHOOTER = -1;
	public static final int SHOOTER_SLAVE = -1;

	public static final int INTAKE_PISTON = -1;
	public static final int CONTROL_PANEL = -1;
	public static final boolean SHOOTER_INV = false;


	// FROM ROBOT CHARACTERIZATION \\
	public static final double ksVolts = 0.843; 					
	public static final double kvVoltSecondsPerMeter = 0.915;	
	public static final double kaVoltSecondsSquaredPerMeter = 0.1; // characterization - 0.252
	
	public static final double kTrackWidthMeters = 0.7874;
	public static final DifferentialDriveKinematics kDriveKinematics = 
		new DifferentialDriveKinematics(kTrackWidthMeters);

	public static final int kEncoderCPR = 2350;
	public static final double kWheelDiameterMeters = 0.1016;
	public static final double kEncoderConstant =
		(kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;



}