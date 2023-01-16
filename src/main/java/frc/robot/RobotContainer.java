// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class RobotContainer {

  // Subsytstems.
  Drivetrain drivetrain;
  Limelight limelight;

  // Susbystem Default Commands.
  private final TankDrive driveCommand;

  // Commands.
  private final LimelightDrive lADrive;

  // Controllers
  private XboxController controller = new XboxController(0);

  public RobotContainer() {
    //Instantiate:
    //  Subsystems:
    drivetrain = new Drivetrain();
    limelight = new Limelight();
    //  Commands:
    lADrive = new LimelightDrive(drivetrain, limelight, () -> controller.getLeftY());

    drivetrain.setDefaultCommand(driveCommand = new TankDrive(
      drivetrain,
      () -> controller.getLeftY(),
      () -> controller.getRightY()
      )
    );

    configureBindings();
  }

  private void configureBindings() {
    Trigger LADriveTrigger = new JoystickButton(controller, XboxController.Button.kA.value);
    LADriveTrigger.whileTrue(lADrive);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}