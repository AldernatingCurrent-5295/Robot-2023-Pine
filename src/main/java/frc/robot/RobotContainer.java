// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AprilTagDrive;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.SetLift;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Mechanism;

public class RobotContainer {

  // Subsytstems.
  Drivetrain                    drivetrain;
  Limelight                     limelight;
  Camera                        camera;
  Mechanism                     mechanism;

  // Susbystem Default Commands.
  private final Command       driveCommand;

  // Commands.
  private final LimelightDrive  lADrive;
  private final AprilTagDrive   cADrive;

  private final SetLift         liftUpCommand;
  private final SetLift         liftDownCommand;

  // Controllers
  private XboxController controller = new XboxController(0);

  public RobotContainer() {
    //Instantiate:
    //  Subsystems:
    drivetrain = new Drivetrain();
    limelight = new Limelight();
    camera = new Camera();
    mechanism = new Mechanism();
    //  Commands:
    lADrive = new LimelightDrive(drivetrain, limelight, () -> controller.getLeftY());
    cADrive = new AprilTagDrive(drivetrain, camera, () -> controller.getLeftY(), 6);
    liftDownCommand = new SetLift(mechanism, 1);
    liftUpCommand = new SetLift(mechanism, -1);

    drivetrain.setDefaultCommand(driveCommand = new ArcadeDrive(
      drivetrain,
      () -> -controller.getLeftY(),
      () -> -controller.getRightX()
      )
    );

    configureBindings();
  }

  private void configureBindings() {
    // Assisted Drive
    Trigger LADriveTrigger = new JoystickButton(controller, XboxController.Button.kA.value);
    Trigger CADriveTrigger = new JoystickButton(controller, XboxController.Button.kB.value);
    LADriveTrigger.whileTrue(lADrive);
    CADriveTrigger.whileTrue(cADrive);

    // Lift
    Trigger liftDownTrigger = new JoystickButton(controller, XboxController.Button.kBack.value);
    Trigger liftUpTrigger = new JoystickButton(controller, XboxController.Button.kStart.value);
    liftDownTrigger.whileTrue(liftDownCommand);
    liftUpTrigger.whileTrue(liftUpCommand);

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}