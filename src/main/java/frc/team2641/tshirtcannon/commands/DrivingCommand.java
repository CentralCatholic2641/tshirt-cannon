// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.tshirtcannon.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2641.tshirtcannon.Constants;
import frc.team2641.tshirtcannon.Robot;

public class DrivingCommand extends CommandBase {

  /** Creates a new Driving. */
  public DrivingCommand() {
    addRequirements(Robot.drivingSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Called every time the scheduler runs while the command is scheduled.
    double speed = Robot.robotContainer.gamepad1.getRawAxis(Constants.joystick1);
    double rotation = Robot.robotContainer.gamepad1.getRawAxis(Constants.joystick2);
    Robot.drivingSubsystem.aDrive(speed, rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
