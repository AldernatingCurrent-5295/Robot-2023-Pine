package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
    }
<<<<<<< HEAD

    public boolean hasTarget() {
        return tv.getBoolean(false);
    }

    public double getTargetX() {
        return tx.getDouble(0.0);
    }

    public double getTargetY() {
        return ty.getDouble(0.0);
    }
}
=======
}
>>>>>>> 8cfe7f26681ec34f155ec96b91f27642c7f5afee
