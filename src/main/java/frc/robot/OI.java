/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Add your docs here.
 */
public class OI {

    // initiating Joysticks
    public static Joystick left = new Joystick(0);
    public static Joystick right = new Joystick(1);

    //initiating xbox controler 
    public static XboxController xboxController = new XboxController(3);

    // initiating the left and right axis
    public static double leftX = 0, leftY = 0, leftZ = 0;
    public static double rightX = 0, rightY = 0, rightZ = 0;

    //Xbox buttons
    public static boolean xBtnY, xBtnX;


    public  void update(){
        //getting axis values 
        leftX = left.getX();
        leftY = left.getY();
        leftZ = left.getZ();

        rightX = right.getX();
        rightY = right.getY();
        rightZ = right.getZ();


        xBtnX = xboxController.getXButton();
        xBtnY = xboxController.getYButton();
    }
}
