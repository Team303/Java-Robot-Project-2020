/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;



import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter{

	 CANSparkMax shooter;
	 CANSparkMax shooterSlave;
	 double savedSetpoint;
	 int count = 0;
	
	 static final double maxFeedError = 0.15; //.15
	
	 public Shooter() {
	 	shooter = new CANSparkMax(RobotMap.SHOOTER, CANSparkMaxLowLevel.MotorType.kBrushless);
		shooterSlave = new CANSparkMax(RobotMap.SHOOTER_SLAVE, CANSparkMaxLowLevel.MotorType.kBrushless);

		/*
		 //shooter.set(ControlMode.PercentOutput, 9.);
        setPIDF(shooter, .3, 0.000003, 1, 0.02475);
		//shooter.setSafetyEnabled(true);
		shooter.getInverted();
		shooter.set(0);
		t = new Timer();*/
	}
	
	public void control() {
		
		/*double setpoint;
		
		
		if(OI.xBtnY) { //set setpoint
			setpoint = 0;
			//shooter.disable();
			//shooterSlave.disable();
		} else if(OI.xBtnX) {
			//shooter.enable();
			//shooterSlave.enable();
			setpoint =20250; // was -26150
		} else {
			setpoint = savedSetpoint;
		}
		
		setSetpoint(setpoint);
		
		if(setpoint!=0 && (getSpeed()<=(setpoint*(1+maxFeedError)) && getSpeed()>=(setpoint*(1-maxFeedError)))) { //feed fuel if shooter is close to setpoint
		savedSetpoint = setpoint;*/
	}
	
	public void setSetpoint(double setpoint) {
		//shooter.set(setpoint);
	}
	
	public double getSpeed() {
		//return shooter.getSelectedSensorVelocity();
		return 0; //Silences the compiler
	}
	
	/*public void resetI() {
		shooter.clearIAccum();
	}*/
	
	public void setPIDF(WPI_TalonSRX motor, double P, double I, double D, double F) {
		motor.config_kP(0 , P);
		motor.config_kI(0 , I);
		motor.config_kD(0 , D);
		motor.config_kF(0 , F);
	}
	
}
