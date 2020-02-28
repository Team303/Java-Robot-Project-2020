


package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    NetworkTable table;
    NetworkTableEntry tv;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry driverCam;
    public NetworkTableEntry ledMode;

     
    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        driverCam = table.getEntry("camMode");
        ledMode = table.getEntry("ledMode");

    }

    public double get2DDistance(){
        double h2 = 98.25; // height of the middle of the power port
        double h1 = 25; // height from the ground to the limelight
        double a2 = getYOffset(); // angle from the limelight to the pp
        double a1 = 0; // incline of the limelight

        double distance = (h2-h1) / Math.tan(Math.toRadians(a1 + a2)); // distance to get to the pp
        return distance;
    }

    public double getXOffset() {
        return -tx.getDouble(0.0);
    }

    public double getYOffset() {
        return ty.getDouble(0.0);
    }
    
    public double getArea() {
        return ta.getDouble(0.0);
    }
    public boolean hasValidContours() {
        if(tv.getDouble(0.0) < 1)return false;
        return true;
    }

    public void switchToVision(boolean vision) {
        if (vision) {
            driverCam.setNumber(1);
        } else {
            driverCam.setNumber(0);
        }

    }
}