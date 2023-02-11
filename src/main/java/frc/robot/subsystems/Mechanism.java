package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MechanismConfig;

public class Mechanism extends SubsystemBase{
    private final CANSparkMax liftMotor = new CANSparkMax(MechanismConfig.liftMotorID, MotorType.kBrushless);
    
    public void SetLiftPower(double power) {
        liftMotor.set(power);
    }
}
