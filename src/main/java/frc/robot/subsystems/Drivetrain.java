package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConfig;

public class Drivetrain extends SubsystemBase {

    private final CANSparkMax rfMotor = new CANSparkMax(DrivetrainConfig.rfMotorID, MotorType.kBrushless); 
    private final CANSparkMax rbMotor = new CANSparkMax(DrivetrainConfig.rbMotorID, MotorType.kBrushless); 

    private final CANSparkMax lfMotor = new CANSparkMax(DrivetrainConfig.lfMotorID, MotorType.kBrushless); 
    private final CANSparkMax lbMotor = new CANSparkMax(DrivetrainConfig.lbMotorID, MotorType.kBrushless); 

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

    private final AHRS gyroAhrs = new AHRS(SPI.Port.kMXP);

    private final RelativeEncoder rfEncoder = rfMotor.getEncoder();
    // private final RelativeEncoder rbEncoder = rbMotor.getEncoder();
    private final RelativeEncoder lfEncoder = lfMotor.getEncoder();
    // private final RelativeEncoder lbEncoder = lbMotor.getEncoder();

    private final DifferentialDrive m_Drive = new DifferentialDrive(m_lMotorControllerGroup, m_rMotorControllerGroup);

    /**
     * Subsystem for interacting with robot's drivetrain
     */
    public Drivetrain() {
        boolean inv = false;
        rbMotor.setInverted(inv);
        lbMotor.setInverted(inv);

        m_lMotorControllerGroup.setInverted(false);
        m_rMotorControllerGroup.setInverted(true);
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Right Enc R8", rfEncoder.getVelocity());
        SmartDashboard.putNumber("Left Enc R8", lfEncoder.getVelocity());

        SmartDashboard.putNumber("Gyro", getHeading());

        double[] motor_values = new double[4];
        motor_values[0] = lfMotor.get();
        motor_values[1] = rfMotor.get();
        motor_values[2] = lbMotor.get();
        motor_values[3] = rbMotor.get();
        SmartDashboard.putNumberArray("RobotDrive Motors", motor_values);
    }

    public double getHeading() {
        return gyroAhrs.getCompassHeading();
    }

    public void tankDrive(Double rPwr, Double lPwr, boolean squareInputs) {
        m_Drive.tankDrive(rPwr, lPwr, squareInputs);
    }

    public void arcadeDrive(double pwr, double rot) {
        m_Drive.arcadeDrive(pwr, rot);
    }
}