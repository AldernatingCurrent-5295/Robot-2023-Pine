package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.mathutils;


public class AprilTagDrive extends CommandBase{
    private final Drivetrain        drivetrain;
    private final Camera         camera;

    private final DoubleSupplier    drivePower;
    private final int               tagID;

    /**
     * Camera assisted aiming using AprilTags with additional input for throttle
     * @param dtrain drivetrain subsystem
     * @param llight limelight subsystem
     * @param throttle doublesupplier for throttle control
     * @param tagID ID for the april tag to be tracked
     */
    public AprilTagDrive(Drivetrain dtrain, Camera cam, DoubleSupplier throttle, int tagID) {

        drivetrain = dtrain;
        camera = cam;

        this.drivePower = throttle;
        this.tagID = tagID;

        addRequirements(dtrain, cam);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double turnPower = (camera.getTagCX(tagID) - .5) * 2; // -0.5 * 2 centers it because its from [0, 1]
        boolean gzero = turnPower > 0;
        turnPower = Math.pow(turnPower, 2) * (gzero ? 1:-1);
        turnPower = mathutils.Clamp(turnPower, .3, -.3);
        
        drivetrain.arcadeDrive(
            -drivePower.getAsDouble(),
            -turnPower
        );
    }
}
