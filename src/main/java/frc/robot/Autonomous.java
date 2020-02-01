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
import frc.robot.Action.ActionIntake;


public class Autonomous {

	ArrayList<Action> arr = new ArrayList<Action>();
	int taskNum = 0;
	
	public Autonomous() {
	}

	enum AutoStates {
		Default, RightPeg, LeftPeg, MidPeg, rBoiler, bBoiler, rHopper, rShootAlign, shoot, bHopper, bBoilerAutoline, rBoilerAutoline, rCenterShoot, bCenterShoot,scoreOpRight, scoreOpLeft, scoreOpRightCent, scoreOpLeftCent, bShootAlign;
	}

	public void run() {
		if (arr.size() >= taskNum) {
			arr.get(taskNum).run();
			if (arr.get(taskNum).isFinished()) {
				taskNum++;
			}
		}
		
	}

	public void assembleTest() {
		//arr.add(new ActionTrajectory("Straight", 0, 0.01, false));
        //arr.add(new ActionWait(9999999));
        
        arr.add(new ActionIntake(10));
	}

}