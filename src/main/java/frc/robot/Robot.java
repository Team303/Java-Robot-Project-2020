/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


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

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;


public class Robot extends TimedRobot {
	public static enum Position {LEFT, RIGHT, PP};
	public static enum Auto {DO_NOTHING, SHOOT_FORWARD, SHOOT_BACKWARD, SHOOT_TRENCH, TRENCH_SHOOT, GO_PP_SHOOT, GO_MID_SHOOT, GO_PP_SHOOT_TRENCH, GO_MID_SHOOT_TRENCH};


	private SendableChooser<Position> positionChooser = new SendableChooser<>();

	public static NavX navX;
	public static Autonomous auto;
	public static Drivebase drivebase;
	public static Commands commands;
	public static Command autoCommand;
	public static Limelight limelight;
	public static Camera camera;
	public static Intake intake;
	public static Shooter shooter;
	public static Axis axis;
	
	private SendableChooser<Auto> autoChooser = new SendableChooser<>();

	

	@Override
	public void robotInit() {

		positionChooser.addObject("LEFT", Position.LEFT);
		positionChooser.addObject("POWER PORT", Position.PP);
		positionChooser.addObject("RIGHT", Position.RIGHT);

		for(Auto auto : Auto.values()) {
			autoChooser.addObject(auto.toString(), auto);
			
		}
		
		SmartDashboard.putData("Position", positionChooser);
		SmartDashboard.putData("Autos", autoChooser);
										

		drivebase = new Drivebase();
		Robot.drivebase.zeroEncoders();	
		
		navX = new NavX();
		navX.navX.zeroYaw();		
		commands = new Commands();

		SmartDashboard.putNumber("Output Voltage", 0);
		//Smart Dashboard Commands
		SmartDashboard.putNumber("lP value", 0.0);
		SmartDashboard.putNumber("lI value", 0.0);
		SmartDashboard.putNumber("lD value", 0.0);
		SmartDashboard.putNumber("rP value", 0.0);
		SmartDashboard.putNumber("rI value", 0.0);
		SmartDashboard.putNumber("rD value", 0.0);
   
        SmartDashboard.putNumber("kS", RobotMap.ksVolts);
        SmartDashboard.putNumber("kV", RobotMap.kvVoltSecondsPerMeter);
        SmartDashboard.putNumber("kA", RobotMap.kaVoltSecondsSquaredPerMeter);

        SmartDashboard.putNumber("Max Velocity", 3.0);
        SmartDashboard.putNumber("Max Acceleration", 3.0);

		SmartDashboard.putNumber("Trajectory Multiplier", 1.0);

		
		navX = new NavX();
		commands = new Commands();
		shooter = new Shooter();
		intake = new Intake();
		drivebase = new Drivebase();
		auto = new Autonomous();
		camera = new Camera();
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

		Position position = positionChooser.getSelected();
		
		if (position == Position.LEFT) {
			assembleLeftAutos();			
		}
		else if (position == Position.RIGHT) {
			assembleRightAutos();
		}
		else if (position == Position.PP) {
			assemblePPAutos();
		}
		autoCommand.schedule();
		System.out.println("--------------START-------------");

	}
	public void assembleLeftAutos() {
		Auto selected = autoChooser.getSelected();
		
		if (selected == Auto.SHOOT_FORWARD) {			
			auto.assembleLL_shootForward();
		}
		else if (selected == Auto.SHOOT_BACKWARD) {
			auto.assembleLL_shootBackward();
		}
		else if (selected == Auto.GO_MID_SHOOT) {
			auto.assembleLL_driveMidShoot();
		}
		else if(selected == Auto.GO_MID_SHOOT_TRENCH){
			auto.assembleLL_driveMidShootTrench();
		}
		else if(selected == Auto.GO_PP_SHOOT){
			auto.assembleLL_drivePPShoot();
		}
		else if(selected == Auto.GO_PP_SHOOT_TRENCH){
			auto.assembleLL_drivePPShootTrench();
		}
		else if(selected == Auto.DO_NOTHING) {
			auto.assembleDoNothing();
		}	
		
		
	}
	public void assembleRightAutos() {
		Auto selected = autoChooser.getSelected();

		if (selected == Auto.SHOOT_FORWARD) {			
			auto.assembleRR_shootForward();
		}
		else if (selected == Auto.SHOOT_BACKWARD) {
			auto.assembleRR_shootBackward();
		}
		else if (selected == Auto.TRENCH_SHOOT) {
			auto.assembleRR_driveTrenchShoot();
		}	
		else if (selected == Auto.SHOOT_TRENCH) {
			auto.assembleRR_shootTrenchShoot();
		}
		else if (selected == Auto.DO_NOTHING) {
			auto.assembleDoNothing();
		}	
	}
	public void assemblePPAutos() {
		Auto selected = autoChooser.getSelected();

		if (selected == Auto.SHOOT_FORWARD) {			
			auto.assemblePP_shootFoward();
		}
		else if (selected == Auto.SHOOT_BACKWARD) {
			auto.assemblePP_shootBackward();
		}	
		else if (selected == Auto.SHOOT_TRENCH) {
			auto.assemblePP_shootTrench();
		}
		else if (selected == Auto.DO_NOTHING) {
			auto.assembleDoNothing();
		}	

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		auto.run(); 
		Robot.drivebase.periodic();
		CommandScheduler.getInstance().run();
	

		//System.out.println("X Pose: " + Robot.drivebase.getPoseX());
		//System.out.println("Y Pose: " + Robot.drivebase.getPoseY());

		System.out.println("LEFT PWR: " + Robot.drivebase.leftMaster.get());
		//System.out.println("DIFFERENCE: " + (Robot.drivebase.leftMaster.getMotorOutputVoltage() - Math.abs(Robot.drivebase.rightMaster.getMotorOutputVoltage())));
		//System.out.println("NavX: " + Robot.navX.getYaw());
		//System.out.println("RIGHT PWR: " + Robot.drivebase.rightMaster.getMotorOutputVoltage());
		System.out.println("-----------------------------");

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
		//double voltage = SmartDashboard.getNumber("Output Voltage", 0);
		//Robot.drivebase.leftMaster.setVoltage(voltage);
		//Robot.drivebase.rightMaster.setVoltage(voltage);

		Robot.drivebase.drive(-OI.lY, -OI.rY);
		
	}

	public void updateSmartDashboard() {
		
		//Encoder Values & NavX Yaw of all the wheels
		SmartDashboard.putNumber("Left Encoder", Robot.drivebase.getLeftEncoder());
		SmartDashboard.putNumber("Right Encoder", Robot.drivebase.getRightEncoder());
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