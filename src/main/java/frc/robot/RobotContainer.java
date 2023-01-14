// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class RobotContainer {

  // Subsytstems.
  Drivetrain drivetrain;
  Limelight limelight;

  // Susbystem Default Commands.
  private final TankDrive driveCommand;

  private XboxController controller = new XboxController(0);

  public RobotContainer() {
    configureBindings();

    drivetrain = new Drivetrain();

    drivetrain.setDefaultCommand(driveCommand = new TankDrive(
      drivetrain,
      () -> controller.getLeftY(),
      () -> controller.getRightY())
    );
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}