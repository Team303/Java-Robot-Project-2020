/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;

public class OI {
	//static NetworkTable preferences = NetworkTable.getTable("Preferences");
	static Joystick left = new Joystick(0);
	static Joystick right = new Joystick(1);
	static XboxController xbox = new XboxController(2);
	static boolean disabledState = false;
	
	static double lX = 0, lY = 0, lZ = 0;
	static double rX = 0, rY = 0, rZ = 0;
	static double xlX = 0, xlY = 0, xrX = 0, xrY = 0;
	static double xLeftTrigger=0, xRightTrigger=0;

	public static int xPov = 0;
	public static int lPov = 0;
	public static int rPov = 0;
	
	static boolean[] lBtn = new boolean[9];
	static boolean[] rBtn = new boolean[9];	
	static boolean xBtnA, xBtnB, xBtnX, xBtnY, xLeftBumper, xRightBumper, xBtnStart, xBtnBack, xLeftStickBtn, xRightStickBtn;
	
	public static void update() {
		
		for(int i=1;i<8;i++) { //wpilib buttons are 1 indexed
			lBtn[i] = left.getRawButton(i);
			rBtn[i] = right.getRawButton(i);
			SmartDashboard.putNumber("POV value", right.getPOV());
		}

		lPov = left.getPOV();
		rPov = right.getPOV();
		
		updateXbox();
		//preferences = NetworkTable.getTable("Preferences");
	}
	
	
	public static void updateXbox() {
		lX = left.getX();
		lY = left.getY();
		lZ = left.getZ();
		
		rX = right.getX();
		rY = right.getY();
		rZ = right.getZ();
		
		xlX = xbox.getX(Hand.kLeft);
		xlY = xbox.getY(Hand.kLeft);
		xrX = xbox.getX(Hand.kRight);
		xrY = xbox.getY(Hand.kRight);
		
		xBtnA = xbox.getAButton();
		SmartDashboard.putBoolean("a state", xBtnA);
		xBtnB = xbox.getBButton();
		SmartDashboard.putBoolean("b state", xBtnB);
		xBtnX = xbox.getXButton();
		xBtnY = xbox.getYButton();
		xLeftBumper = xbox.getBumper(Hand.kLeft);
		xRightBumper = xbox.getBumper(Hand.kRight);
		xBtnStart = xbox.getStartButton();
		xBtnBack = xbox.getBackButton();
		xLeftStickBtn = xbox.getStickButton(Hand.kLeft);
		xRightStickBtn = xbox.getStickButton(Hand.kRight);
		xLeftTrigger = xbox.getTriggerAxis(Hand.kLeft);
		xRightTrigger = xbox.getTriggerAxis(Hand.kRight);
	}
	
	public static boolean pulse(boolean input){
	
		if(input){
			if(!disabledState){
				disabledState = true;
				return true;
			}
			return false;
		}
		disabledState = false;
		return false;
	}
}