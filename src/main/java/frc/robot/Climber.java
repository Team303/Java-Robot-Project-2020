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


public Climber(){

    climber = new CANSparkMax(RobotMap.CLIMBER, MotorType.kBrushless );
    climberSlave = new CANSparkMax(RobotMap.CLIMBER_SLAVE, MotorType.kBrushless);
    climberDeploy = new Solenoid(RobotMap.CLIMBER_SOLENOID);
    

    climber.setInverted(RobotMap.CLIMBER_INV);
    climberSlave.setInverted(RobotMap.CLIMBER_SLAVE_INV);
    

    climberEncoder = climber.getEncoder();

    zeroEncoders();
    setPoint = 0;

    deployClimber(false);

   
    }


    //------------------------------------------------------------------controller control----------------------------------------------------------------
    public void control(){ 

        if ((OI.xlY >=0.2 || OI.lBtn[5]) && getPosition() >=0) {
            setClimber(-0.5);
        } else if ((OI.xlY <=-0.2 || OI.lBtn[3] ) && getPosition() <= 120) {
            setClimber(0.5);
        } else {
            setClimber(0);
        }

        if (OI.lBtn[5]) {
            deployClimber(false);
        } else if (OI.lBtn[3]) {
            deployClimber(true);
        }
            
    }

    public void setClimber(double power) {
        climber.set(power);
        climberSlave.set(power);
    }

    public void zeroEncoders(){
        climberEncoder.setPosition(0);
    }

    public double getPosition() {
        return climberEncoder.getPosition();
    }

    public void deployClimber(Boolean state){
        climberDeploy.set(state);

    }



    public double getSpeed(){
        return climberEncoder.getVelocity();

    }

    public void setClimberPID(){        
        climberPID.setReference(setPoint, ControlType.kPosition);
    }

          /*
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
            if (OI.xLeftTrigger <= 0.75) 
                climberSetpoint = new int[] {RobotMap.LOW_SWITCH_SETPOINT, 
                RobotMap.MID_SWITCH_SETPOINT, RobotMap.HIGH_SWITCH_SETPOINT};
            
            
            if (OI.xPov == 0) { //Up
                setSetPoint(climberSetpoint[2]);
            } else if (OI.xPov == 180) { //Down
                setSetPoint(0);
            } else if (OI.xPov == 90) { //Right
                setSetPoint(climberSetpoint[1]);
            } else if (OI.xPov == 270) { //Left
                setSetPoint(climberSetpoint[0]);
            } 
    
            setClimberPID();    */    


}