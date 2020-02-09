package frc.robot.Action;
//package org.usfirst.frc.team303.robot.Action;
import java.util.ArrayList;
import java.util.Arrays;

public class ActionParallelAction implements Action {
	ArrayList<Action> conditionActions=new ArrayList<Action>();//actions that control when it is finished
	ArrayList<Action> nonConditionActions=new ArrayList<Action>(); //actions that run and do not control when finished
	
	//varargs magic
	public ActionParallelAction(Action conditionalAction, Action... nonConditionalActions) {
		conditionActions.add(conditionalAction);
		nonConditionActions.addAll(Arrays.asList(nonConditionalActions));
	}
	
	public ActionParallelAction(ArrayList<Action> conditionActionsC, ArrayList<Action> nonConditionActionsC){
		conditionActions=conditionActionsC;
		nonConditionActions=nonConditionActionsC;
	}
	//alternate constructor for when you only have conditional actions
	public ActionParallelAction(ArrayList<Action> conditionActionsC){
		this(conditionActionsC,new ArrayList<Action>());
	}
	//runs all actions
	public void run(){
		for(Action e:conditionActions){
			e.run();
		}
		for(Action e:nonConditionActions){
			e.run();
		}
	}
	//checks if all conditional actions are finished
	public boolean isFinished(){
		for(Action e:conditionActions){
			if(!e.isFinished())
				return false;
		}
		return true;
		
	}
}