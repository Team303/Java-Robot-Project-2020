/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;

import java.util.Arrays;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;

public class ColorSensor{
    private final ColorSensorV3 colorSensor;
    public CANSparkMax controlMotor; // motor for control panel movement

    private boolean rotateContinue;

    enum Color
    {
        RED, GREEN, BLUE, YELLOW, NONE
    }
    public ColorSensor(){
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        controlMotor = new CANSparkMax(RobotMap.CONTROL_PANEL_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        // !REMINDER --> MAKE SURE TO SET MOTOR TO BRAKE
    }

    // Functioning Methods ------------------------------------------------------------------------
    public void rotationControl(){
        rotateContinue = true; // checks if motor should continue rotations
        Color startColor = getColor(); // gets the starting color that should be rotated to
        boolean onColor = true; // checks if sensor is currently on the required color
        boolean start = true; // checks if sensor is still on start color
        int rotations = 0; // how many rotations have been done, the motor will stop right after 3
        
        while (rotations < 6){
            if (start){ 
                // this will only run while sensor is still on starting color for first time
                if (getColor().equals(startColor) == false){
                    onColor = false;
                    start = false;
                }
            } else if (!onColor){
                // runs while sensor is rotating on other colors
                if (getColor().equals(startColor)){
                    onColor = true;
                }
            } else if (onColor && !start){
                // this will run any other time, sensor is on start color
                if (getColor().equals(startColor) == false){
                    rotations++;
                    onColor = false;
                }
            }
            controlMotor.set(0.5);
        }
        controlMotor.set(0);
    }    

    public void positionControl(){
        // GET THE FMS COLOR \\
        Color fmsColor = getFMSColor();
        /* while(fmsColor.equals(Color.NONE)){
            fmsColor = getFMSColor();
        } */
        Color[] allColors = new Color[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};
        Color toStop = getStopColor(fmsColor, allColors);        
        while(getColor().equals(toStop) == false){
            // while the sensor does not see the fms color
            controlMotor.set(0.5);
        }
        controlMotor.set(0);
    }


    // Helper methods --------------------------------------------------------------------------------
    public boolean checkForRotation(int rotations){ 
        // external helper method to determine if should keep rotating
        if (rotations == 3){
            return false;
        }
        return true;
    }

    public Color getStopColor(Color fmsColor, Color[] allColors){
        int stopIndex= Arrays.asList(allColors).indexOf(fmsColor);
        if (stopIndex == 0){
            return allColors[allColors.length - 1];
        }
        else{
            return allColors[stopIndex - 1];
        }
    }

    public RawColor getRawColor(){
        //return a Raw Color object, which has int attributes for red, green, blue, and infrared
        return colorSensor.getRawColor();
    }

    public Color getColor()
    {
        RawColor color = getRawColor();
        //These constants decide what is considered "full enough" or "empty enough" for a given value, since RGB values are 0 or 250
        final int FULL = 250;
        final int EMPTY = 5;

        if(color.red<= EMPTY && color.green>=FULL && color.blue>=FULL) return Color.BLUE;
        else if(color.red>=FULL && color.green>=FULL && color.blue<=EMPTY) return Color.YELLOW;
        else if(color.red <= EMPTY && color.green>=FULL && color.blue<=EMPTY ) return Color.GREEN;
        else if(color.red>=FULL && color.green<=EMPTY && color.blue<=EMPTY ) return Color.RED;
        else return Color.NONE;
    }

    public Color getFMSColor(){
        Color color;
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0){
            switch(gameData.charAt(0)){
                case 'B':
                    color = Color.BLUE; break;
                case 'G':
                    color = Color.GREEN; break;
                case 'R':
                    color = Color.RED; break;
                case 'Y':
                    color = Color.YELLOW; break;
                default:
                    System.out.println("CORRUPT GAME DATA!!!");
                    color = Color.NONE; 
                    break;
            }
        }
        else{
            System.out.println("NO GAME DATA RECEIVED YET");
            color = Color.NONE;
        }
        return color;
    }

}