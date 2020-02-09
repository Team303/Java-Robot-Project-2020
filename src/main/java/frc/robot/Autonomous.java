/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Action.Action;
import frc.robot.Action.ActionConveyorBelt;
import frc.robot.Action.ActionDriveStraightByEncoders;
import frc.robot.Action.ActionIntake;
import frc.robot.Action.ActionShooter;
import frc.robot.Action.ActionWait;
import frc.robot.Action.ActionParallelAction;
import frc.robot.Action.ActionTurnToAngle;



public class Autonomous {
	//creates array list for all tasks
	ArrayList<Action> arr = new ArrayList<Action>();
	int taskNum = 0;

	//empty constructor
	public Autonomous() {
		
	}

	enum AutoStates {

	}
	/**
	 * Uses an array to count the number of tasks left to perform and runs them all
	 */
	public void run() {
		if (arr.size() >= taskNum) {
			arr.get(taskNum).run();
			if (arr.get(taskNum).isFinished()) {
				taskNum++;
			}
		}
		SmartDashboard.putNumber("taskNum", taskNum);
	}
	
	
	/**
	 * Temporary method to test out all the auton actions.
	 */
	public void testCode(){
		arr.add(new ActionIntake(10));
		arr.add(new ActionShooter(2083, 23));
		arr.add(new ActionParallelAction(new ActionIntake(2), new ActionShooter(2334234 , 234)));
		arr.add(new ActionWait(69420)); //nice
		arr.add(new ActionDriveStraightByEncoders(40, 0.5, 30));
		arr.add(new ActionConveyorBelt(24));
		arr.add(new ActionTurnToAngle(40, false, 5.0f));
		
				
	}
	
	public void assembleDoNothing(){
		arr.add(new ActionWait(9999));
	}

//change all the shooter numbers, 20 20 is just random ofc
	public void assemblePR_justShoot(){

		arr.add(new ActionParallelAction(new ActionConveyorBelt(10), new ActionShooter(200, 10)));
		

	}

	public void assemblePR_shootFoward(){

		
		arr.add(new ActionParallelAction(new ActionConveyorBelt(10), new ActionShooter(200, 10)));
		arr.add(new ActionDriveStraightByEncoders(2, 0.2, 10));	
	}

	public void assemblePR_shootBackward(){

		arr.add(new ActionShooter(20, 20));
		arr.add(new ActionDriveStraightByEncoders(-2, 0.2, 10));
		
	}

	public void assemblePR_shootTrench(){

		arr.add(new ActionShooter(20, 20));
		arr.add(new ActionDriveStraightByEncoders(5, 0.2, 15));
		
	}




	public void assembleLR_justShoot(){

		arr.add(new ActionParallelAction(new ActionShooter(200, 10), new ActionConveyorBelt(10)));

	}

	public void assembleLR_drivePPShoot(){
		arr.add(new ActionDriveStraightByEncoders(2, 0.2, 15));
		arr.add(new ActionParallelAction(new ActionShooter(200, 10) , new ActionConveyorBelt(10)));
	}

	public void assembleLR_midDriveShoot(){
		arr.add(new ActionDriveStraightByEncoders(1, 0.2, 15));
		arr.add(new ActionParallelAction(new ActionShooter(200 , 10) , new ActionConveyorBelt(10)));
	}

	
	
	public void assembleRR_JustShoot(){
	arr.add(new ActionShooter(20 , 5));
		
	}

	public void assembleRR_ShootTrenchShoot(){
		arr.add(new ActionShooter(200, 20));
		arr.add(new ActionDriveStraightByEncoders(5, 0.2, 10));		
		arr.add(new ActionShooter(200, 20));
	}

	public void assembleRR_DrivePickShoot(){
		arr.add(new ActionDriveStraightByEncoders(50, 0.5, 30));
		
	arr.add(new ActionParallelAction(new ActionShooter(200,10), new ActionConveyorBelt(10)));																																																																																																																														``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````ActionParallelAction(new ActionShooter(20, 20), new ActionConveyorBelt(20)));
	}
	
	//preload 2
	public void assembleRR_Preload2DrivePickShoot() {
		arr.add(new ActionConveyorBelt(40));
		arr.add(new ActionDriveStraightByEncoders(5, 0.2, 10));
		arr.add(new ActionParallelAction(new ActionShooter(20, 20), new ActionConveyorBelt(20)));
	}

}