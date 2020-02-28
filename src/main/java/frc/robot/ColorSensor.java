/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.ColorSensorV3.RawColor;

import java.util.Arrays;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ColorSensor{
    private final ColorSensorV3 colorSensor;
    public CANSparkMax controlMotor;
    public final ColorMatch colorMatcher = new ColorMatch();

    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);


    enum FieldColor {
        RED, GREEN, BLUE, YELLOW, NONE, UNKNOWN
    }

    public ColorSensor(){
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        controlMotor = new CANSparkMax(RobotMap.CONTROL_PANEL, MotorType.kBrushless);
        controlMotor.setInverted(RobotMap.CONTROL_PANEL_INV);
        controlMotor.setIdleMode(IdleMode.kBrake);

        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget); 

    }

    public void testing() {
        SmartDashboard.putNumber("Color Red", colorSensor.getRed());
        SmartDashboard.putNumber("Color Blue", colorSensor.getBlue());
        SmartDashboard.putNumber("Color Green", colorSensor.getGreen());
        SmartDashboard.putString("Current Color", getCurrentColor().toString());
        SmartDashboard.putString("CURRENT COLOR", getCurrentColor().toString());
        SmartDashboard.putString("FMS COLOR", getFMSColor().toString());
    }


    public void control() {
        if (OI.rBtn[3]) {
            controlMotor.set(0.3);
        } else if (OI.rBtn[5]) {
            controlMotor.set(-0.3);
        } else {

            if (OI.xLeftBumper) {
                positionControl();
            } else if (OI.xRightBumper) {
                rotationControl();
            } else {
                controlMotor.set(0);
            }
        }


    }

    public FieldColor getCurrentColor() {
        Color detectedColor = colorSensor.getColor();
        String colorString;
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
    
        if (match.color == kBlueTarget) {
          return FieldColor.BLUE;
        } else if (match.color == kRedTarget) {
            return FieldColor.RED;
        } else if (match.color == kGreenTarget) {
            return FieldColor.GREEN;
        } else if (match.color == kYellowTarget) {
            return FieldColor.YELLOW;
        } else {
            return FieldColor.UNKNOWN;
        }


    }

    public void rotationControl(){
        FieldColor startColor = getCurrentColor(); 
        boolean onStartColor = true; 
        boolean started = false; 
        int rotations = 0; 
        
        if (rotations < 6){
            if (!started){ 
                // this will only run while sensor is still on starting color for first time
                if (!getCurrentColor().equals(startColor)){
                    onStartColor = false;
                    started = true;
                }
            } else if (!onStartColor){
                // runs while sensor is rotating on other colors
                if (getCurrentColor().equals(startColor)){
                    onStartColor = true;
                }
            } else if (onStartColor && started){
                // this will run any other time, sensor is on start color
                if (getCurrentColor().equals(startColor) == false){
                    rotations++;
                    onStartColor = false;
                }
            }
            controlMotor.set(0.4);
        } else {
            controlMotor.set(0);
        }

    }    

    public void positionControl(){
        FieldColor fmsColor = getFMSColor();
        //FieldColor[] allColors = new FieldColor[]{FieldColor.RED, FieldColor.YELLOW, FieldColor.BLUE, FieldColor.GREEN};
        //FieldColor toStop = getStopColor(fmsColor, allColors);  


        if (!getCurrentColor().equals(fmsColor)){
            controlMotor.set(0.2);
        } else {
            controlMotor.set(0);
        }
    }

    public Color getStopColor(Color fmsColor, Color[] allColors){

        int stopIndex= Arrays.asList(allColors).indexOf(fmsColor);

        if (stopIndex == 0){
            return allColors[allColors.length - 1];
        } else{
            return allColors[stopIndex - 1];
        }
    }


    public FieldColor getFMSColor(){
        FieldColor color;
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        
        if (gameData.length() > 0){
            switch(gameData.charAt(0)){
                case 'B':
                    color = FieldColor.BLUE; break;
                case 'G':
                    color = FieldColor.GREEN; break;
                case 'R':
                    color = FieldColor.RED; break;
                case 'Y':
                    color = FieldColor.YELLOW; break;
                default:
                    color = FieldColor.UNKNOWN; 
                    break;
            }              
        } else {
            //Color Not Found
            color = FieldColor.NONE;
        }

        SmartDashboard.putString("FMS Color", color.toString());

        return color;
    }

}