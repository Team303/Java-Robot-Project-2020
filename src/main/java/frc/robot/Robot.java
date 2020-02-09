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
	//static AutoStates autoSelected;
//	SendableChooser<AutoStates> chooser = new SendableChooser<>();
	//static Drivebase driveSubsystem;
	public static enum Position {LEFT, PP, RIGHT};
    public static enum Auto {DO_NOTHING, Just_Shoot, Shoot_Trench, Trench_Shoot, PreLoad2_Shoot, DrivePP_Shoot, DriveMid_Shoot};

	
	static Timer timer = new Timer();
	static Autonomous auto;
	public static NavX navX;
	private static double width;
	private static double centerX;
	private static double area;
	public static Joystick left;
	public static Joystick right;
//	public static Commands commands;
	public static Drivebase drivebase;
	public static Command autoCommand;
	public static Intake intake;
	public static Shooter shooter;

	private SendableChooser<Position> positionChooser = new SendableChooser<>();
	private SendableChooser<Auto> autoChooser = new SendableChooser<>();


	@Override
	public void robotInit() {

		timer.start();
		drivebase = new Drivebase();
		shooter = new Shooter();
		auto = new Autonomous();
		navX = new NavX();
		navX.navX.zeroYaw();
		intake = new Intake();
		Robot.drivebase.zeroEncoder();

		
		positionChooser.addOption("Left", Position.LEFT);
		positionChooser.addOption("Right", Position.RIGHT);
		positionChooser.addOption("PP", Position.PP);
	
		for (Auto auto: Auto.values()) {
		  autoChooser.addOption(auto.toString(), auto);
		}
	
		SmartDashboard.putData("Position", positionChooser);
		SmartDashboard.putData("Auto", autoChooser);



	}

	@Override
	public void robotPeriodic() {
		updateSmartDashboard();
		OI.update();

		if (OI.lBtn[2]) {
			navX.navX.zeroYaw();
		}

	}


	@Override
	public void autonomousInit() {

		Robot.drivebase.zeroEncoder();
		Robot.drivebase.reset();
		
		Position position = positionChooser.getSelected();
		
		if(position == Position.LEFT){
			
		}
		else if( position  == Position.RIGHT){
			
		}
		else if(position== Position.PP){
		
		}		


	}

	public void assembleRightAutos(){
			
	}

	public void assembleLeftAutos(){
		auto.assembleLR_justShoot();
		auto.assembleLR_drivePPShoot();
		auto.assembleLR_midDriveShoot();
	}

	public void assemblePPAutos(){
		
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
		navX.navX.zeroYaw();
		timer.reset();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		OI.update();
		Robot.drivebase.periodic();



		if (OI.lBtn[2]) {
			navX.navX.zeroYaw();
		} else if (OI.lBtn[3]) {
			Robot.drivebase.zeroEncoder();
		} else {
			Robot.drivebase.drive(OI.lY, OI.rY);
		}


		intake.intakeControl();
		shooter.control();


	}


	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Encoder", Robot.drivebase.getLeftEncoder());
		SmartDashboard.putNumber("Right Encoder", Robot.drivebase.getRightEncoder());
		SmartDashboard.putNumber("NavX Yaw", Robot.navX.getYaw());

		SmartDashboard.putNumber("Left Power", Robot.drivebase.leftFront.get());
		SmartDashboard.putNumber("Right Power", Robot.drivebase.rightFront.get());

		SmartDashboard.putNumber("Shooter Velocity", Robot.shooter.getVelocity());



	}
}