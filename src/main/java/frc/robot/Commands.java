/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Add your docs here.
 */
public class Commands {


    private final Drivebase m_robotDrive = new Drivebase();

    public Command getAutonomousCommand() throws Exception {

        double lP = SmartDashboard.getNumber("lP value", 7.77);
        double lI = SmartDashboard.getNumber("lI value", 0.0);
        double lD = SmartDashboard.getNumber("lD value", 0.0);
        double rP = SmartDashboard.getNumber("rP value", 7.77);
        double rI = SmartDashboard.getNumber("rI value", 0.0);
        double rD = SmartDashboard.getNumber("rD value", 0.0);

        double kS = SmartDashboard.getNumber("kS", RobotMap.ksVolts);
        double kV = SmartDashboard.getNumber("kV", RobotMap.kvVoltSecondsPerMeter);
        double kA = SmartDashboard.getNumber("kA", RobotMap.kaVoltSecondsSquaredPerMeter);

        double maxVelocity = SmartDashboard.getNumber("Max Velocity", 3.0);
        double maxAcceleration = SmartDashboard.getNumber("Max Acceleration", 3.0);

        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(kS, kV, kA), RobotMap.kDriveKinematics, 10);

        TrajectoryConfig config = new TrajectoryConfig(maxVelocity, maxAcceleration)
        .setKinematics(RobotMap.kDriveKinematics);
        //.addConstraint(autoVoltageConstraint);
    
        /*
        Trajectory trajectory = null;

        try {
                trajectory = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/output/Straight.wpilib.json"));
        } catch(Exception e){
            System.out.println("Path not found.");
        } */ 

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            List.of(
                new Pose2d(0,0,Rotation2d.fromDegrees(0)),
                new Pose2d(3,0,Rotation2d.fromDegrees(0))
            ),
            config
        );
        
        /*
        RamseteCommand ramseteCommand = new RamseteCommand(
            trajectory,
            m_robotDrive::getPose,
            new RamseteController(2,0.7),
            new SimpleMotorFeedforward(kS, kV, kA),
            RobotMap.kDriveKinematics,
            m_robotDrive::getWheelSpeeds,
            new PIDController(lP, lI, lD),
            new PIDController(rP, rI, rD),
            m_robotDrive::setOutputVolts,
            m_robotDrive
        );*/


        //return ramseteCommand.andThen(() -> m_robotDrive.setOutputVolts(0, 0));
        return null;
    } 

}