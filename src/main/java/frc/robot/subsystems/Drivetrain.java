package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {

    private final CANSparkMax rfMotor = new CANSparkMax(DrivetrainConstants.rfMotorID, MotorType.kBrushless); 
    private final CANSparkMax rbMotor = new CANSparkMax(DrivetrainConstants.rbMotorID, MotorType.kBrushless); 

    private final CANSparkMax lfMotor = new CANSparkMax(DrivetrainConstants.lfMotorID, MotorType.kBrushless); 
    private final CANSparkMax lbMotor = new CANSparkMax(DrivetrainConstants.lbMotorID, MotorType.kBrushless); 

    private final MotorControllerGroup m_rMotorControllerGroup = 
        new MotorControllerGroup(
            rfMotor,
            rbMotor
        );

    private final MotorControllerGroup m_lMotorControllerGroup =
        new MotorControllerGroup(
            lfMotor,
            lbMotor
        );

    private final DifferentialDrive m_Drive = new DifferentialDrive(m_lMotorControllerGroup, m_rMotorControllerGroup);


    public Drivetrain() {
        boolean inv = false;
        rbMotor.setInverted(inv);
        lbMotor.setInverted(inv);

        m_lMotorControllerGroup.setInverted(true);
    }

    public void tankDrive(Double rPwr, Double lPwr, boolean squareInputs) {
            m_Drive.tankDrive(rPwr, lPwr, squareInputs);
    }
}
