/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.util.Units;

public class RobotMap {

	//================= CAN IDs =================
	
	//these don't do anything, but are here so we don't accidentally use their IDs
	public static final int PDP = 0;
	

	public static final int FRONT_LEFT = 2; 
	public static final int MIDDLE_LEFT = 3; 
	public static final int BACK_LEFT = 4; 

	public static final int FRONT_RIGHT = 5; 
	public static final int MIDDLE_RIGHT = 6; 
	public static final int BACK_RIGHT = 7;
	
	public static final boolean FRONT_LEFT_INV = false;
	public static final boolean MIDDLE_LEFT_INV = false;
	public static final boolean BACK_LEFT_INV = false;
	public static final boolean FRONT_RIGHT_INV = true;
	public static final boolean MIDDLE_RIGHT_INV = true;
	public static final boolean BACK_RIGHT_INV = true;

	public static final int INDEXER = -1;
	public static final int INTAKE = -1;
	public static final int MOTION_SENSOR = -1;

	public static final int SHOOTER = -1;
	public static final int SHOOTER_SLAVE = -1;

	public static final boolean SHOOTER_INV = false;
	public static final boolean SHOOTER_SLAVE_INV = true;


	//From Robot Characterization 
	public static final double ksVolts = 0.126; 					
	public static final double kvVoltSecondsPerMeter = 2.26;	
	public static final double kaVoltSecondsSquaredPerMeter = 0.191; 


	public static final double kTrackWidthMeters = Units.inchesToMeters(23.75);
	public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackWidthMeters);

	public static final int kEncoderCPR = 20480;
	public static final double kWheelDiameterMeters = Units.inchesToMeters(6.125);
	public static final double kEncoderConstant = (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;


}