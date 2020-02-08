/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;
import edu.wpi.first.wpilibj.I2C;

public class ColorSensor{
    private final ColorSensorV3 colorSensor;
    enum Color
    {
        RED, GREEN, BLUE, YELLOW, NONE
    }
    public ColorSensor(){
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
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
    
}