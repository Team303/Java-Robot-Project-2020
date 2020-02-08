/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//import frc.robot.Autonomous.AutoStates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.io.IOException;

//import com.sun.java.swing.plaf.windows.TMSchema.Control;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;


public class Robot extends TimedRobot {	

	static NavX navX;
	public static Drivebase drivebase;
	public static Commands commands;
	public static Command autoCommand;
	//public static Intake intake;
	

	@Override
	public void robotInit() {
		
		drivebase = new Drivebase();
		Robot.drivebase.zeroEncoders();	
		
		navX = new NavX();
		navX.navX.zeroYaw();
		
		
		commands = new Commands();
	
	}

	@Override
	public void robotPeriodic() {
		updateSmartDashboard();
		OI.update();

		if (OI.lZ < 0.5) {
			drivebase.zeroEncoders();
			navX.zeroYaw();
			
		}
		
	}
	

	@Override
	public void autonomousInit() {
		Robot.drivebase.zeroEncoders();
		Robot.drivebase.reset();

		try{
			autoCommand = commands.getAutonomousCommand();
		} catch (Exception e) {
			System.out.println("CANNOT MAKE AUTONMOUS COMMAND");
		}

		autoCommand.schedule();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {	
		Robot.drivebase.periodic();
		CommandScheduler.getInstance().run();
	}

	/**
	 * Run once during operator control
	 */
	@Override
	public void teleopInit() {
		navX.navX.zeroYaw();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		OI.update();
		Robot.drivebase.periodic();

		Robot.drivebase.drive(-OI.lY, -OI.rY);
		
	}

	public void updateSmartDashboard() {
		
		//Encoder Values & NavX Yaw of all the wheels
		SmartDashboard.putNumber("Left Front Encoder", Robot.drivebase.getLeftEncoders()[0]);
		SmartDashboard.putNumber("Right Front Encoder", Robot.drivebase.getRightEncoders()[0]);
		SmartDashboard.putNumber("Left Middle Encoder", Robot.drivebase.getLeftEncoders()[1]);
		SmartDashboard.putNumber("Right Middle Encoder", Robot.drivebase.getRightEncoders()[1]);
		SmartDashboard.putNumber("Left Back Encoder", Robot.drivebase.getLeftEncoders()[2]);
		SmartDashboard.putNumber("Right Back Encoder", Robot.drivebase.getRightEncoders()[2]);		
		SmartDashboard.putNumber("NavX Yaw", Robot.navX.getYaw());

		//Pose, Velocity, Distance Information
		SmartDashboard.putNumber("Pose X", Robot.drivebase.getPoseX());
		SmartDashboard.putNumber("Pose Y", Robot.drivebase.getPoseY());
		SmartDashboard.putNumber("Left Wheel Speed", Robot.drivebase.getLeftVelocity());
		SmartDashboard.putNumber("Right Wheel Speed", Robot.drivebase.getLeftVelocity());
		SmartDashboard.putNumber("Left Distance", Robot.drivebase.getLeftDistanceMeters());
		SmartDashboard.putNumber("Right Distance", Robot.drivebase.getRightDistanceMeters());
		SmartDashboard.putNumber("Left Feet Distance", Units.metersToFeet(Robot.drivebase.getLeftDistanceMeters()));
		SmartDashboard.putNumber("Right Feet Distance", Units.metersToFeet(Robot.drivebase.getRightDistanceMeters()));

		//Joystick Values
		SmartDashboard.putNumber("Left Joystick", OI.lY);
		SmartDashboard.putNumber("Right Joystick", OI.rY);
	}
}