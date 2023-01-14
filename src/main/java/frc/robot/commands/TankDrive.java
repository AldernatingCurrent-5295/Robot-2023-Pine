package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends CommandBase{
    
    private final Drivetrain        drivetrain;
    private final DoubleSupplier    leftPower, rightPower;
    
    public TankDrive(Drivetrain dtrain, DoubleSupplier leftPower, DoubleSupplier rightPower) {

        drivetrain = dtrain;

        this.leftPower = leftPower;
        this.rightPower = rightPower;

        addRequirements(dtrain);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        drivetrain.tankDrive(
            leftPower.getAsDouble(), 
            rightPower.getAsDouble(), 
            true
        );
    }
}
