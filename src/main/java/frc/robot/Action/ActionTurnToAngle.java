package frc.robot.Action;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ActionTurnToAngle implements Action {
	
	double fSetpoint; //final setpoint to feed to controller
	double iSetpoint;
	boolean firstRun = true;
	boolean relativeYaw;
	int counter = 0;
	float tolerance;
	boolean pivot;
	double pivotPower;
	double timeout = 2.0;
	boolean pivotDirection;
	//we added these two things today 
	double angleToTurn = 180;
	double errorDivisor;
	Timer timer;
	
	public ActionTurnToAngle(double setpoint, boolean relative, float tolerance) { //pivot is assumed false
		this(setpoint, relative, tolerance, false, 1, false);
	}

	public ActionTurnToAngle(double setpoint, boolean relative, float tolerance, double timeout) { //pivot is assumed false
		this(setpoint, relative, tolerance, false, 1, false);
	}
	
	public ActionTurnToAngle(double setpoint, boolean relative, float tolerance, boolean pivot, double pivotPower, boolean pivotDirection) {
		this.angleToTurn = setpoint;
		firstRun = true;
		this.tolerance = tolerance;
		iSetpoint = setpoint;
		relativeYaw = relative;
		this.pivot = pivot;
		this.pivotPower = pivotPower;
		this.pivotDirection = pivotDirection;
		timer = new Timer();
		this.timeout = 2.0;
	}
	
	@Override
	public void run() {

		if (firstRun) {
			timer.reset();
			timer.start();
			firstRun = false;
		}
		
		/*if(firstRun) {
			double theta = Robot.navX.getYaw();
			fSetpoint = relativeYaw ? theta+iSetpoint : iSetpoint;
			
			if(relativeYaw) {				
				if(fSetpoint>180){
					fSetpoint -= 360;
				} else if(fSetpoint<-180){
					fSetpoint+=360;	
				}
			}
			
			SmartDashboard.putNumber("Auto NavX Setpoint", fSetpoint);
			Robot.navX.setSetpoint(fSetpoint);
			Robot.navX.turnController.enable();
		}
		
		double output = Robot.navX.getPidOutput();
		
		if(pivot) {
			if(pivotDirection) {
				Robot.drivebase.drive(-pivotPower*output, output);
			}
			else {
				Robot.drivebase.drive(-output, pivotPower*output);
			}
		} else {
			Robot.drivebase.drive(-output, output);	
		}
		*/


		//0.6
		//0.55
		//0.55
		 
		/* original code
		double error = 90 - Robot.navX.getYaw();
		double power = Math.pow(error/60, 0.666);
		power = 0.666 * power;
		power = Math.min(power, 0.55);
		
		Robot.drivebase.drive(power, -power);
		*/
		
		if (Math.abs(angleToTurn) < 45){
			errorDivisor = 22; //28
		}
		else{
			errorDivisor = 60;
		}
		

		double divisor = SmartDashboard.getNumber("NavX Divisor", 60);
		double exponent = SmartDashboard.getNumber("NavX Exponent", 0.66);


		double error = angleToTurn - Robot.navX.getYaw();
		double power = Math.pow(Math.abs(error)/errorDivisor, 0.66);
		power = 0.55 * power;
		power = Math.min(power, 0.55);
		power = Math.copySign(power, error);


		SmartDashboard.putNumber("NavX Power", power);
		SmartDashboard.putNumber("NavX Error", error);

		Robot.drivebase.drive(power, -power);	


		//SmartDashboard.putNumber("NavX PID Output", output);
		
		firstRun = false;
	}
	
	@Override
	public boolean isFinished() {
		double yaw = Robot.navX.getYaw();
		double setpoint = Robot.navX.turnController.getSetpoint();
		setpoint = -setpoint;
		
		
		boolean end = Math.abs(yaw - setpoint) <= tolerance;		
		boolean end2 = false;
		
		SmartDashboard.putBoolean("Ended", end);

	
		if(end) {
			counter++;
		} else {
			counter = 0;
		}
		
		if(counter>=6) {
			end2 = true;
		} else {
			end2 = false;
		}
		
		if(end) {
			//firstRun = true;
			Robot.navX.turnController.disable();
		}

		SmartDashboard.putBoolean("End", end);
		boolean end3 = (Math.abs(Math.abs(angleToTurn) - Math.abs(yaw)) <= tolerance) || (timer.get() >= timeout);		

		return end3;
	}
	
}