package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Mechanism;

public class SetLift extends CommandBase{
    private final Mechanism mechanism;
    private final double power;

    public SetLift(Mechanism mchnsm, double power) {
        mechanism = mchnsm;

        this.power = power;

        addRequirements(mchnsm);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        mechanism.SetLiftPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        mechanism.SetLiftPower(0);
    }
}
