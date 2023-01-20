package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LimelightConfig;

public class Limelight extends SubsystemBase{
    
    private NetworkTable	networkTable = NetworkTableInstance.getDefault().getTable("limelight");
    
    NetworkTableEntry		tv = networkTable.getEntry("tv");
	NetworkTableEntry		tx = networkTable.getEntry("tx");
	NetworkTableEntry		ty = networkTable.getEntry("ty");
	NetworkTableEntry		ta = networkTable.getEntry("ta");

    /**
     * Subsystem for interacting with limelight through networktables
     */
    public Limelight() {

    }

    @Override
	public void periodic() {
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);

        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("Target Distance", estimateDistance());

    }

    public boolean hasTarget() {
        return tv.getBoolean(false);
    }

    public double getTargetX() {
        return tx.getDouble(0.0);
    }

    public double  getTargetY() {
        return ty.getDouble(0.0);
    }

    /*
     * Estimated distance from limelight lens to pole goal.
     */
    public double estimateDistance() {
        
        double tVerticalAngle = getTargetY();
        double angleToGoalDegrees = LimelightConfig.llAngle + tVerticalAngle;
        // SmartDashboard.putNumber("Degrees", angleToGoalDegrees);
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
        // SmartDashboard.putNumber("Radians", angleToGoalRadians);
        double dist = (LimelightConfig.goalElevation - LimelightConfig.llElevation) / Math.tan(angleToGoalRadians);

        return dist;
    }
}