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

	static Timer timer = new Timer();
	//static NavX navX;
	public static Drivebase drivebase;
	//public static Intake intake;
	

	@Override
	public void robotInit() {
		
		timer.start();
		drivebase = new Drivebase();
		
		//navX = new NavX();
		//navX.navX.zeroYaw();
		//intake = new Intake();
		//Robot.drivebase.zeroEncoder();

	}

	@Override
	public void robotPeriodic() {
		updateSmartDashboard();
		OI.update();

		/*if (OI.lBtn[2]) {
			navX.navX.zeroYaw();
		}*/
		
	}
	

	@Override
	public void autonomousInit() {

		//Robot.drivebase.zeroEncoder();
		//Robot.drivebase.reset();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {	
		//Robot.drivebase.periodic();
		
	}

	/**
	 * Run once during operator control
	 */
	@Override
	public void teleopInit() {
		//navX.navX.zeroYaw();
		//timer.reset();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		OI.update();
		//Robot.drivebase.periodic();
		
		if (OI.lBtn[2]) {
			//navX.navX.zeroYaw();
		} else if (OI.lBtn[3]) {
			Robot.drivebase.zeroEncoder();
		} else {
			Robot.drivebase.drive(-OI.lY, -OI.rY);
		}
	}


	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Front Encoder", Robot.drivebase.getLeftEncoder()[0]);
		SmartDashboard.putNumber("Right Front Encoder", Robot.drivebase.getRightEncoder()[0]);
		SmartDashboard.putNumber("Left Middle Encoder", Robot.drivebase.getLeftEncoder()[1]);
		SmartDashboard.putNumber("Right Middle Encoder", Robot.drivebase.getRightEncoder()[1]);
		SmartDashboard.putNumber("Left Back Encoder", Robot.drivebase.getLeftEncoder()[2]);
		SmartDashboard.putNumber("Right Back Encoder", Robot.drivebase.getRightEncoder()[2]);
		//SmartDashboard.putNumber("NavX Yaw", Robot.navX.getYaw());
		
		SmartDashboard.putNumber("Left Power", Robot.drivebase.leftFront.get());
		SmartDashboard.putNumber("Right Power", Robot.drivebase.rightFront.get());


		SmartDashboard.putNumber("Left Joystick", OI.lY);
		SmartDashboard.putNumber("Right Joystick", OI.rY);


	}
}