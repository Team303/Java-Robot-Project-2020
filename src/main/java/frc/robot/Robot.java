/**
 * This is a very simple robot program that can be used to send telemetry to
 * the data_logger script to characterize your drivetrain. If you wish to use
 * your actual robot code, you only need to implement the simple logic in the
 * autonomousPeriodic function and change the NetworkTables update rate
 */

package frc.robot;


import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {

  static private int PIDIDX = 0;

  Joystick left;
  Joystick right;

  DifferentialDrive drive;
  WPI_TalonFX leftMaster;
  WPI_TalonFX rightMaster;

  @Override
  public void robotInit() {

    left = new Joystick(0);
    right = new Joystick(1);


    leftMaster = new WPI_TalonFX(2);
    leftMaster.setInverted(false);
    leftMaster.setSensorPhase(false);
    leftMaster.setNeutralMode(NeutralMode.Brake);

    rightMaster = new WPI_TalonFX(5);
    rightMaster.setInverted(true);
    rightMaster.setSensorPhase(false);
    rightMaster.setNeutralMode(NeutralMode.Brake);

    WPI_TalonFX leftSlave0 = new WPI_TalonFX(3);
    leftSlave0.setInverted(false);
    leftSlave0.follow(leftMaster);
    leftSlave0.setNeutralMode(NeutralMode.Brake);
    WPI_TalonFX leftSlave1 = new WPI_TalonFX(4);
    leftSlave1.setInverted(false);
    leftSlave1.follow(leftMaster);
    leftSlave1.setNeutralMode(NeutralMode.Brake);

    WPI_TalonFX rightSlave0 = new WPI_TalonFX(6);
    rightSlave0.setInverted(true);
    rightSlave0.follow(rightMaster);
    rightSlave0.setNeutralMode(NeutralMode.Brake);
    WPI_TalonFX rightSlave1 = new WPI_TalonFX(7);
    rightSlave1.setInverted(true);
    rightSlave1.follow(rightMaster);
    rightSlave1.setNeutralMode(NeutralMode.Brake);

    drive = new DifferentialDrive(leftMaster, rightMaster);

    drive.setDeadband(0);

    leftMaster.configSelectedFeedbackSensor(
        FeedbackDevice.IntegratedSensor,
        PIDIDX, 10
    );
	
    rightMaster.configSelectedFeedbackSensor(
        FeedbackDevice.IntegratedSensor,
        PIDIDX, 10
    );
	
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);

  }


  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    drive.tankDrive(-left.getY(), right.getY());
  }


}
