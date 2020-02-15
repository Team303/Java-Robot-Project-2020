/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class Climber{

public CANSparkMax climber ;
public CANSparkMax climberSlave;
public Solenoid climberDeploy ;

CANPIDController climberPID; 
CANEncoder climberEncoder;

public double setPoint;
public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

public static final int GEARING = 1;
double encoderConstant = (1 / GEARING) *1;


public  Climber(){

    climber = new CANSparkMax(RobotMap.CLIMBER, MotorType.kBrushless );
    climberSlave = new CANSparkMax(RobotMap.CLIMBER_SLAVE, MotorType.kBrushless);
    climberDeploy = new Solenoid(RobotMap.CLIMBER_FOWARD);
    //

    climberPID = climber.getPIDController();
    climberEncoder = climber.getEncoder();

    climberEncoder.setPositionConversionFactor(1000);

    climber.restoreFactoryDefaults();
    climberSlave.restoreFactoryDefaults();

    climber.setInverted(RobotMap.CLIMBER_INV);
    climberSlave.setInverted(RobotMap.CLIMBER_SLAVE_INV);

    climberSlave.follow(climber); 
   
     kP = SmartDashboard.getNumber("Climber P", 0.3);
	 kI = SmartDashboard.getNumber("Climber I", 0.0);
     kD = SmartDashboard.getNumber("Climber D", 1.0);
     kFF = SmartDashboard.getNumber("Climber FF", 1.0); 
     kIz = SmartDashboard.getNumber("Climber kIz", 1.0);
     kMaxOutput = SmartDashboard.getNumber("Climber kMaxOutput", 1.0);
     kMinOutput = SmartDashboard.getNumber("Climber kMinOutput", 1.0);

     climberPID.setP(kP);
     climberPID.setI(kI);
     climberPID.setD(kD);
     climberPID.setFF(kFF);
     climberPID.setIZone(kIz);
     climberPID.setOutputRange(kMinOutput, kMaxOutput);

     zeroEncoders();
     setPoint = 0;
   
    }


    //------------------------------------------------------------------controller control----------------------------------------------------------------
    public void control(){
            
            if (OI.xlY < -0.3) { //
                setSetPoint(getSetPoint() + 400);
                //setclimberPower(0.5);
            } else if (OI.xlY > 0.3 && getSetPoint() >= -100){
                setSetPoint(getSetPoint() - 400);
                //setclimberPower(-0.25);
            } else {
                //setclimberPower(0);
            }
                      
            int[] climberSetpoint = new int[] {RobotMap.LOW_SWITCH_SETPOINT, 
                RobotMap.MID_SWITCH_SETPOINT, RobotMap.HIGH_SWITCH_SETPOINT};
          /*  if (OI.xLeftTrigger <= 0.75) 
                climberSetpoint = new int[] {RobotMap.LOW_SWITCH_SETPOINT, 
                RobotMap.MID_SWITCH_SETPOINT, RobotMap.HIGH_SWITCH_SETPOINT};*/
            
            
            if (OI.xPov == 0) { //Up
                setSetPoint(climberSetpoint[2]);
            } else if (OI.xPov == 180) { //Down
                setSetPoint(0);
            } else if (OI.xPov == 90) { //Right
                setSetPoint(climberSetpoint[1]);
            } else if (OI.xPov == 270) { //Left
                setSetPoint(climberSetpoint[0]);
            } 
    
            setClimberPID();        

    }

    public void zeroEncoders(){
        climberEncoder.setPosition(0);
        
     }


    public void deployClimber(Boolean state){
        climberDeploy.set(state);
    }

    public void setSetPoint(double setPoint){
        this.setPoint = setPoint;
    }
    
    public double getSetPoint() {
        return setPoint;
    }



    public double getSpeed(){
        return climberEncoder.getVelocity();

    }
    
    public double getVelocity(){
        return climberEncoder.getVelocity()* encoderConstant / 60.;
    }


    public void setClimberPID(){        

        climberPID.setReference(setPoint, ControlType.kPosition);

    }

}