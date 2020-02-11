/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the PPoject.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Action.Action;
import frc.robot.Action.ActionIndexer;
import frc.robot.Action.ActionDriveStraightByEncoders;
import frc.robot.Action.ActionIntake;
import frc.robot.Action.ActionShooter;
import frc.robot.Action.ActionWait;
import frc.robot.Action.ActionParallelAction;
import frc.robot.Action.ActionTurnToAngle;
import frc.robot.Action.ActionTurnToTarget;



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
	

	public void add(Action action) {
		arr.add(action);
	}
	
	public void assembleDoNothing(){
		arr.add(new ActionWait(9999));
	}

	//---------------------------  MAIN AUTOS ------------------------------------------

	public void assemblePP_simpleShoot(){
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.INITIATION_LINE, 15));
	}

	public void assembleLL_simpleShoot(){
		arr.add(new ActionDriveStraightByEncoders(100000, 0.5, 10));	
		arr.add(new ActionTurnToTarget(2.0));	
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.TRENCH_RUN, 10));
	}

	public void assembleRR_simpleShoot(){
		arr.add(new ActionDriveStraightByEncoders(100000, 0.5, 10));	
		arr.add(new ActionTurnToTarget(2.0));	
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.TRENCH_RUN, 10));
	}

	public void assemblePP_shootFoward(){
		arr.add(new ActionParallelAction(new ActionIndexer(10), new ActionShooter(true, 200, 10)));
		arr.add(new ActionDriveStraightByEncoders(2, 0.2, 10));	
	}

	public void assemblePP_shootBackward(){

		arr.add(new ActionShooter(true, 20, 20));
		arr.add(new ActionDriveStraightByEncoders(-2, 0.2, 10));
		
	}

	public void assemblePP_shootTrench(){
		arr.add(new ActionShooter(true,20, 20));
		arr.add(new ActionDriveStraightByEncoders(5, 0.2, 15));
	}


		//---------------------------  OTHER AUTOS ------------------------------------------

	public void assembleLL_drivePPShoot(){
		arr.add(new ActionDriveStraightByEncoders(2, 0.2, 15));
		arr.add(new ActionParallelAction(new ActionShooter(true, 200, 10) , new ActionIndexer(10)));
	}


	public void assembleLL_midDriveShoot(){
		arr.add(new ActionDriveStraightByEncoders(1, 0.2, 15));
		arr.add(new ActionParallelAction(new ActionShooter(true, 200 , 10) , new ActionIndexer(10)));
	}

	public void assembleRR_ShootTrenchShoot(){
		arr.add(new ActionShooter(true, 200, 20));
		arr.add(new ActionDriveStraightByEncoders(5, 0.2, 10));		
		arr.add(new ActionShooter(true, 200, 20));
	}
			
	public void assembleRR_DrivePickShoot(){
		arr.add(new ActionDriveStraightByEncoders(50, 0.5, 30));
		//arr.add(new ActionParallelAction (new ActionShooter(200 , 10), new ActionIndexer(10) ));																																																																																																																														``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````ActionParallelAction(new ActionShooter(20, 20), new ActionIndexer(20)));
	}
	
	

	public void assembleRR_PPeload2DrivePickShoot() {
		arr.add(new ActionIndexer(40));
		arr.add(new ActionDriveStraightByEncoders(5, 0.2, 10));
		arr.add(new ActionParallelAction(new ActionShooter(true, 20, 20), new ActionIndexer(20)));
	}

	public void testCode(){
		arr.add(new ActionIntake(10));
		arr.add(new ActionShooter(true, 2083, 23));
		arr.add(new ActionParallelAction(new ActionIntake(2), new ActionShooter(true, 2334234 , 234)));
		arr.add(new ActionWait(69420)); //nice
		arr.add(new ActionDriveStraightByEncoders(40, 0.5, 30));
		arr.add(new ActionIndexer(24));
		arr.add(new ActionTurnToAngle(40, 5.0f));			
	}
	

	
}