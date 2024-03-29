// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.tshirtcannon.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2641.tshirtcannon.Robot;

public class ChargeCommand extends CommandBase {
  /** Creates a new ChargeCommand. */
  public ChargeCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.pneumaticSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.pneumaticSubsystem.charge();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.pneumaticSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
