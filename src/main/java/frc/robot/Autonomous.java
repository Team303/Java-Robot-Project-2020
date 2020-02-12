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
import frc.robot.Action.ActionDriveStraightByEncoders;
import frc.robot.Action.ActionIndexer;
import frc.robot.Action.ActionIntake;
import frc.robot.Action.ActionShooter;
import frc.robot.Action.ActionTrajectory;
import frc.robot.Action.ActionWait;
import frc.robot.Action.ActionParallelAction;
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
	
	
	/**
	 * Temporary method to test out all the auton actions.
	 */
	public void testCode(){
		arr.add(new ActionIntake(10));
		arr.add(new ActionIndexer(3));
		arr.add(new ActionShooter(true, 2083, 23));
		arr.add(new ActionParallelAction(new ActionIntake(2), new ActionShooter(true, 2334234 , 234)));
		arr.add(new ActionWait(69420)); //nice
		arr.add(new ActionDriveStraightByEncoders(40, 0.5, 30));
		arr.add(new ActionTurnToTarget(2));
		
				
	}
	
	public void assembleDoNothing(){
				
		arr.add(new ActionWait(Double.MAX_VALUE));
	}

//--------------------------------------------------------------PP----------------------------------------------------------------------
	

	public void assemblePP_shootFoward(){
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.INITIATION_LINE, 10));
		arr.add(new ActionDriveStraightByEncoders(2, 0.2, 10));	
	}

	public void assemblePP_shootBackward(){
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.INITIATION_LINE, 10));
		arr.add(new ActionDriveStraightByEncoders(-2, 0.2, 10));
	}

	public void assemblePP_shootTrench(){
		arr.add(new ActionShooter(true, 200, 10));
		arr.add(new ActionParallelAction(new ActionTrajectory("PP to Trench", 10), new ActionIntake(10)));
	}
	
	
//-------------------------------------------------------------Left---------------------------------------------------------------------
	
	
	public void assembleLL_shootForward(){
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.INITIATION_LINE, 10));
		arr.add(new ActionDriveStraightByEncoders(2, 0.2, 15));	
	}


	public void assembleLL_shootBackward(){
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.INITIATION_LINE, 10));
		arr.add(new ActionDriveStraightByEncoders(-2, 0.2, 15));
		
	}


	public void assembleLL_drivePPShoot(){
		arr.add(new ActionTrajectory("Left to PP", 10));
		arr.add(new ActionTurnToTarget(2));
		arr.add(new ActionShooter(true, 200, 10));
	}

	
	public void assembleLL_drivePPShootTrench(){
		arr.add(new ActionTrajectory("Left to PP", 10));
		arr.add(new ActionTurnToTarget(2));
		arr.add(new ActionShooter(true, 200, 10));
		arr.add(new ActionParallelAction(new ActionTrajectory("PP to Trench", 10), new ActionIntake(10)));
	}

	
	public void assembleLL_driveMidShoot(){
		arr.add(new ActionTrajectory("Left to Middle", 10));
		arr.add(new ActionTurnToTarget(2));
		arr.add(new ActionShooter(true, 200, 10));
	}


	public void assembleLL_driveMidShootTrench(){
		arr.add(new ActionTrajectory("Left to Middle", 10));
		arr.add(new ActionTurnToTarget(2));
		arr.add(new ActionShooter(true, 200, 10));
		arr.add(new ActionParallelAction(new ActionTrajectory("Middle to Trench", 10), new ActionIntake(10)));
	}

	
//------------------------------------------------------------Right---------------------------------------------------------------------

	public void assembleRR_shootForward(){
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.INITIATION_LINE, 10));
		arr.add(new ActionDriveStraightByEncoders(1, 0.2, 15));
	}

	public void assembleRR_shootBackward(){
		arr.add(new ActionShooter(false, Camera.VelocitySetpoint.INITIATION_LINE, 10));
		arr.add(new ActionDriveStraightByEncoders(-1, 0.2, 15));
	}

public void assembleRR_shootTrenchShoot(){
		arr.add(new ActionShooter(true, 200, 20));
		arr.add(new ActionParallelAction(new ActionTrajectory("Right to Trench", 10), new ActionIntake(10)));		
		arr.add(new ActionTurnToTarget(2));
		arr.add(new ActionShooter(true, 200, 20));
	}

	public void assembleRR_driveTrenchShoot(){
		arr.add(new ActionParallelAction(new ActionTrajectory("Right to Trench", 10), new ActionIntake(10)));
		arr.add(new ActionTurnToTarget(2));
		arr.add(new ActionShooter(true, 200, 20));
		
	}

	
	


}