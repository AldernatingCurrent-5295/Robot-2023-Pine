package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;


public class LimelightDrive extends CommandBase{
    private final Drivetrain        drivetrain;
    private final Limelight         limelight;

    private final DoubleSupplier    drivePower;

    /**
     * Limelight assisted aiming with additional input for throttle
     * @param dtrain drivetrain subsystem
     * @param llight limelight subsystem
     * @param throttle doublesupplier for throttle control
     */
    public LimelightDrive(Drivetrain dtrain, Limelight llight, DoubleSupplier throttle) {

        drivetrain = dtrain;
        limelight = llight;

        this.drivePower = throttle;

        addRequirements(dtrain, llight);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {

        // if (!limelight.hasTarget()) { return; }

        double turnPower = limelight.getTargetX() / 30;
        // boolean gzero = turnPower > 0;
        // turnPower = Math.pow(turnPower, 2) * (gzero ? 1:-1);
        
        drivetrain.arcadeDrive(
            -drivePower.getAsDouble(),
            -turnPower
        );
    }
}
